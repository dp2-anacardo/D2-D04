
package controllers.rookie;

import controllers.AbstractController;
import datatype.CreditCard;
import domain.Rookie;
import forms.RookieForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.AdministratorService;
import services.RookieService;

import javax.validation.Valid;

@Controller
@RequestMapping("/rookie")
public class RegisterRookieController extends AbstractController {

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private ActorService			actorService;
	@Autowired
	private AdministratorService	administratorService;




	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		RookieForm rookieForm;
		rookieForm = new RookieForm();
		result = this.createEditModelAndView(rookieForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView save(@Valid final RookieForm rookieForm, final BindingResult binding) {
		ModelAndView result;
		Rookie rookie;

		if (this.actorService.existUsername(rookieForm.getUsername()) == false) {
			binding.rejectValue("username", "error.username");
			result = this.createEditModelAndView(rookieForm);
		} else if (this.administratorService.checkPass(rookieForm.getPassword(), rookieForm.getConfirmPass()) == false) {
			binding.rejectValue("password", "error.password");
			result = this.createEditModelAndView(rookieForm);
		} else if (binding.hasErrors())
			result = this.createEditModelAndView(rookieForm);
		else
			try {

				final CreditCard c = new CreditCard();
				c.setBrandName(rookieForm.getBrandName());
				c.setCvv(rookieForm.getCvvCode());
				c.setExpirationYear(rookieForm.getExpiration());
				c.setHolder(rookieForm.getHolderName());
				c.setNumber(rookieForm.getNumber());
				rookie = this.rookieService.reconstruct(rookieForm, binding);
				rookie.setCreditCard(c);
				this.rookieService.save(rookie);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(rookieForm, "error.commit.error");
			}
		return result;
	}

	@RequestMapping(value="/company/show", method = RequestMethod.GET)
	public ModelAndView showCompany(@RequestParam int rookieId){
		ModelAndView result;

		try{
			Assert.notNull(rookieId);
			Rookie rookie = rookieService.findOne(rookieId);
			result = new ModelAndView("rookie/company/show");
			result.addObject("rookie", rookie);
		}catch (Throwable oops){
			result = new ModelAndView("redirect/misc/403");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final RookieForm rookieForm) {
		ModelAndView result;
		result = this.createEditModelAndView(rookieForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final RookieForm rookieForm, final String messageCode) {

		final ModelAndView result;

		result = new ModelAndView("rookie/create");
		result.addObject("rookieForm", rookieForm);
		result.addObject("message", messageCode);

		return result;
	}
}
