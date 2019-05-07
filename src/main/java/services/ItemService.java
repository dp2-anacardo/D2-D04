package services;

import datatype.Url;
import domain.Actor;
import domain.Item;
import domain.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.ItemRepository;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class ItemService {

    //Managed Repository
    @Autowired
    private ItemRepository itemRepository;
    //Services
    @Autowired
    private ActorService actorService;

    @Autowired
    private Validator validator;

    public Item reconstruct(Item m, BindingResult binding) {
        Item result;
        if (m.getId() == 0) {
            result = this.create();
            result.setProvider((Provider) this.actorService.getActorLogged());
        } else {
            result = this.itemRepository.findOne(m.getId());
        }
        result.setName(m.getName());
        result.setDescription(m.getDescription());
        result.setLinks(m.getLinks());
        result.setPictures(m.getPictures());

        validator.validate(result, binding);
        if (binding.hasErrors())
            throw new ValidationException();
        return result;
    }

    public Item create() {
        Actor a = this.actorService.getActorLogged();
        Assert.isTrue(a instanceof Provider);

        Item result = new Item();

        Collection<Url> links = new ArrayList<Url>();
        Collection<Url> pictures = new ArrayList<Url>();

        result.setPictures(pictures);
        result.setLinks(links);

        return result;
    }

    public Collection<Item> findAll() {
        return this.itemRepository.findAll();
    }

    public Item findOne(int id) {
        return this.itemRepository.findOne(id);
    }

    public Item save(Item m) {
        Assert.notNull(m);
        Actor a = this.actorService.getActorLogged();
        Assert.isTrue(a instanceof Provider);
        Assert.isTrue(m.getProvider().equals(a));

        m = this.itemRepository.save(m);

        return m;
    }


    public void delete(Item m) {
        Assert.notNull(m);
        Provider a = (Provider) this.actorService.getActorLogged();
        Assert.isTrue(m.getProvider().equals(a));

        this.itemRepository.delete(m);
    }

    public Collection<Item> findAllByProvider(final int providerID) {
        final Collection<Item> result = this.itemRepository.findAllByProvider(providerID);
        Assert.notNull(result);

        return result;
    }

}
