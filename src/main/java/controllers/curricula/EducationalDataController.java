
package controllers.curricula;

import controllers.AbstractController;
import domain.EducationalData;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.EducationalDataService;
import services.RookieService;

import javax.validation.ValidationException;

@Controller
@RequestMapping("/educationalData/rookie")
public class EducationalDataController extends AbstractController {

	@Autowired
	private EducationalDataService	educationalDataService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private RookieService			rookieService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		ModelAndView result;
		try {
			final EducationalData e = this.educationalDataService.create();
			result = this.createEditModelAndView(e, null, curriculaId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int curriculaId, @RequestParam final int educationalDataId) {
		ModelAndView result;
		try {
			final EducationalData e = this.educationalDataService.findOne(educationalDataId);
			result = this.createEditModelAndView(e, null, curriculaId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("educationalD") EducationalData educationalData, @RequestParam final int curriculaId, final BindingResult binding) {
		ModelAndView result;
		try {
			educationalData = this.educationalDataService.reconstruct(educationalData, binding);
			educationalData = this.educationalDataService.save(educationalData, curriculaId);
			result = new ModelAndView("redirect:/curricula/rookie/list.do");
		} catch (final ValidationException e) {
			result = this.createEditModelAndView(educationalData, null, curriculaId);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(educationalData, "curricula.commit.error", curriculaId);
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int educationalDataId) {
		ModelAndView result;
		try {
			final EducationalData e = this.educationalDataService.findOne(educationalDataId);
			this.educationalDataService.delete(e);
			result = new ModelAndView("redirect:/curricula/rookie/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	private ModelAndView createEditModelAndView(final EducationalData p, final String messageCode, final int curriculaId) {
		ModelAndView result;

		result = new ModelAndView("educationalData/rookie/edit");
		result.addObject("educationalD", p);
		result.addObject("message", messageCode);
		result.addObject("curriculaId", curriculaId);

		return result;

	}
}
