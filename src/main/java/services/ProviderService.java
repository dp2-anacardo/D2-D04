
package services;

import domain.Actor;
import domain.Message;
import domain.Provider;
import domain.SocialProfile;
import forms.ProviderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.ProviderRepository;
import security.Authority;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class ProviderService {

	//Managed Repositories
	@Autowired
	private ActorService			actorService;
	//Supporting services
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private ProviderRepository		providerRepository;
	@Autowired
	private Validator				validator;


	public Provider create() {

		final Authority auth;
		final UserAccount userAccount;
		final Collection<Authority> authorities;
		final Collection<SocialProfile> profiles;
		final Collection<Message> sent;
		final Collection<Message> received;
		final Provider a = new Provider();
		userAccount = new UserAccount();
		auth = new Authority();
		authorities = new ArrayList<>();
		profiles = new ArrayList<>();
		sent = new ArrayList<>();
		received = new ArrayList<>();

		auth.setAuthority(Authority.PROVIDER);
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

	public Collection<Provider> findAll() {
		Collection<Provider> result;

		result = this.providerRepository.findAll();

		return result;
	}

	public Provider findOne(final int providerId) {
		Assert.isTrue(providerId != 0);

		Provider result;

		result = this.providerRepository.findOne(providerId);

		return result;
	}

	public Provider save(final Provider p) {

		Assert.notNull(p);
		Provider result;
		final char[] c = p.getPhoneNumber().toCharArray();
		if ((!p.getPhoneNumber().equals(null) && !p.getPhoneNumber().equals("")))
			if (c[0] != '+') {
				final String i = this.configurationService.findAll().get(0).getCountryCode();
				p.setPhoneNumber("+" + i + " " + p.getPhoneNumber());
			}
		if (p.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String res = encoder.encodePassword(p.getUserAccount().getPassword(), null);
			p.getUserAccount().setPassword(res);

		}
		result = this.providerRepository.save(p);
		return result;
	}

	public void delete(final Provider p) {

		final Actor actor = this.actorService.getActorLogged();
		Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("PROVIDER"));
		Assert.notNull(p);

		this.providerRepository.delete(p);
	}

	public Provider reconstruct(final Provider p, final BindingResult binding) {

		Provider result;
		if (p.getId() == 0) {
			this.validator.validate(p, binding);
			result = p;
		} else {
			result = this.providerRepository.findOne(p.getId());

			result.setName(p.getName());
			result.setPhoto(p.getPhoto());
			result.setPhoneNumber(p.getPhoneNumber());
			result.setEmail(p.getEmail());
			result.setAddress(p.getAddress());
			result.setVatNumber(p.getVatNumber());
			result.setSurname(p.getSurname());
			result.setMake(p.getMake());
			this.validator.validate(p, binding);
		}
		return result;
	}

	public Provider reconstruct(final ProviderForm a, final BindingResult binding) {

		final Provider result = this.create();
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
		result.setMake(a.getMake());

		this.validator.validate(result, binding);
		return result;
	}

}
