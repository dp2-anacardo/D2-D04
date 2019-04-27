package services;

import domain.Actor;
import domain.Curricula;
import domain.PositionData;
import domain.Rookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.PositionDataRepository;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Collection;

@Service
@Transactional
public class PositionDataService {

    //Managed Repository
    @Autowired
    private PositionDataRepository positionDataRepository;
    //Services
    @Autowired
    private ActorService actorService;
    @Autowired
    private RookieService rookieService;
    @Autowired
    private CurriculaService curriculaService;
    @Autowired
    private Validator validator;

    public PositionData reconstruct(PositionData p, BindingResult binding){
        PositionData result;
        if(p.getId()==0){
            result = this.create();
        }else{
            result = this.positionDataRepository.findOne(p.getId());
        }
        result.setDescription(p.getDescription());
        result.setEndDate(p.getEndDate());
        result.setStartDate(p.getStartDate());
        result.setTitle(p.getTitle());

        validator.validate(result,binding);

        if(binding.hasErrors())
            throw new ValidationException();
        return result;
    }

    public PositionData create(){
        Actor a = this.actorService.getActorLogged();
        Rookie h = this.rookieService.findOne(a.getId());
        Assert.notNull(h);

        PositionData result = new PositionData();
        return result;
    }

    public Collection<PositionData> findAll(){
        return this.positionDataRepository.findAll();
    }

    public PositionData findOne(int id){
        return this.positionDataRepository.findOne(id);
    }

    public PositionData save(PositionData p, int curriculaId){
        Assert.notNull(p);
        Actor a = this.actorService.getActorLogged();
        Rookie h = this.rookieService.findOne(a.getId());
        Curricula curricula = this.curriculaService.findOne(curriculaId);
        Assert.notNull(h);

        if(p.getEndDate()!=null){
            Assert.isTrue(p.getStartDate().before(p.getEndDate()));
        }

        if(p.getId()==0){
            p = this.positionDataRepository.save(p);
            curricula.getPositionData().add(p);
        }else{
            p = this.positionDataRepository.save(p);
        }
        return p;
    }

    public PositionData save2(PositionData positionData){
        Assert.notNull(positionData);

        PositionData result = this.positionDataRepository.save(positionData);

        return result;
    }

    public void delete(PositionData p){
        Assert.notNull(p);
        Actor a = this.actorService.getActorLogged();
        Rookie h = this.rookieService.findOne(a.getId());
        Assert.notNull(h);

        for (Curricula c : h.getCurricula()){
            if(c.getPositionData().contains(p)) {
                c.getPositionData().remove(p);
                break;
            }
        }
        this.positionDataRepository.delete(p);
    }
}
