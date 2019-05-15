
package controllers.audit;

import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;
import domain.Position;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.AuditService;
import services.AuditorService;
import services.PositionService;

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

    @ExceptionHandler(BindException.class)
    public ModelAndView handleMismatchException(final BindException oops) {
        return new ModelAndView("redirect:/");
    }

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
        Auditor auditor = this.auditorService.findOne(actorService.getActorLogged().getId());

        try {
            Collection<Auditor> auditors = this.positionService.getAuditorsByPosition(positionId);
            Assert.notNull(positionId);
            position = this.positionService.findOne(positionId);
            Assert.isTrue(position.getIsFinal());
            Assert.isTrue(!position.getIsCancelled());
            Assert.isTrue(!auditors.contains(auditor));
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

      /*  if(StringUtils.isEmpty(audit.getText())) {
            binding.rejectValue("text", "error.text");
            result = new ModelAndView("audit/auditor/create");
            result.addObject("audit",audit);
            result.addObject("positionId", positionId);
        }*/

        try {
            audit = this.auditService.reconstruct(audit, binding);
            this.auditService.saveCreate(audit, positionId);
            result = new ModelAndView("redirect:/audit/auditor/list.do");
        }catch (ValidationException v){
            result = new ModelAndView("audit/auditor/create");
            result.addObject("audit", audit);
            result.addObject("positionId", positionId);
        }catch (Exception oops){
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

    // Update Save -------------------------------------------------------------
    @RequestMapping(value = "auditor/update", method = RequestMethod.POST, params = "update")
    public ModelAndView updateSave(@ModelAttribute("audit") Audit audit, final BindingResult binding) {
        ModelAndView result;
        if(StringUtils.isEmpty(audit.getText())) {
            binding.rejectValue("text", "error.text");
            result = new ModelAndView("audit/auditor/update");
            result.addObject("audit",audit);
        }

        try {
            audit = this.auditService.reconstruct(audit, binding);
            this.auditService.saveUpdate(audit);
            result = new ModelAndView("redirect:/audit/auditor/list.do");
        }catch (ValidationException v){
            result = new ModelAndView("audit/auditor/update");
            result.addObject("audit", audit);
        }catch (Throwable oops){
            result = new ModelAndView("audit/auditor/update");
            result.addObject("audit",audit);
            result.addObject("message","audit.commit.error");
        }
        return result;
    }


    // Delete ------------------------------------------------------
    @RequestMapping(value = "auditor/update", method = RequestMethod.POST, params = "delete")
    public ModelAndView delete(@ModelAttribute("audit") Audit audit) {
        ModelAndView result;

        try {
             this.auditService.delete(audit);
             result = new ModelAndView("redirect:/audit/auditor/list.do");
        } catch (final Throwable oops) {
             result = new ModelAndView("audit/auditor/update");
             result.addObject("audit", audit);
             return result;
        }
        return result;
    }

    @RequestMapping(value="auditor/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam int auditId){
        ModelAndView result;

        try{
            Audit audit = this.auditService.findOne(auditId);
            Collection<Audit> audits = this.auditService.getAuditsByAuditor();
            Assert.isTrue(audits.contains(audit));
            this.auditService.delete(audit);
            result = new ModelAndView("redirect:/audit/auditor/list.do");
        }catch (Throwable oops){
            result = new ModelAndView("redirect:/audit/auditor/list.do");
        }
        return result;
    }
}
