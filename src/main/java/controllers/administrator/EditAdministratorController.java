
package controllers.administrator;

import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import org.hibernate.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.AdministratorService;

import javax.validation.Valid;

@Controller
@RequestMapping("administrator/administrator")
public class EditAdministratorController extends AbstractController {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private AdministratorService	administratorService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		final Actor user = this.actorService.getActorLogged();
		final Administrator admin = this.administratorService.findOne(user.getId());
		Assert.notNull(admin);
		result = this.editModelAndView(admin);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "update")
	public ModelAndView update(@Valid Administrator admin, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(admin);
		else
			try {
				admin = this.administratorService.reconstruct(admin, binding);
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:/profile/myInformation.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(admin, "actor.commit.error");
			}
		return result;
	}

	protected ModelAndView editModelAndView(final Administrator admin) {
		ModelAndView result;
		result = this.editModelAndView(admin, null);
		return result;
	}

	protected ModelAndView editModelAndView(final Administrator admin, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("administrator/administrator/edit");
		result.addObject("administrator", admin);
		result.addObject("messageCode", messageCode);

		return result;
	}
}
