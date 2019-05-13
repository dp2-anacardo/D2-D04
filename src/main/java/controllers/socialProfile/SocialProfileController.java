
package controllers.socialProfile;

import controllers.AbstractController;
import domain.Actor;
import domain.SocialProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.SocialProfileService;

import javax.validation.ValidationException;
import java.util.Collection;

@Controller
@RequestMapping("/socialProfile/admin,company,rookie")
public class SocialProfileController extends AbstractController {

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private ActorService			actorService;




	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<SocialProfile> profiles;
		final Actor user = this.actorService.getActorLogged();
		profiles = user.getSocialProfiles();

		result = new ModelAndView("socialProfile/admin,company,rookie/list");
		result.addObject("socialProfiles", profiles);
		result.addObject("requestURI", "socialProfile/admin,company,rookie/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialProfile profile;

		profile = this.socialProfileService.create();
		result = this.createEditModelAndView(profile);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialProfileId) {

		ModelAndView result;
		SocialProfile profile;
		Actor user;

		try {
			user = this.actorService.getActorLogged();
			profile = this.socialProfileService.findOne(socialProfileId);
			Assert.notNull(profile);
			Assert.isTrue(user.getSocialProfiles().contains(profile));
			Assert.isTrue(this.actorService.existIdSocialProfile(socialProfileId));
		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}

		result = this.createEditModelAndView(profile);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(SocialProfile profile, final BindingResult binding) {

		ModelAndView result;

		try {
			profile = this.socialProfileService.reconstruct(profile, binding);
			profile = this.socialProfileService.save(profile);
			result = new ModelAndView("redirect:list.do");
		} catch (final ValidationException e) {
			result = this.createEditModelAndView(profile, null);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(profile, "socialProfile.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(SocialProfile profile, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(profile);
		else
			try {
				profile = this.socialProfileService.reconstruct(profile, binding);
				this.socialProfileService.delete(profile);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(profile, "socialProfile.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialProfile profile) {

		ModelAndView result;
		result = this.createEditModelAndView(profile, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialProfile profile, final String messageCode) {

		ModelAndView result;
		result = new ModelAndView("socialProfile/admin,company,rookie/edit");
		result.addObject("socialProfile", profile);
		result.addObject("message", messageCode);
		return result;
	}

	private ModelAndView forbiddenOperation() {
		return new ModelAndView("redirect:/");
	}
}
