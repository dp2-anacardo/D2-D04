
package controllers.provider;

import controllers.AbstractController;
import datatype.CreditCard;
import domain.Provider;
import forms.ProviderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.AdministratorService;
import services.ProviderService;

import javax.validation.Valid;

@Controller
@RequestMapping("/provider")
public class RegisterProviderController extends AbstractController {

	@Autowired
	private ProviderService	providerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;




	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		ProviderForm providerForm;
		providerForm = new ProviderForm();
		result = this.createEditModelAndView(providerForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView save(@Valid final ProviderForm pForm, final BindingResult binding) {
		ModelAndView result;
		Provider p;

		if (this.actorService.existUsername(pForm.getUsername()) == false) {
			binding.rejectValue("username", "error.username");
			result = this.createEditModelAndView(pForm);
		} else if (this.administratorService.checkPass(pForm.getPassword(),pForm.getConfirmPass()) == false) {
			binding.rejectValue("password", "error.password");
			result = this.createEditModelAndView(pForm);
		} else if (binding.hasErrors())
			result = this.createEditModelAndView(pForm);
		else
			try {
				final CreditCard c = new CreditCard();
				c.setBrandName(pForm.getBrandName());
				c.setCvv(pForm.getCvvCode());
				c.setExpirationYear(pForm.getExpiration());
				c.setHolder(pForm.getHolderName());
				c.setNumber(pForm.getNumber());
				p = this.providerService.reconstruct(pForm, binding);
				p.setCreditCard(c);
				this.providerService.save(p);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				if (binding.hasErrors()) {
					result = this.createEditModelAndView(pForm, "error.duplicated");
				}else{
						result = this.createEditModelAndView(pForm, "error.commit.error");
					}
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final ProviderForm pForm) {
		ModelAndView result;
		result = this.createEditModelAndView(pForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ProviderForm pForm, final String messageCode) {

		final ModelAndView result;

		result = new ModelAndView("provider/create");
		result.addObject("providerForm", pForm);
		result.addObject("message", messageCode);

		return result;
	}
}
