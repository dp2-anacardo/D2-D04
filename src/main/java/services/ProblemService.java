
package services;

import datatype.Url;
import domain.Actor;
import domain.Company;
import domain.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.ProblemRepository;
import security.LoginService;
import security.UserAccount;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class ProblemService {

    // Manage Repository
    @Autowired
    private ProblemRepository problemRepository;

    //Supporting devices

    @Autowired
    private ActorService actorService;

    @Autowired
    private Validator validator;


    // CRUD methods
    public Problem create() {
        final Actor actor = this.actorService.getActorLogged();
        Assert.isTrue(actor instanceof Company);

        final Problem result = new Problem();

        final Collection<Url> attachments = new ArrayList<Url>();
        result.setAttachment(attachments);

        return result;
    }

    public Problem findOne(final int problemID) {
        final Problem result = this.problemRepository.findOne(problemID);
        Assert.notNull(result);

        return result;
    }

    public Problem save(final Problem problem) {
        Assert.notNull(problem);
        final Actor actor = this.actorService.getActorLogged();
        Assert.isTrue(actor instanceof Company);
        final Problem result;

        if (problem.getId() == 0)
            result = this.problemRepository.save(problem);
        else {
            Assert.isTrue(problem.getCompany() == actor);
            result = this.problemRepository.save(problem);
        }

        return result;
    }

    public void delete(final Problem problem) {
        final Actor actor = this.actorService.getActorLogged();
        Assert.isTrue(actor instanceof Company);
        Assert.isTrue(problem.getCompany().equals((Company) actor));
        Assert.isTrue(problem.getIsFinal() == false);
        Assert.notNull(problem);
        final UserAccount userAccount = LoginService.getPrincipal();
        Assert.notNull(userAccount);

        this.problemRepository.delete(problem.getId());
    }

    public void deleteForced(final Problem problem){
        Assert.notNull(problem);
        this.problemRepository.delete(problem);
    }

    // Other methods

    public Problem reconstruct(final Problem problem, final BindingResult binding) {
        Problem result;

        if (problem.getId() == 0) {
            final Company actor = (Company) this.actorService.getActorLogged();
            result = this.create();
            result.setCompany(actor);

        } else {
            result = this.problemRepository.findOne(problem.getId());
        }

        result.setStatement(problem.getStatement());
        result.setIsFinal(problem.getIsFinal());
        result.setHint(problem.getHint());
        result.setAttachment(problem.getAttachment());
        result.setTitle(problem.getTitle());

        this.validator.validate(result, binding);

        if (binding.hasErrors()) {
            throw new ValidationException();
        }

        return result;

    }

    public Collection<Problem> findAllByCompany(final int companyID) {
        final Collection<Problem> result = this.problemRepository.findAllByCompany(companyID);
        Assert.notNull(result);

        return result;
    }

    public List<Problem> getProblemsFinalByCompany(final Company company) {
        List<Problem> problems;

        problems = this.problemRepository.getProblemsFinalByCompany(company.getId());

        return problems;
    }
}
