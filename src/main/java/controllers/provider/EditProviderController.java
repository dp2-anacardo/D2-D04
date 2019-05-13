
package controllers.provider;

import controllers.AbstractController;
import domain.Actor;
import domain.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.ProviderService;

import javax.validation.Valid;

@Controller
@RequestMapping("provider/provider")
public class EditProviderController extends AbstractController {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private ProviderService	providerService;




	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		final Actor user = this.actorService.getActorLogged();
		final Provider p = this.providerService.findOne(user.getId());
		Assert.notNull(p);
		result = this.editModelAndView(p);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "update")
	public ModelAndView update(@Valid Provider p, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(p);
		else
			try {
				p = this.providerService.reconstruct(p, binding);
				this.providerService.save(p);
				result = new ModelAndView("redirect:/profile/myInformation.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(p, "actor.commit.error");
			}
		return result;
	}

	protected ModelAndView editModelAndView(final Provider p) {
		ModelAndView result;
		result = this.editModelAndView(p, null);
		return result;
	}

	protected ModelAndView editModelAndView(final Provider p, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("provider/provider/edit");
		result.addObject("provider", p);
		result.addObject("messageCode", messageCode);

		return result;
	}
}
