
package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.CurriculaRepository;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class CurriculaService {

	//Managed repository
	@Autowired
	private CurriculaRepository	curriculaRepository;
	//Services
	@Autowired
	private ActorService		actorService;
	@Autowired
	private RookieService		rookieService;
	@Autowired
	private EducationalDataService educationalDataService;
	@Autowired
	private MiscDataService miscDataService;
	@Autowired
	private PersonalDataService personalDataService;
	@Autowired
	private PositionDataService positionDataService;


	public Curricula create() {
		UserAccount userAccount;
		userAccount = this.actorService.getActorLogged().getUserAccount();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));

		final Curricula result = new Curricula();
		return result;
	}

	public Collection<Curricula> findAll() {
		return this.curriculaRepository.findAll();
	}

	public Curricula findOne(final int id) {
		return this.curriculaRepository.findOne(id);
	}

	public Curricula save(Curricula curricula) {
		Assert.notNull(curricula);
		final Actor a = this.actorService.getActorLogged();
		final Rookie h = this.rookieService.findOne(a.getId());
		Assert.notNull(h);

		if (curricula.getId() == 0) {
			curricula = this.curriculaRepository.save(curricula);
			h.getCurricula().add(curricula);
		} else
			curricula = this.curriculaRepository.save(curricula);

		return curricula;
	}

	public void delete(final Curricula curricula) {
		Assert.notNull(curricula);
		final Actor a = this.actorService.getActorLogged();
		final Rookie h = this.rookieService.findOne(a.getId());
		Assert.notNull(h);
		Assert.isTrue(h.getCurricula().contains(curricula));
		h.getCurricula().remove(curricula);
		this.curriculaRepository.delete(curricula);
	}

	public void deleteCopy(final Curricula curricula){
		Assert.notNull(curricula);
		this.curriculaRepository.delete(curricula);
	}

	public Curricula copy(final Curricula curricula) {
		Assert.notNull(curricula);
		Curricula result;
		Collection<EducationalData> educationalData = new ArrayList<>();
		MiscData miscData = new MiscData();
		PersonalData personalData = new PersonalData();
		Collection<PositionData> positionData = new ArrayList<>();
		result = new Curricula();

		if(curricula.getEducationalData() != null){
			for (final EducationalData ed : curricula.getEducationalData()) {
				EducationalData res = new EducationalData();
				res.setDegree(ed.getDegree());
				res.setEndDate(ed.getEndDate());
				res.setStartDate(ed.getStartDate());
				res.setInstitution(ed.getInstitution());
				res.setMark(ed.getMark());
				res = this.educationalDataService.save2(res);
				educationalData.add(res);
			}
			result.setEducationalData(educationalData);
		}
		if(curricula.getMiscData()!=null){
			miscData.setAttachment(curricula.getMiscData().getAttachment());
			miscData.setFreeText(curricula.getMiscData().getFreeText());
			miscData = this.miscDataService.save2(miscData);
			result.setMiscData(miscData);
		}


		personalData.setFullName(curricula.getPersonalData().getFullName());
		personalData.setGithubProfile(curricula.getPersonalData().getGithubProfile());
		personalData.setLinkedInProfile(curricula.getPersonalData().getLinkedInProfile());
		personalData.setPhoneNumber(curricula.getPersonalData().getPhoneNumber());
		personalData.setStatement(curricula.getPersonalData().getStatement());
		personalData = this.personalDataService.save(personalData);

		if(curricula.getPositionData() != null){
			for (final PositionData pd : curricula.getPositionData()) {
				PositionData res = new PositionData();
				res.setDescription(pd.getDescription());
				res.setEndDate(pd.getEndDate());
				res.setStartDate(pd.getStartDate());
				res.setTitle(pd.getTitle());
				res = this.positionDataService.save2(res);
				positionData.add(res);
			}
			result.setPositionData(positionData);
		}





		result.setPersonalData(personalData);

		result.setIsCopy(true);

		result = this.curriculaRepository.save(result);

		return result;
	}

	public Collection<Curricula> getCurriculaByRookie() {
		final Actor a = this.actorService.getActorLogged();
		final Rookie h = this.rookieService.findOne(a.getId());
		Assert.notNull(h);
		return this.curriculaRepository.getCurriculaByRookie(h);
	}

}
