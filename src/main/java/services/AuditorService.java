package services;

import domain.Actor;
import domain.Auditor;
import domain.Message;
import domain.SocialProfile;
import forms.AuditorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.AuditorRepository;
import security.Authority;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class AuditorService {

    @Autowired
    private ConfigurationService	configurationService;

    @Autowired
    private ActorService			actorService;

    @Autowired
    private AuditorRepository auditorRepository;

    @Autowired
    private Validator validator;

    public Auditor create() {

        final Authority auth;
        final UserAccount userAccount;
        final Collection<Authority> authorities;
        final Collection<SocialProfile> profiles;
        final Collection<Message> sent;
        final Collection<Message> received;
        final Auditor a = new Auditor();
        userAccount = new UserAccount();
        auth = new Authority();
        authorities = new ArrayList<>();
        profiles = new ArrayList<>();
        sent = new ArrayList<>();
        received = new ArrayList<>();

        auth.setAuthority(Authority.AUDITOR);
        authorities.add(auth);
        userAccount.setAuthorities(authorities);
        a.setUserAccount(userAccount);
        a.setIsBanned(false);
        a.setIsSpammer(false);
        a.setMessagesReceived(received);
        a.setMessagesSent(sent);
        a.setSocialProfiles(profiles);
        return a;
    }

    public Collection<Auditor> findAll() {
        Collection<Auditor> result;

        result = this.auditorRepository.findAll();

        return result;
    }

    public Auditor findOne(int auditorId){
        Auditor result;

        result = this.auditorRepository.findOne(auditorId);

        return result;
    }

    public Auditor save(final Auditor a) {

        Assert.notNull(a);
        Auditor result;
        final char[] c = a.getPhoneNumber().toCharArray();
        if ((!a.getPhoneNumber().equals(null) && !a.getPhoneNumber().equals("")))
            if (c[0] != '+') {
                final String i = this.configurationService.findAll().get(0).getCountryCode();
                a.setPhoneNumber("+" + i + " " + a.getPhoneNumber());
            }
        if (a.getId() == 0) {
            final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
            final String res = encoder.encodePassword(a.getUserAccount().getPassword(), null);
            a.getUserAccount().setPassword(res);

        }
        result = this.auditorRepository.save(a);
        return result;
    }

    public void delete(final Auditor a) {

        final Actor actor = this.actorService.getActorLogged();
        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("AUDITOR"));
        Assert.notNull(a);

        this.auditorRepository.delete(a);
    }

    public Auditor reconstruct(final Auditor a, final BindingResult binding) {

        Auditor result;
        if (a.getId() == 0) {
            this.validator.validate(a, binding);
            result = a;
        } else {
            result = this.auditorRepository.findOne(a.getId());

            result.setName(a.getName());
            result.setPhoto(a.getPhoto());
            result.setPhoneNumber(a.getPhoneNumber());
            result.setEmail(a.getEmail());
            result.setAddress(a.getAddress());
            result.setVatNumber(a.getVatNumber());
            result.setSurname(a.getSurname());
            this.validator.validate(a, binding);
        }
        return result;
    }

    public Auditor reconstruct(final AuditorForm a, final BindingResult binding) {

        final Auditor result = this.create();
        result.setAddress(a.getAddress());
        result.setEmail(a.getEmail());
        result.setId(a.getId());
        result.setName(a.getName());
        result.setPhoneNumber(a.getPhoneNumber());
        result.setPhoto(a.getPhoto());
        result.setSurname(a.getSurname());
        result.getUserAccount().setPassword(a.getPassword());
        result.getUserAccount().setUsername(a.getUsername());
        result.setVersion(a.getVersion());
        result.setVatNumber(a.getVatNumber());

        this.validator.validate(result, binding);
        return result;
    }

    public void flush(){
        this.auditorRepository.flush();
    }
}
