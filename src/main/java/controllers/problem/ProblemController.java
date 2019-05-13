
package controllers.problem;

import controllers.AbstractController;
import domain.Actor;
import domain.Company;
import domain.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.ProblemService;

import javax.validation.ValidationException;
import java.util.Collection;

@Controller
@RequestMapping("/problem")
public class ProblemController extends AbstractController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private ActorService actorService;



    // List -------------------------------------------------------------
    @RequestMapping(value = "company/list", method = RequestMethod.GET)
    public ModelAndView list() {
        final ModelAndView result;
        Collection<Problem> problems;

        try {
            final Actor principal = this.actorService.getActorLogged();
            Assert.isTrue(principal instanceof Company);
            problems = this.problemService.findAllByCompany(principal.getId());
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }

        result = new ModelAndView("problem/company/list");
        result.addObject("problems", problems);
        result.addObject("requestURI", "problem/company/list.do");

        return result;
    }

    // Create -----------------------------------------------------------
    @RequestMapping(value = "company/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Problem problem;

        problem = this.problemService.create();
        result = this.createModelAndView(problem);

        return result;
    }

    // Update ------------------------------------------------------------
    @RequestMapping(value = "company/update", method = RequestMethod.GET)
    public ModelAndView update(@RequestParam final int problemID) {
        ModelAndView result;
        Problem problem;

        try {
            final Actor principal = this.actorService.getActorLogged();
            problem = this.problemService.findOne(problemID);
            Assert.isTrue(this.problemService.findAllByCompany(principal.getId()).contains(problem));
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }

        try {
            final Actor principal = this.actorService.getActorLogged();
            problem = this.problemService.findOne(problemID);
            Assert.isTrue(problem.getIsFinal() == false);
            Assert.isTrue(this.problemService.findAllByCompany(principal.getId()).contains(problem));
            result = this.updateModelAndView(problem);
            return result;
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }
    }

    // Create Save -------------------------------------------------------------
    @RequestMapping(value = "company/create", method = RequestMethod.POST, params = "save")
    public ModelAndView createSave(@ModelAttribute("problem") Problem problem, final BindingResult binding) {
        ModelAndView result;

        try {
            problem = this.problemService.reconstruct(problem, binding);
            problem = this.problemService.save(problem);
            result = new ModelAndView("redirect:list.do");
        } catch (final ValidationException e) {
            result = this.createModelAndView(problem, null);
            for (final ObjectError oe : binding.getAllErrors())
                if (oe.getDefaultMessage().equals("URL incorrecta") || oe.getDefaultMessage().equals("Invalid URL"))
                    result.addObject("attachmentError", oe.getDefaultMessage());
        } catch (final Throwable oops) {
            result = this.createModelAndView(problem, "problem.commit.error");
        }
        return result;
    }

    // Update Save -------------------------------------------------------------
    @RequestMapping(value = "company/update", method = RequestMethod.POST, params = "update")
    public ModelAndView updateSave(@ModelAttribute("problem") Problem problem, final BindingResult binding) {
        ModelAndView result;
        Problem prblm;

        try {
            problem = this.problemService.reconstruct(problem, binding);
            problem = this.problemService.save(problem);
            result = new ModelAndView("redirect:list.do");
        } catch (final ValidationException e) {
            result = this.updateModelAndView(problem, null);
            for (final ObjectError oe : binding.getAllErrors())
                if (oe.getDefaultMessage().equals("URL incorrecta") || oe.getDefaultMessage().equals("Invalid URL"))
                    result.addObject("attachmentError", oe.getDefaultMessage());
        } catch (final Throwable oops) {
            result = this.updateModelAndView(problem, "problem.commit.error");
        }
        return result;
    }

    // Display ---------------------------------------
    @RequestMapping(value = "show", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int problemID) {
        ModelAndView result;
        Problem problem;

        try {
            final Actor principal = this.actorService.getActorLogged();
            problem = this.problemService.findOne(problemID);
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }

        result = new ModelAndView("problem/show");
        result.addObject("problem", problem);

        return result;
    }

    // Delete GET ------------------------------------------------------
    @RequestMapping(value = "company/delete", method = RequestMethod.GET)
    public ModelAndView deleteGet(@RequestParam final int problemID) {
        ModelAndView result;
        Problem problem;
        Collection<Problem> problems;

        try {
            try {
                final Actor principal = this.actorService.getActorLogged();
                problem = this.problemService.findOne(problemID);
                problems = this.problemService.findAllByCompany(principal.getId());
                Assert.isTrue(problems.contains(problem));
                Assert.isTrue(problem.getIsFinal() == false);
            } catch (final Exception e) {
                result = new ModelAndView("redirect:/");
                return result;
            }
            this.problemService.delete(problem);
            result = new ModelAndView("redirect:list.do");
        } catch (final Throwable oops) {
            problem = this.problemService.findOne(problemID);
            result = this.updateModelAndView(problem, "problem.commit.error");
        }

        return result;
    }

    // Delete POST ------------------------------------------------------
    @RequestMapping(value = "company/update", method = RequestMethod.POST, params = "delete")
    public ModelAndView deletePost(@ModelAttribute("problem") Problem problem, final BindingResult binding) {
        ModelAndView result;
        Collection<Problem> problems;

        try {
            try {
                this.problemService.delete(problem);
                result = new ModelAndView("redirect:list.do");
            } catch (final Exception e) {
                result = new ModelAndView("redirect:/");
                return result;
            }
        } catch (final Throwable oops) {
            result = this.updateModelAndView(problem, "problem.commit.error");
        }

        return result;
    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createModelAndView(final Problem problem) {
        ModelAndView result;

        result = this.createModelAndView(problem, null);

        return result;
    }

    protected ModelAndView createModelAndView(final Problem problem, final String message) {
        ModelAndView result;

        result = new ModelAndView("problem/company/create");

        result.addObject("problem", problem);
        result.addObject("message", message);

        return result;
    }

    protected ModelAndView updateModelAndView(final Problem problem) {
        ModelAndView result;

        result = this.updateModelAndView(problem, null);

        return result;
    }

    protected ModelAndView updateModelAndView(final Problem problem, final String message) {
        ModelAndView result;

        result = new ModelAndView("problem/company/update");

        result.addObject("problem", problem);
        result.addObject("message", message);

        return result;
    }

}
