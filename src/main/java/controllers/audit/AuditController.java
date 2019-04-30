
package controllers.audit;

import controllers.AbstractController;
import domain.*;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import javax.validation.ValidationException;
import java.util.Collection;

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

    @Autowired
    private AuditService auditService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private AuditorService auditorService;

    @Autowired
    private PositionService positionService;

    // List -------------------------------------------------------------
    // Un auditor puede listar todas sus audits ya sea final o no
    @RequestMapping(value = "auditor/list", method = RequestMethod.GET)
    public ModelAndView list() {
        final ModelAndView result;
        Collection<Audit> audits;

        audits = this.auditService.getAuditsByAuditor();

        result = new ModelAndView("audit/auditor/list");
        result.addObject("audits", audits);
        result.addObject("requestURI", "audit/auditor/list.do");

        return result;
    }

    // List de Audits para cualquier actor ------------------------------
    // Cualquier actor puede ver las audits de una position (supongo que son final, en los requisitos no lo pone explicitamente)
    @RequestMapping(value="/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam int positionId){
        ModelAndView result;
        Collection<Audit> audits;
        Position position;

        try{
            Assert.notNull(positionId);
            position = this.positionService.findOne(positionId);
            Assert.notNull(position);
            audits = this.auditService.getAuditsFinalByPosition(positionId);
        }catch(Throwable oops){
            result = new ModelAndView("redirect:/position/listNotLogged.do");
            return result;
        }

        result = new ModelAndView("audit/list");
        result.addObject("audits", audits);
        result.addObject("requestURI", "audit/list.do");
        return result;
    }

    // Show -------------------------------------------------------------
    // Un auditor puede hacer un show de alguna de sus audits
    @RequestMapping(value="auditor/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam int auditId){
        ModelAndView result;
        Audit audit;
        Auditor auditor = auditorService.findOne(actorService.getActorLogged().getId());

        try{
            Assert.notNull(auditId);
            audit = this.auditService.findOne(auditId);
            Assert.isTrue(audit.getAuditor().equals(auditor));
        } catch (Throwable oops){
            result = new ModelAndView("redirect:/");
            return result;
        }

        result = new ModelAndView("audit/auditor/show");
        result.addObject("audit", audit);
        return result;
    }

    // Create -----------------------------------------------------------
    @RequestMapping(value = "auditor/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam int positionId) {
        ModelAndView result;
        Audit audit;
        Position position;

        try {
            Assert.notNull(positionId);
            position = this.positionService.findOne(positionId);
            Assert.notNull(position);
            audit = this.auditService.create();
            result = new ModelAndView("audit/auditor/create");
            result.addObject("audit", audit);
            result.addObject("positionId", positionId);
        }catch(Throwable oops){
            result = new ModelAndView("redirect:/position/listNotLogged.do");
        }
        return result;
    }

    // Create Save -------------------------------------------------------------
    @RequestMapping(value="auditor/create", method = RequestMethod.POST, params ="save")
    public ModelAndView createSave(@ModelAttribute("audit") Audit audit, @RequestParam int positionId, BindingResult binding){
        ModelAndView result;

        if(StringUtils.isEmpty(audit.getText())) {
            binding.rejectValue("text", "error.text");
            result = new ModelAndView("audit/auditor/create");
            result.addObject("audit",audit);
            result.addObject("positionId", positionId);
        }

        try {
            audit = this.auditService.reconstruct(audit, binding);
            this.auditService.save(audit, positionId);
            result = new ModelAndView("redirect:/audit/auditor/list.do");
        }catch (ValidationException v){
            result = new ModelAndView("audit/auditor/create");
            result.addObject("audit", audit);
            result.addObject("positionId", positionId);
        }catch (Throwable oops){
            result = new ModelAndView("audit/auditor/create");
            result.addObject("audit",audit);
            result.addObject("positionId", positionId);
            result.addObject("message","audit.commit.error");
        }
        return result;
    }

    // Update ------------------------------------------------------------
    @RequestMapping(value = "auditor/update", method = RequestMethod.GET)
    public ModelAndView update(@RequestParam int auditId){
        ModelAndView result;
        Audit audit;
        Auditor auditor;

        try{
            audit = this.auditService.findOne(auditId);
            auditor = this.auditorService.findOne(actorService.getActorLogged().getId());
            Assert.isTrue(audit.getAuditor().equals(auditor));
            Assert.isTrue(audit.getIsFinal()==false);
            result = new ModelAndView("audit/auditor/update");
            result.addObject("audit", audit);
        }catch(Throwable oops){
            result = new ModelAndView("redirect:/audit/auditor/list.do");
        }
        return result;
    }
//
//    // Update Save -------------------------------------------------------------
//    @RequestMapping(value = "company/update", method = RequestMethod.POST, params = "update")
//    public ModelAndView updateSave(@ModelAttribute("problem") Problem problem, final BindingResult binding) {
//        ModelAndView result;
//        Problem prblm;
//
//        try {
//            problem = this.problemService.reconstruct(problem, binding);
//            problem = this.problemService.save(problem);
//            result = new ModelAndView("redirect:list.do");
//        } catch (final ValidationException e) {
//            result = this.updateModelAndView(problem, null);
//            for (final ObjectError oe : binding.getAllErrors())
//                if (oe.getDefaultMessage().equals("URL incorrecta") || oe.getDefaultMessage().equals("Invalid URL"))
//                    result.addObject("attachmentError", oe.getDefaultMessage());
//        } catch (final Throwable oops) {
//            result = this.updateModelAndView(problem, "problem.commit.error");
//        }
//        return result;
//    }
//
//    // Display ---------------------------------------
//    @RequestMapping(value = "show", method = RequestMethod.GET)
//    public ModelAndView display(@RequestParam final int problemID) {
//        ModelAndView result;
//        Problem problem;
//
//        try {
//            final Actor principal = this.actorService.getActorLogged();
//            if (principal instanceof Company) {
//                problem = this.problemService.findOne(problemID);
//                Assert.isTrue(this.problemService.findAllByCompany(principal.getId()).contains(problem));
//            } else {
//                //TODO Caso Rookie ?
//            }
//            problem = this.problemService.findOne(problemID);
//        } catch (final Exception e) {
//            result = new ModelAndView("redirect:/");
//            return result;
//        }
//
//        result = new ModelAndView("problem/show");
//        result.addObject("problem", problem);
//
//        return result;
//    }
//
//    // Delete GET ------------------------------------------------------
//    @RequestMapping(value = "company/delete", method = RequestMethod.GET)
//    public ModelAndView deleteGet(@RequestParam final int problemID) {
//        ModelAndView result;
//        Problem problem;
//        Collection<Problem> problems;
//
//        try {
//            try {
//                final Actor principal = this.actorService.getActorLogged();
//                problem = this.problemService.findOne(problemID);
//                problems = this.problemService.findAllByCompany(principal.getId());
//                Assert.isTrue(problems.contains(problem));
//                Assert.isTrue(problem.getIsFinal() == false);
//            } catch (final Exception e) {
//                result = new ModelAndView("redirect:/");
//                return result;
//            }
//            this.problemService.delete(problem);
//            result = new ModelAndView("redirect:list.do");
//        } catch (final Throwable oops) {
//            problem = this.problemService.findOne(problemID);
//            result = this.updateModelAndView(problem, "problem.commit.error");
//        }
//
//        return result;
//    }
//
//    // Delete POST ------------------------------------------------------
//    @RequestMapping(value = "company/update", method = RequestMethod.POST, params = "delete")
//    public ModelAndView deletePost(@ModelAttribute("problem") Problem problem, final BindingResult binding) {
//        ModelAndView result;
//        Collection<Problem> problems;
//
//        try {
//            try {
//                this.problemService.delete(problem);
//                result = new ModelAndView("redirect:list.do");
//            } catch (final Exception e) {
//                result = new ModelAndView("redirect:/");
//                return result;
//            }
//        } catch (final Throwable oops) {
//            result = this.updateModelAndView(problem, "problem.commit.error");
//        }
//
//        return result;
//    }
//
//    // Ancillary methods ------------------------------------------------------
//
//    protected ModelAndView createModelAndView(final Problem problem) {
//        ModelAndView result;
//
//        result = this.createModelAndView(problem, null);
//
//        return result;
//    }
//
//    protected ModelAndView createModelAndView(final Problem problem, final String message) {
//        ModelAndView result;
//
//        result = new ModelAndView("problem/company/create");
//
//        result.addObject("problem", problem);
//        result.addObject("message", message);
//
//        return result;
//    }
//
//    protected ModelAndView updateModelAndView(final Problem problem) {
//        ModelAndView result;
//
//        result = this.updateModelAndView(problem, null);
//
//        return result;
//    }
//
//    protected ModelAndView updateModelAndView(final Problem problem, final String message) {
//        ModelAndView result;
//
//        result = new ModelAndView("problem/company/update");
//
//        result.addObject("problem", problem);
//        result.addObject("message", message);
//
//        return result;
//    }

}
