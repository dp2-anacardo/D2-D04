
package controllers.curricula;

import controllers.AbstractController;
import domain.*;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import javax.validation.ValidationException;
import java.util.Collection;

@Controller
@RequestMapping("/curricula")
public class CurriculaController extends AbstractController {

	@Autowired
	private CurriculaService	curriculaService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private RookieService		rookieService;

	@Autowired
	private PersonalDataService	personalDataService;

	@Autowired
	private CompanyService		companyService;


	@RequestMapping(value = "/rookie/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Actor a = this.actorService.getActorLogged();
			final Rookie h = this.rookieService.findOne(a.getId());
			Assert.notNull(h);
			final Collection<Curricula> curriculas = this.curriculaService.getCurriculaByRookie();
			result = new ModelAndView("curricula/rookie/list");
			result.addObject("curriculas", curriculas);
			result.addObject("RequestURI", "curricula/rookie/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/rookie/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int curriculaId) {
		ModelAndView result;
		try {
			final Curricula c = this.curriculaService.findOne(curriculaId);
			Assert.notNull(c);
			Assert.isTrue(c.getIsCopy() == false);

			final Actor a = this.actorService.getActorLogged();
			final Rookie h = this.rookieService.findOne(a.getId());
			Assert.notNull(h);
			Assert.isTrue(h.getCurricula().contains(c));
			result = new ModelAndView("curricula/rookie/display");
			result.addObject("curricula", c);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/company/display", method = RequestMethod.GET)
	public ModelAndView showCompany(@RequestParam final int curriculaId) {
		ModelAndView result;
		try {
			final Curricula c = this.curriculaService.findOne(curriculaId);
			Assert.notNull(c);

			final Actor a = this.actorService.getActorLogged();
			final Company company = this.companyService.findOne(a.getId());
			Assert.notNull(company);
			result = new ModelAndView("curricula/company/display");
			result.addObject("curricula", c);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/rookie/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final PersonalData p = this.personalDataService.create();
			result = this.createEditModelAndView(p, null);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	@RequestMapping(value = "/rookie/create", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("personalD") PersonalData p, final BindingResult binding) {
		ModelAndView result;
		try {
			p = this.personalDataService.reconstruct(p, binding);
			p = this.personalDataService.save(p);
			Curricula c = this.curriculaService.create();
			c.setPersonalData(p);
			c = this.curriculaService.save(c);
			result = new ModelAndView("redirect:list.do");
		} catch (final ValidationException e) {
			result = this.createEditModelAndView(p, null);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(p, "curricula.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/rookie/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int curriculaId) {
		ModelAndView result;
		try {
			final Curricula c = this.curriculaService.findOne(curriculaId);
			this.curriculaService.delete(c);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	private ModelAndView createEditModelAndView(final PersonalData p, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curricula/rookie/create");
		result.addObject("personalD", p);
		result.addObject("messageCode", messageCode);

		return result;

	}
}
