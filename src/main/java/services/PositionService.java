
package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.PositionRepository;
import security.UserAccount;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.*;

@Service
@Transactional
public class PositionService {
    
	//Managed Repositories
	@Autowired
	private PositionRepository		positionRepository;

	//Supporting services
	@Autowired
	private ActorService			actorService;
	@Autowired
	private FinderService			finderService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private Validator				validator;
	@Autowired
	private CompanyService			companyService;
	@Autowired
	private RookieService			rookieService;
	@Autowired
	private MessageService			messageService;


	public Position create() {
		UserAccount userAccount;
		userAccount = this.actorService.getActorLogged().getUserAccount();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("COMPANY"));

		final Position position = new Position();
		return position;
	}

	public Collection<Position> findAll() {
		final Collection<Position> res = this.positionRepository.findAll();
		return res;
	}

	public Position findOne(final int id) {
		return this.positionRepository.findOne(id);
	}

	public Position save(Position position) {
		UserAccount userAccount;
		userAccount = this.actorService.getActorLogged().getUserAccount();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("COMPANY"));

		final Company c = this.companyService.findOne(this.actorService.getActorLogged().getId());
		Assert.notNull(c);
		Assert.notNull(position);

		if (position.getId() == 0) {
			Assert.isTrue(position.getIsCancelled() == false);
			position.setTicker(this.tickerGenerator(position));
			position = this.positionRepository.save(position);
		} else {
			Assert.isTrue(position.getCompany().equals(c));
			position = this.positionRepository.save(position);
		}
		return position;
	}

	public Position saveDraft(final Position position) {
		Assert.notNull(position);
		Assert.isTrue(position.getIsFinal() == false);
		position.setIsFinal(false);
		final Position result = this.save(position);
		return result;
	}

	public Position saveFinal(final Position position) {
		Assert.notNull(position);
		Assert.isTrue(position.getProblems().size() >= 2);
		if (position.getId() != 0)
			Assert.isTrue(position.getIsFinal() == false);
		position.setIsFinal(true);
		final Position result = this.save(position);

		for (final Rookie h : this.rookieService.findAll()) {
			final Collection<Position> res = this.finderService.search(h.getFinder());
			if (res.contains(result)) {
				final Message msg = this.messageService.create();
				msg.setRecipient(h);
				msg.setSubject("A new position matches your criteria.");
				msg.setBody("A new position that matches your criteria has been published.");
				msg.getTags().add("NOTIFICATION");
				this.messageService.save(msg);
			}
		}

		return result;
	}

	public void delete(final Position position) {
		Assert.notNull(position);
		UserAccount userAccount;
		userAccount = this.actorService.getActorLogged().getUserAccount();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("COMPANY"));
		Assert.isTrue(position.getIsFinal() == false);

		this.positionRepository.delete(position);

	}

    public void deleteForced(final Position position){
        Assert.notNull(position);
        this.positionRepository.delete(position.getId());
    }

	private String tickerGenerator(final Position position) {
		final String res = position.getCompany().getCommercialName().substring(0, 4).toUpperCase();
		final Random random = new Random();

		final String nums = String.format("%04d", random.nextInt(10000));

		return res + "-" + nums;

	}

	public List<Position> getPositionsAvilables() {
		final List<Position> res = this.positionRepository.getPositionsAvailable();
		return res;
	}

	public Collection<Position> getPositionsByCompanyAll(final Company company) {
		Collection<Position> positions;

		positions = this.positionRepository.getPositionsByCompanyAll(company.getId());

		return positions;
	}

	public Collection<Position> searchPositions(final String keyword) {
		Collection<Position> res = Collections.emptyList();
		List<Position> proAux1;
		List<Position> proAux2;

		if (!(keyword == null || keyword.equals(""))) {
			proAux1 = (List<Position>) this.finderService.getPositionsByKeyWord(keyword);
			proAux2 = (List<Position>) this.finderService.getPositionsContainsKeyWord(keyword);

			final Set<Position> set = new LinkedHashSet<>(proAux1);
			set.addAll(proAux2);
			res = new ArrayList<>(set);
		}

		Configuration conf;
		conf = this.configurationService.getConfiguration();

		res = this.finderService.maxPosition(res, conf);

		return res;
	}

	public Position reconstruct(final Position p, final BindingResult binding) {
		Position result;
		if (p.getId() == 0) {
			result = this.create();
			result.setCompany(p.getCompany());
			result.setIsFinal(p.getIsFinal());
			result.setTicker(this.tickerGenerator(p));

		} else
			result = this.positionRepository.findOne(p.getId());
		result.setTitle(p.getTitle());
		result.setDescription(p.getDescription());
		result.setDeadline(p.getDeadline());
		result.setSkill(p.getSkill());
		result.setTechnology(p.getTechnology());
		result.setProfile(p.getProfile());
		result.setSalary(p.getSalary());
		result.setProblems(p.getProblems());

		this.validator.validate(result, binding);
		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}

	public Collection<Position> getPositionsByCompanyAvailable(final int companyId) {
		return this.positionRepository.getPositionsByCompanyAvailable(companyId);
	}

	public Collection<Auditor> getAuditorsByPosition(int positionId) {
		return this.positionRepository.getAuditorsByPosition(positionId);
	}
}
