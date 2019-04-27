
package services;

import domain.*;
import forms.RookieForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.RookieRepository;
import security.Authority;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class RookieService {

	//Managed Repositories
	@Autowired
	private ActorService			actorService;
	//Supporting services
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private RookieRepository		rookieRepository;
	@Autowired
	private Validator				validator;
	@Autowired
	private FinderService			finderService;


	public Rookie create() {

		final Authority auth;
		final UserAccount userAccount;
		final Collection<Authority> authorities;
		final Collection<SocialProfile> profiles;
		final Collection<Message> sent;
		final Collection<Message> received;
		final Collection<Curricula> curriculas;
		final Rookie a = new Rookie();
		userAccount = new UserAccount();
		auth = new Authority();
		authorities = new ArrayList<Authority>();
		profiles = new ArrayList<SocialProfile>();
		sent = new ArrayList<Message>();
		received = new ArrayList<Message>();
		curriculas = new ArrayList<Curricula>();

		auth.setAuthority(Authority.ROOKIE);
		authorities.add(auth);
		userAccount.setAuthorities(authorities);
		a.setUserAccount(userAccount);
		a.setIsBanned(false);
		a.setIsSpammer(false);
		a.setMessagesReceived(received);
		a.setMessagesSent(sent);
		a.setSocialProfiles(profiles);
		a.setCurricula(curriculas);
		return a;
	}

	public Collection<Rookie> findAll() {
		Collection<Rookie> result;

		result = this.rookieRepository.findAll();

		return result;
	}

	public Rookie findOne(final int rookieId) {
		Assert.isTrue(rookieId != 0);

		Rookie result;

		result = this.rookieRepository.findOne(rookieId);

		return result;
	}

	public Rookie save(final Rookie rookie) {

		Assert.notNull(rookie);
		Rookie result;
		final char[] c = rookie.getPhoneNumber().toCharArray();
		if ((!rookie.getPhoneNumber().equals(null) && !rookie.getPhoneNumber().equals("")))
			if (c[0] != '+') {
				final String i = this.configurationService.findAll().get(0).getCountryCode();
				rookie.setPhoneNumber("+" + i + " " + rookie.getPhoneNumber());
			}
		if (rookie.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String res = encoder.encodePassword(rookie.getUserAccount().getPassword(), null);
			rookie.getUserAccount().setPassword(res);
			Finder finderCreate;
			Finder finder;
			finderCreate = this.finderService.create();
			finder = this.finderService.save(finderCreate);
			rookie.setFinder(finder);

		}
		result = this.rookieRepository.save(rookie);
		return result;
	}

	public void delete(final Rookie rookie) {

		final Actor actor = this.actorService.getActorLogged();
		Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));
		Assert.notNull(rookie);

		this.rookieRepository.delete(rookie);
	}

	public Rookie reconstruct(final Rookie rookie, final BindingResult binding) {

		Rookie result;
		if (rookie.getId() == 0) {
			this.validator.validate(rookie, binding);
			result = rookie;
		} else {
			result = this.rookieRepository.findOne(rookie.getId());

			result.setName(rookie.getName());
			result.setPhoto(rookie.getPhoto());
			result.setPhoneNumber(rookie.getPhoneNumber());
			result.setEmail(rookie.getEmail());
			result.setAddress(rookie.getAddress());
			result.setVatNumber(rookie.getVatNumber());
			result.setSurname(rookie.getSurname());
			this.validator.validate(rookie, binding);
		}
		return result;
	}

	public Rookie reconstruct(final RookieForm a, final BindingResult binding) {

		final Rookie result = this.create();
		result.setAddress(a.getAddress());
		result.setEmail(a.getEmail());
		result.setId(a.getId());
		result.setName(a.getName());
		result.setPhoneNumber(a.getPhoneNumber());
		result.setPhoto(a.getPhoto());
		result.setSurname(a.getSurname());
		result.getUserAccount().setPassword(a.getPassword());
		result.getUserAccount().setUsername(a.getUsername());
		result.setVersion(a.getVersion());
		result.setVatNumber(a.getVatNumber());

		this.validator.validate(result, binding);
		return result;
	}

}
