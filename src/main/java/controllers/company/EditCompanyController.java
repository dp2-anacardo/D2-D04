
package controllers.company;

import controllers.AbstractController;
import domain.Actor;
import domain.Company;
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
import services.CompanyService;

import javax.validation.Valid;

@Controller
@RequestMapping("company/company")
public class EditCompanyController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private CompanyService	companyService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		final Actor user = this.actorService.getActorLogged();
		final Company a = this.companyService.findOne(user.getId());
		Assert.notNull(a);
		result = this.editModelAndView(a);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "update")
	public ModelAndView update(@Valid Company a, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(a);
		else
			try {
				a = this.companyService.reconstruct(a, binding);
				this.companyService.save(a);
				result = new ModelAndView("redirect:/profile/myInformation.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(a, "actor.commit.error");
			}
		return result;
	}

	protected ModelAndView editModelAndView(final Company a) {
		ModelAndView result;
		result = this.editModelAndView(a, null);
		return result;
	}

	protected ModelAndView editModelAndView(final Company a, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("company/company/edit");
		result.addObject("company", a);
		result.addObject("messageCode", messageCode);

		return result;
	}
}
