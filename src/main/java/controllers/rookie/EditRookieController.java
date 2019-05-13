
package controllers.rookie;

import controllers.AbstractController;
import domain.Actor;
import domain.Rookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.RookieService;

import javax.validation.Valid;

@Controller
@RequestMapping("rookie/rookie")
public class EditRookieController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private RookieService	rookieService;




	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		final Actor user = this.actorService.getActorLogged();
		final Rookie a = this.rookieService.findOne(user.getId());
		Assert.notNull(a);
		result = this.editModelAndView(a);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "update")
	public ModelAndView update(@Valid Rookie a, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(a);
		else
			try {
				a = this.rookieService.reconstruct(a, binding);
				this.rookieService.save(a);
				result = new ModelAndView("redirect:/profile/myInformation.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(a, "actor.commit.error");
			}
		return result;
	}

	protected ModelAndView editModelAndView(final Rookie a) {
		ModelAndView result;
		result = this.editModelAndView(a, null);
		return result;
	}

	protected ModelAndView editModelAndView(final Rookie a, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("rookie/rookie/edit");
		result.addObject("rookie", a);
		result.addObject("messageCode", messageCode);

		return result;
	}
}
