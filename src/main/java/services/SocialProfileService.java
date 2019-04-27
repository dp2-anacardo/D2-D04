
package services;

import domain.Actor;
import domain.SocialProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.SocialProfileRepository;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class SocialProfileService {

	@Autowired
	private SocialProfileRepository	socialProfileRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	public SocialProfile create() {

		SocialProfile result;
		result = new SocialProfile();
		return result;

	}

	public SocialProfile findOne(final Integer socialProfileId) {

		SocialProfile result;
		Assert.notNull(socialProfileId);
		result = this.socialProfileRepository.findOne(socialProfileId);
		Assert.notNull(result);
		return result;
	}

	public List<SocialProfile> findAll() {

		List<SocialProfile> result;
		result = this.socialProfileRepository.findAll();
		Assert.notEmpty(result);
		return result;
	}

	public SocialProfile save(final SocialProfile socialProfile) {

		SocialProfile result;
		Assert.notNull(socialProfile);
		final Actor user = this.actorService.getActorLogged();
		result = this.socialProfileRepository.save(socialProfile);
		if (socialProfile.getId() == 0)
			user.getSocialProfiles().add(result);
		return result;

	}

	public void delete(final SocialProfile socialProfile) {

		final Actor actor = this.actorService.getActorLogged();
		Assert.notNull(socialProfile);
		Assert.isTrue(this.socialProfileRepository.exists(socialProfile.getId()));
		final Collection<SocialProfile> result = actor.getSocialProfiles();
		Assert.notEmpty(result);
		result.remove(socialProfile);
		this.socialProfileRepository.delete(socialProfile.getId());
	}

	//Metodo reconstruct para el objeto poda
	public SocialProfile reconstruct(final SocialProfile profile, final BindingResult binding) {

		SocialProfile result;
		if (profile.getId() == 0) {
			result = this.create();
		} else {
			result = this.socialProfileRepository.findOne(profile.getId());
			profile.setVersion(result.getVersion());
			result = profile;
		}

		result.setNick(profile.getNick());
		result.setProfileLink(profile.getProfileLink());
		result.setSocialNetworkName(profile.getSocialNetworkName());

		this.validator.validate(profile, binding);
		if (binding.hasErrors()){
			throw new ValidationException();
		}
		return result;
	}
}
