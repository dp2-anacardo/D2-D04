package services;

import domain.Actor;
import domain.Audit;
import domain.Auditor;
import domain.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.AuditRepository;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;
    @Autowired
    private ActorService actorService;
    @Autowired
    private AuditorService auditorService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private Validator validator;


    public Audit reconstruct(final Audit audit, final BindingResult binding) {
        Audit result;
        if (audit.getId() == 0) {
            result = this.create();
            result.setMoment(new Date());
            result.setText(audit.getText());
            result.setScore(audit.getScore());
            result.setIsFinal(audit.getIsFinal());
            result.setAuditor(auditorService.findOne(actorService.getActorLogged().getId()));

        } else {
            result = this.auditRepository.findOne(audit.getId());
            result.setText(audit.getText());
            result.setScore(audit.getScore());
            result.setIsFinal(audit.getIsFinal());
        }

        this.validator.validate(result, binding);
        if (binding.hasErrors())
            throw new ValidationException();
        return result;
    }

    public Audit create(){
        Assert.isTrue(actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("AUDITOR"));
        Audit result;

        result = new Audit();

        return result;
    }

    public Audit findOne(int auditId){
        Audit result;

        result = this.auditRepository.findOne(auditId);

        return result;
    }

    public Collection<Audit> findAll(){
        Collection<Audit> result;

        result = this.auditRepository.findAll();

        return result;
    }

    public Audit saveCreate(Audit audit, int positionId){
        Assert.notNull(audit);
        final Actor actor = this.actorService.getActorLogged();
        Auditor auditor = this.auditorService.findOne(actor.getId());
        final Audit result;
        Collection<Auditor> auditors = this.positionService.getAuditorsByPosition(positionId);
        Assert.isTrue(!auditors.contains(auditor));
        Assert.isTrue(!audit.getText().equals(""));

        result = this.auditRepository.save(audit);
        Position position = this.positionService.findOne(positionId);
        position.getAudits().add(result);

        return result;
    }

    public Audit saveUpdate(Audit audit){
        Assert.notNull(audit);
        final Actor actor = this.actorService.getActorLogged();
        Assert.isTrue(actor instanceof Auditor);
        final Audit result;

        Assert.isTrue(audit.getAuditor() == actor);
        Assert.isTrue(!audit.getText().equals(""));
        result = this.auditRepository.save(audit);

        return result;
    }

    public void delete(final Audit audit) {
        final Actor actor = this.actorService.getActorLogged();
        Auditor auditor = auditorService.findOne(actor.getId());
        Assert.isTrue(actor instanceof Auditor);
        Assert.isTrue(audit.getAuditor().equals(auditor));
        Assert.isTrue(!audit.getIsFinal());
        Assert.notNull(audit);

        Position p = this.getPositionByAudit(audit.getId());
        p.getAudits().remove(audit);

        this.auditRepository.delete(audit.getId());
    }

    public void deleteForced(final Audit audit) {

        Assert.notNull(audit);

        Position p = this.getPositionByAudit(audit.getId());
        p.getAudits().remove(audit);

        this.auditRepository.delete(audit.getId());
    }

    public Collection<Audit> getAuditsByAuditor(){
        Assert.isTrue(actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("AUDITOR"));
        Collection<Audit> result;
        Auditor auditor = this.auditorService.findOne(actorService.getActorLogged().getId());

        result = this.auditRepository.getAuditsByAuditor(auditor.getId());

        return result;
    }

    public Collection<Audit> getAuditsFinalByPosition(int positionId){
        Collection<Audit> result;

        result = this.auditRepository.getAuditsFinalByPosition(positionId);

        return result;
    }

    public Position getPositionByAudit(int auditId){

        Position result;

        result = this.auditRepository.getPositionByAudit(auditId);

        return result;
    }

    public Collection<Audit> getAuditsByPosition(int positionId){
        Collection<Audit> result;

        result = this.auditRepository.getAuditsByPosition(positionId);

        return result;
    }
}
