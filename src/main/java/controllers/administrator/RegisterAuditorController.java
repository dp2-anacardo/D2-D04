
package controllers.administrator;

import controllers.AbstractController;
import datatype.CreditCard;
import domain.Auditor;
import forms.AuditorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.AdministratorService;
import services.AuditorService;

import javax.validation.Valid;

@Controller
@RequestMapping("administrator/auditor")
public class RegisterAuditorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private ActorService			actorService;




	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		AuditorForm aForm;
		aForm = new AuditorForm();
		result = this.createEditModelAndView(aForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView save(@Valid final AuditorForm aForm, final BindingResult binding) {
		ModelAndView result;
		Auditor a;

		if (this.actorService.existUsername(aForm.getUsername()) == false) {
			binding.rejectValue("username", "error.username");
			result = this.createEditModelAndView(aForm);
		} else if (this.administratorService.checkPass(aForm.getPassword(), aForm.getConfirmPass()) == false) {
			binding.rejectValue("password", "error.password");
			result = this.createEditModelAndView(aForm);
		} else if (binding.hasErrors())
			result = this.createEditModelAndView(aForm);
		else
			try {
				final CreditCard c = new CreditCard();
				c.setBrandName(aForm.getBrandName());
				c.setCvv(aForm.getCvvCode());
				c.setExpirationYear(aForm.getExpiration());
				c.setHolder(aForm.getHolderName());
				c.setNumber(aForm.getNumber());
				a = this.auditorService.reconstruct(aForm,binding);
				a.setCreditCard(c);
				this.auditorService.save(a);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				if (binding.hasErrors()) {
					result = this.createEditModelAndView(aForm, "error.duplicated");
				}else {
					result = this.createEditModelAndView(aForm, "error.commit.error");
				}
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final AuditorForm aForm) {
		ModelAndView result;
		result = this.createEditModelAndView(aForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final AuditorForm aForm, final String messageCode) {

		final ModelAndView result;

		result = new ModelAndView("administrator/auditor/create");
		result.addObject("auditorForm", aForm);
		result.addObject("message", messageCode);

		return result;
	}
}
