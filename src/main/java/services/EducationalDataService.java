package services;

import domain.Actor;
import domain.Curricula;
import domain.EducationalData;
import domain.Rookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.EducationalDataRepository;
import security.UserAccount;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Collection;

@Service
@Transactional
public class EducationalDataService {

    //Managed repository
    @Autowired
    private EducationalDataRepository educationalDataRepository;
    //Services
    @Autowired
    private ActorService actorService;
    @Autowired
    private RookieService rookieService;
    @Autowired
    private CurriculaService curriculaService;
    @Autowired
    private Validator validator;

    public EducationalData reconstruct(EducationalData educationalData, BindingResult binding){
        EducationalData result;
        if(educationalData.getId()==0){
            result = this.create();
        }else{
            result = this.educationalDataRepository.findOne(educationalData.getId());
        }
        result.setDegree(educationalData.getDegree());
        result.setEndDate(educationalData.getEndDate());
        result.setInstitution(educationalData.getInstitution());
        result.setMark(educationalData.getMark());
        result.setStartDate(educationalData.getStartDate());
        validator.validate(result,binding);
        if(binding.hasErrors())
            throw new ValidationException();
        return result;
    }

    public EducationalData create(){
        UserAccount userAccount;
        userAccount = this.actorService.getActorLogged().getUserAccount();
        Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));

        EducationalData result = new EducationalData();

        return result;
    }

    public Collection<EducationalData> findAll(){
        return this.educationalDataRepository.findAll();
    }

    public EducationalData findOne(int id){
        return this.educationalDataRepository.findOne(id);
    }

    public EducationalData save(EducationalData educationalData,int curriculaId){
        Assert.notNull(educationalData);
        Actor a = this.actorService.getActorLogged();
        Rookie h = this.rookieService.findOne(a.getId());
        Curricula curricula = this.curriculaService.findOne(curriculaId);
        Assert.notNull(h);
        Assert.isTrue(h.getCurricula().contains(curricula));

        if(educationalData.getEndDate()!=null){
            Assert.isTrue(educationalData.getStartDate().before(educationalData.getEndDate()));
        }

        if(educationalData.getId() == 0){
            educationalData = this.educationalDataRepository.save(educationalData);
            curricula.getEducationalData().add(educationalData);
        }else{
            educationalData = this.educationalDataRepository.save(educationalData);
        }
        return educationalData;
    }

    public EducationalData save2(EducationalData educationalData){
        Assert.notNull(educationalData);
            educationalData = this.educationalDataRepository.save(educationalData);
        return educationalData;
    }


    public void delete(EducationalData educationalData){
        Assert.notNull(educationalData);
        Actor a = this.actorService.getActorLogged();
        Rookie h = this.rookieService.findOne(a.getId());
        Assert.notNull(h);

        for(Curricula c : h.getCurricula()){
            if(c.getEducationalData().contains(educationalData)){
                c.getEducationalData().remove(educationalData);
                break;
            }
        }
        this.educationalDataRepository.delete(educationalData);
    }
}
