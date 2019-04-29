package services;

import domain.Actor;
import domain.Audit;
import domain.Auditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.AuditRepository;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Collection;

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
    private Validator validator;


    public Audit reconstruct(final Audit audit, final BindingResult binding) {
        Audit result;
        if (audit.getId() == 0) {
            result = this.create();
            result.setMoment(audit.getMoment());
            result.setAuditor(auditorService.findOne(actorService.getActorLogged().getId()));

        } else
            result = this.auditRepository.findOne(audit.getId());
        result.setText(audit.getText());
        result.setScore(audit.getScore());
        result.setIsFinal(audit.getIsFinal());

        this.validator.validate(result, binding);
        if (binding.hasErrors())
            throw new ValidationException();
        return result;
    }

    public Audit create(){
        Assert.isTrue(actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().equals("AUDITOR"));
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

    public Audit save(Audit audit){
        Assert.notNull(audit);
        final Actor actor = this.actorService.getActorLogged();
        Assert.isTrue(actor instanceof Auditor);
        final Audit result;

        if (audit.getId() == 0)
            result = this.auditRepository.save(audit);
        else {
            Assert.isTrue(audit.getAuditor() == actor);
            result = this.auditRepository.save(audit);
        }
        return result;
    }

    public void delete(final Audit audit) {
        final Actor actor = this.actorService.getActorLogged();
        Auditor auditor = auditorService.findOne(actor.getId());
        Assert.isTrue(actor instanceof Auditor);
        Assert.isTrue(audit.getAuditor().equals(auditor));
        Assert.isTrue(audit.getIsFinal() == false);
        Assert.notNull(audit);

        this.auditRepository.delete(audit.getId());
    }

    public Collection<Audit> getAuditsByAuditor(){
        Assert.isTrue(actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().equals("AUDITOR"));
        Collection<Audit> result;
        Auditor auditor = this.auditorService.findOne(actorService.getActorLogged().getId());

        result = this.auditRepository.getAuditsByAuditor(auditor.getId());

        return result;
    }
}
