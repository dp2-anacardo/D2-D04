
package services;

import domain.Actor;
import domain.Message;
import domain.Provider;
import domain.Sponsorship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.SponsorshipRepository;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class SponsorshipService {

    // Manage Repository
    @Autowired
    private SponsorshipRepository sponsorshipRepository;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private Validator validator;


    // CRUD methods
    public Sponsorship create() {
        final Sponsorship result = new Sponsorship();

        return result;
    }

    public Sponsorship findOne(final int sponsorshipID) {
        final Sponsorship result = this.sponsorshipRepository.findOne(sponsorshipID);
        Assert.notNull(result);

        return result;
    }

    public Collection<Sponsorship> findAll() {
        final Collection<Sponsorship> result = this.sponsorshipRepository.findAll();
        Assert.notNull(result);

        return result;
    }

    public Sponsorship save(final Sponsorship sponsorship) {
        Assert.notNull(sponsorship);
        final Actor principal = this.actorService.getActorLogged();
        Assert.isInstanceOf(Provider.class, principal);

        final Sponsorship result = this.sponsorshipRepository.save(sponsorship);

        return result;
    }

    public void delete(final Sponsorship sponsorship) {
        Assert.notNull(sponsorship);

        final Provider p = (Provider) this.actorService.getActorLogged();
        Assert.isTrue(sponsorship.getProvider().equals(p));

        this.sponsorshipRepository.delete(sponsorship);
    }

    public void deleteForced(final Sponsorship sponsorship) {
        Assert.notNull(sponsorship);
        this.sponsorshipRepository.delete(sponsorship);
    }

    // Other business methods

    public Sponsorship reconstruct(final Sponsorship sponsorship, final BindingResult binding) {
        Sponsorship result;

        if (sponsorship.getId() == 0) {
            result = sponsorship;
            result.setProvider((Provider) this.actorService.getActorLogged());

        } else
            result = this.sponsorshipRepository.findOne(sponsorship.getId());

        result.setBanner(sponsorship.getBanner());
        result.setCreditCard(sponsorship.getCreditCard());

        this.validator.validate(result, binding);

        if (binding.hasErrors()) {
            throw new ValidationException();
        }

        return result;

    }

    public List<Sponsorship> findAllByPosition(final int positionID) {
        Assert.notNull(positionID);

        List<Sponsorship> sponsorships = new ArrayList<Sponsorship>();
        try {
            sponsorships = this.sponsorshipRepository.findAllByPosition(positionID);

        } catch (final Throwable e) {
            e.getCause();
        }
        return sponsorships;
    }

    public Collection<Sponsorship> findAllByProvider(final int providerID) {
        Assert.notNull(providerID);

        Collection<Sponsorship> sponsorships = new ArrayList<Sponsorship>();
        try {
            sponsorships = this.sponsorshipRepository.findAllByProvider(providerID);

        } catch (final Throwable e) {
            e.getCause();
        }
        return sponsorships;
    }

    public Sponsorship showSponsorship(final int positionID) {
        Sponsorship result = null;
        final List<Sponsorship> sponsorships = this.findAllByPosition(positionID);
        if (sponsorships.size() > 0) {
            final Random rnd = new Random();
            result = sponsorships.get(rnd.nextInt(sponsorships.size()));

            final Double vat = this.configurationService.getConfiguration().getDefaultVAT();
            final Double fee = this.configurationService.getConfiguration().getFlatRate();
            final Double totalAmount = (vat / 100) * fee + fee;
            final Message message = this.messageService.create();
            final Collection<String> tags = new ArrayList<String>();
            message.setRecipient(result.getProvider());
            tags.add("NOTIFICATION");
            message.setTags(tags);
            message.setSubject("A sponsorship has been shown \n Se ha mostrado un anuncio");
            message.setBody("Se le cargará un importe de: " + totalAmount + "\n Se le cargará un importe de:" + totalAmount);
            this.messageService.save(message);
        }

        return result;
    }
}


