
package controllers.curricula;

import controllers.AbstractController;
import domain.PositionData;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.PositionDataService;
import services.RookieService;

import javax.validation.ValidationException;

@Controller
@RequestMapping("/positionData/rookie")
public class PositionDataController extends AbstractController {

	@Autowired
	private PositionDataService	positionDataService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private RookieService		rookieService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		ModelAndView result;
		try {
			final PositionData p = this.positionDataService.create();
			result = this.createEditModelAndView(p, null, curriculaId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int curriculaId, @RequestParam final int positionDataId) {
		ModelAndView result;
		try {
			final PositionData e = this.positionDataService.findOne(positionDataId);
			result = this.createEditModelAndView(e, null, curriculaId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("positionD") PositionData positionData, @RequestParam final int curriculaId, final BindingResult binding) {
		ModelAndView result;
		try {
			positionData = this.positionDataService.reconstruct(positionData, binding);
			positionData = this.positionDataService.save(positionData, curriculaId);
			result = new ModelAndView("redirect:/curricula/rookie/list.do");
		} catch (final ValidationException e) {
			result = this.createEditModelAndView(positionData, null, curriculaId);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(positionData, "curricula.commit.error", curriculaId);
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int positionDataId) {
		ModelAndView result;
		try {
			final PositionData e = this.positionDataService.findOne(positionDataId);
			this.positionDataService.delete(e);
			result = new ModelAndView("redirect:/curricula/rookie/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	private ModelAndView createEditModelAndView(final PositionData p, final String messageCode, final int curriculaId) {
		ModelAndView result;

		result = new ModelAndView("positionData/rookie/edit");
		result.addObject("positionD", p);
		result.addObject("message", messageCode);
		result.addObject("curriculaId", curriculaId);

		return result;

	}

}
