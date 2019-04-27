package services;

import domain.Actor;
import domain.Curricula;
import domain.MiscData;
import domain.Rookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.MiscDataRepository;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Collection;

@Service
@Transactional
public class MiscDataService {

    //Managed Repository
    @Autowired
    private MiscDataRepository miscDataRepository;
    //Services
    @Autowired
    private ActorService actorService;
    @Autowired
    private RookieService rookieService;
    @Autowired
    private CurriculaService curriculaService;
    @Autowired
    private Validator validator;

    public MiscData reconstruct(MiscData m, BindingResult binding){
        MiscData result;
        if(m.getId()==0){
            result = this.create();
        }else{
            result = this.miscDataRepository.findOne(m.getId());
        }
        result.setAttachment(m.getAttachment());
        result.setFreeText(m.getFreeText());

        validator.validate(result,binding);
        if(binding.hasErrors())
            throw new ValidationException();
        return result;
    }

    public MiscData create(){
        Actor a = this.actorService.getActorLogged();
        Rookie h = this.rookieService.findOne(a.getId());
        Assert.notNull(h);

        MiscData result = new MiscData();
        return result;
    }

    public Collection<MiscData> findAll(){
        return this.miscDataRepository.findAll();
    }

    public MiscData findOne(int id){
        return this.miscDataRepository.findOne(id);
    }

    public MiscData save(MiscData m,int curriculaId){
        Assert.notNull(m);
        Actor a = this.actorService.getActorLogged();
        Rookie h = this.rookieService.findOne(a.getId());
        Curricula curricula = this.curriculaService.findOne(curriculaId);
        Assert.notNull(h);
        if(m.getId()!=0){
            Assert.isTrue(h.getCurricula().contains(curricula));
        }


        if(m.getId()==0){
            Assert.isTrue(curricula.getMiscData()==null);
            m = this.miscDataRepository.save(m);
            curricula.setMiscData(m);
        }else{
            m = this.miscDataRepository.save(m);
        }
        return m;
    }

    public MiscData save2(MiscData miscData){
        Assert.notNull(miscData);

        MiscData result = miscDataRepository.save(miscData);

        return result;
    }

    public void delete(MiscData m){
        Assert.notNull(m);
        Actor a = this.actorService.getActorLogged();
        Rookie h = this.rookieService.findOne(a.getId());
        Assert.notNull(h);

        this.miscDataRepository.delete(m);
    }
}
