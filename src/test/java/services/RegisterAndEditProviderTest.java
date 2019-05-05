/*
 * SampleTest.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import datatype.CreditCard;
import domain.Company;
import domain.Provider;
import forms.CompanyForm;
import forms.ProviderForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.DataBinder;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegisterAndEditProviderTest extends AbstractTest {

	@Autowired
	private ProviderService	providerService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private CompanyService	companyService;


	/*
	 * Testing functional requirement : requirement 9.3 (provider register)
	 * Positive:An actor who is not authenticated can register as provider
	 * Negative:An actor who is not authenticated can not register in the system
	 * Sentence coverage: 100%
	 * Data coverage: 30%
	 */

	@Test
	public void registerProviderDriver() {
		final Object testingData[][] = {
			{
				"prueba1", "123456", "123456", "prueba1", "prueba", "", "prueba@prueba.com", "600102030", "", 21, "prueba1", "MASTERCARD", "5473259551394900", "2026/10/20", 841, "make", null
			}
			,
				{
				"prueba1", "123456", "123456", "prueba1", "prueba", "", "prueba@prueba.com", "600102030", "", 21, "prueba1", "MASTERCARD", "5473259551394900", "", 841, "make", ParseException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterProvider((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (int) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (Integer) testingData[i][14],
				(String) testingData[i][15], (Class<?>) testingData[i][16]);
	}

	/*
	 * Testing functional requirement : requirement 8.2 [Acme-Hacker-Rank] (provider edit personal data)
	 * Positive:An actor who is authenticated as Provider can edit your personal data
	 * Negative:An actor who is authenticated as Rookie can not edit edit personal data of any provider
	 * Sentence coverage: 80%
	 * Data coverage: 18%
	 */

	@Test
	public void editProviderDriver() {
		final Object testingData[][] = {
			{
				"provider2", "newName", null
			}
			,
				{
				"rookie1", "newName", NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditProvider((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	public void templateRegisterProvider(final String username, final String password, final String comfirmPass, final String name, final String surname, final String photo, final String email, final String phoneNumber, final String address,
		final int vatNumber, final String holderName, final String brandName, final String creditCardNumber, final String expiration, final Integer cvvCode, final String make, final Class<?> expected) {

		Class<?> caught;
		caught = null;
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {

			final Date fecha = sdf.parse(expiration);
			final ProviderForm aForm = new ProviderForm();

			final DataBinder binding = new DataBinder(new Provider());

			aForm.setName(name);
			aForm.setSurname(surname);
			aForm.setUsername(username);
			aForm.setPassword(password);
			aForm.setConfirmPass(comfirmPass);
			aForm.setEmail(email);
			aForm.setAddress(address);
			aForm.setPhoneNumber(phoneNumber);
			aForm.setPhoto(photo);
			aForm.setVatNumber(vatNumber);
			aForm.setMake(make);

			final CreditCard c = new CreditCard();
			c.setBrandName(brandName);
			c.setCvv(cvvCode);
			c.setExpirationYear(fecha);
			c.setHolder(holderName);
			c.setNumber(creditCardNumber);
			final Provider a = this.providerService.reconstruct(aForm, binding.getBindingResult());
			a.setCreditCard(c);

			this.providerService.save(a);

		} catch (final Throwable e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);

	}

	private void templateEditProvider(final String provider, final String newName, final Class<?> class1) {
		Class<?> caught;
		caught = null;
		try {

			super.authenticate(provider);
			final DataBinder binding = new DataBinder(new Provider());
			final Provider c = this.providerService.findOne(this.actorService.getActorLogged().getId());
			c.setName(newName);
			final Provider result = this.providerService.reconstruct(c, binding.getBindingResult());

			this.providerService.save(result);

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(class1, caught);
	}
}
