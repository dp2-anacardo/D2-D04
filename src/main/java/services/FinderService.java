package services;

import domain.Configuration;
import domain.Finder;
import domain.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.FinderRepository;

import java.util.*;

@Service
@Transactional
public class FinderService {

    //Managed repository
    @Autowired
    private FinderRepository finderRepository;

    //Services
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private  RookieService rookieService;

    //Validator
    @Autowired
    private Validator validator;


    //Simple CRUD Methods
    public Finder create() {
        Finder result;
        result = new Finder();
        final Collection<Position> positions = new ArrayList<>();
        result.setLastUpdate(new Date());
        result.setPositions(positions);
        return result;
    }

    public Collection<Finder> findAll() {
        return this.finderRepository.findAll();
    }

    public Finder findOne(final Integer id) {
        return this.finderRepository.findOne(id);
    }

    public Finder save(Finder finder) {

        Assert.notNull(finder);
        if(finder.getId()!=0) {
            Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));
            Assert.isTrue(this.rookieService.findOne(this.actorService.getActorLogged().getId()).getFinder().equals(finder));
        }

        Collection<Position> result = this.search(finder);

        Configuration conf;
        conf = this.configurationService.getConfiguration();

        result = this.maxPosition(result, conf);

        finder.setPositions(result);
        final Date moment = new Date();
        finder.setLastUpdate(moment);

        finder = this.finderRepository.save(finder);
        return finder;
    }

    //Reconstruct
    public Finder reconstruct(final Finder finder, final BindingResult binding) {
        Finder result;

        result = this.finderRepository.findOne(finder.getId());

        finder.setVersion(result.getVersion());

        result = finder;
        this.validator.validate(finder, binding);

        return result;
    }

    //Another methods

    public void delete(final Finder f) {
        Assert.notNull(f);
        this.finderRepository.delete(f);
    }

    public Collection<Position> getPositionsByKeyWord(String keyword) {
        return this.finderRepository.getPositionsByKeyWord(keyword);
    }

    public Collection<Position> getPositionsContainsKeyWord(String keyword) {
        return this.finderRepository.getPositionsContainsKeyWord(keyword);
    }

    public Collection<Position> maxPosition(Collection<Position> result, Configuration configuration) {
        if (result.size() > configuration.getMaxResults()) {

            final List<Position> copy = (List<Position>) result;

            final List<Position> paradesLim = new ArrayList<>();
            for (int i = 0; i < configuration.getMaxResults(); i++)
                paradesLim.add(copy.get(i));
            result = paradesLim;
        }

        return result;
    }

    public Collection<Position> search(Finder finder) {
        Collection<Position> result = Collections.emptyList();
        List<Position> pro1 = null;
        List<Position> pro2 = null;
        List<Position> pro3 = null;
        List<Position> pro4 = null;
        List<Position> proAux1;
        List<Position> proAux2;

        if (!(finder.getKeyWord() == null || finder.getKeyWord().equals(""))) {
            proAux1 = (List<Position>) this.finderRepository.getPositionsByKeyWord(finder.getKeyWord());
            proAux2 = (List<Position>) this.finderRepository.getPositionsContainsKeyWord(finder.getKeyWord());

            Set<Position> set = new LinkedHashSet<>(proAux1);
            set.addAll(proAux2);
            pro1 = new ArrayList<>(set);
        }
        if (finder.getDeadline() != null)
            pro2 = (List<Position>) this.finderRepository.getPositionsByDeadline(finder.getDeadline());
        if (finder.getMaxDeadline() != null)
            pro3 = (List<Position>) this.finderRepository.getPositionsUntilDeadline(new Date(), finder.getMaxDeadline());
        if (finder.getMinSalary() != null)
            pro4 = (List<Position>) this.finderRepository.getPositionsByMinSalary(finder.getMinSalary());
        if (!(pro1 == null && pro2 == null && pro3 == null && pro4 == null)) {
            if (pro1 == null)
                pro1 = (List<Position>) this.finderRepository.findAllFinal();
            if (pro2 == null)
                pro2 = (List<Position>) this.finderRepository.findAllFinal();
            if (pro3 == null)
                pro3 = (List<Position>) this.finderRepository.findAllFinal();
            if (pro4 == null)
                pro4 = (List<Position>) this.finderRepository.findAllFinal();
            pro1.retainAll(pro2);
            pro1.retainAll(pro3);
            pro1.retainAll(pro4);

            result = pro1;
        }
        return result;
    }

    public Collection<Finder> findAllByPosition(final int positionId){

        Collection<Finder> result;

        result = this.finderRepository.findAllByPosition(positionId);

        return result;
    }
}
