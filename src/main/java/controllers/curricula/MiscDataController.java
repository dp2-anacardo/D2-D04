
package controllers.curricula;

import controllers.AbstractController;
import domain.MiscData;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.MiscDataService;
import services.RookieService;

import javax.validation.ValidationException;

@Controller
@RequestMapping("miscData/rookie")
public class MiscDataController extends AbstractController {

	@Autowired
	private MiscDataService	miscDataService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private RookieService	rookieService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		ModelAndView result;
		try {
			final MiscData p = this.miscDataService.create();
			result = this.createEditModelAndView(p, null, curriculaId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int curriculaId, @RequestParam final int miscDataId) {
		ModelAndView result;
		try {
			final MiscData e = this.miscDataService.findOne(miscDataId);
			result = this.createEditModelAndView(e, null, curriculaId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("miscD") MiscData miscData, @RequestParam final int curriculaId, final BindingResult binding) {
		ModelAndView result;
		try {
			miscData = this.miscDataService.reconstruct(miscData, binding);
			miscData = this.miscDataService.save(miscData, curriculaId);
			result = new ModelAndView("redirect:/curricula/rookie/list.do");
		} catch (final ValidationException ex) {
			result = this.createEditModelAndView(miscData, null, curriculaId);
			for (final ObjectError e : binding.getAllErrors())
				if (e.getDefaultMessage().equals("URL incorrecta") || e.getDefaultMessage().equals("Invalid URL"))
					result.addObject("attachmentError", e.getDefaultMessage());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscData, "curricula.commit.error", curriculaId);
		}
		return result;
	}

	private ModelAndView createEditModelAndView(final MiscData p, final String messageCode, final int curriculaId) {
		ModelAndView result;

		result = new ModelAndView("miscData/rookie/edit");
		result.addObject("miscD", p);
		result.addObject("message", messageCode);
		result.addObject("curriculaId", curriculaId);

		return result;

	}
}
