
package controllers.company;

import controllers.AbstractController;
import datatype.CreditCard;
import domain.Company;
import forms.CompanyForm;
import org.hibernate.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.AdministratorService;
import services.CompanyService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/company")
public class RegisterCompanyController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CompanyService			companyService;



	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		CompanyForm companyForm;
		companyForm = new CompanyForm();
		result = this.createEditModelAndView(companyForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView save(@Valid final CompanyForm companyForm, final BindingResult binding) {
		ModelAndView result;
		Company cy;

		if (this.actorService.existUsername(companyForm.getUsername()) == false) {
			binding.rejectValue("username", "error.username");
			result = this.createEditModelAndView(companyForm);
		} else if (this.administratorService.checkPass(companyForm.getPassword(), companyForm.getConfirmPass()) == false) {
			binding.rejectValue("password", "error.password");
			result = this.createEditModelAndView(companyForm);
		} else if (binding.hasErrors())
			result = this.createEditModelAndView(companyForm);
		else
			try {
				final CreditCard c = new CreditCard();
				c.setBrandName(companyForm.getBrandName());
				c.setCvv(companyForm.getCvvCode());
				c.setExpirationYear(companyForm.getExpiration());
				c.setHolder(companyForm.getHolderName());
				c.setNumber(companyForm.getNumber());
				cy = this.companyService.reconstruct(companyForm, binding);
				cy.setCreditCard(c);
				this.companyService.save(cy);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				if (binding.hasErrors())
					result = this.createEditModelAndView(companyForm, "error.duplicated");
				result = this.createEditModelAndView(companyForm, "error.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final CompanyForm companyForm) {
		ModelAndView result;
		result = this.createEditModelAndView(companyForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final CompanyForm companyForm, final String messageCode) {

		final ModelAndView result;

		result = new ModelAndView("company/create");
		result.addObject("companyForm", companyForm);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		try{
			Collection<Company> companies = this.companyService.findAll();
			result = new ModelAndView("company/list");
			result.addObject("companies",companies);
			result.addObject("RequestURI","company/list.do");
		}catch (Throwable oops){
			result = new ModelAndView("redirect:/" );
		}
		return result;
	}
}
