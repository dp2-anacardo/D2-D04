
package controllers.auditor;

import controllers.AbstractController;
import domain.Actor;
import domain.Auditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.AuditorService;

import javax.validation.Valid;

@Controller
@RequestMapping("auditor/auditor")
public class EditAuditorController extends AbstractController {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private AuditorService			auditorService;




	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		final Actor user = this.actorService.getActorLogged();
		final Auditor a = this.auditorService.findOne(user.getId());
		Assert.notNull(a);
		result = this.editModelAndView(a);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "update")
	public ModelAndView update(@Valid Auditor a, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(a);
		else
			try {
				a = this.auditorService.reconstruct(a, binding);
				this.auditorService.save(a);
				result = new ModelAndView("redirect:/profile/myInformation.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(a, "actor.commit.error");
			}
		return result;
	}

	protected ModelAndView editModelAndView(final Auditor a) {
		ModelAndView result;
		result = this.editModelAndView(a, null);
		return result;
	}

	protected ModelAndView editModelAndView(final Auditor a, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("auditor/auditor/edit");
		result.addObject("auditor", a);
		result.addObject("messageCode", messageCode);

		return result;
	}
}
