
package services;

import domain.SocialProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.DataBinder;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SocialProfileTest extends AbstractTest {

	@Autowired
	private SocialProfileService socialProfileService;


	/*
	 * Testing functional requirement : 23.1 An actor that is authenticated must be able to manage their social profiles
	 * Positive: A admin create a social profile
	 * Negative: A admin tries to create a social profile with invalid data
	 * Sentence coverage: 100%
	 * Data coverage: 40%
	 */
	@Test
	public void createSocialProfileDriver() {
		final Object testingData[][] = {
			{
				"prueba", "prueba", "http://www.twitter.com", null, "admin1"
			}, {
				"", "prueba", "http://www.twitter.com", ValidationException.class, "admin1"
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.createSocialProfileTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3], (String) testingData[i][4]);
	}

	private void createSocialProfileTemplate(final String s1, final String s2, final String s3, final Class<?> expected, final String s4) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(s4);
			SocialProfile s = this.socialProfileService.create();
			s.setNick(s1);
			s.setSocialNetworkName(s2);
			s.setProfileLink(s3);
			final DataBinder binding = new DataBinder(new SocialProfile());
			s = this.socialProfileService.reconstruct(s, binding.getBindingResult());
			this.socialProfileService.save(s);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	/*
	 * Testing functional requirement : 23.1 An actor that is authenticated must be able to manage their social profiles
	 * Positive: A admin edit a social profile
	 * Negative: A admin tries to edit a social profile of other admin
	 * Sentence coverage: 100%
	 * Data coverage: 40%
	 */
	@Test
	public void editSocialProfileDriver() {
		final Object testingData[][] = {
			{
				"socialProfileAdmin1", "pruebesita", "admin1", null
			}, {
				"socialProfileAdmin1", "pruebesita", "admin2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.editSocialProfileTemplate(super.getEntityId((String) testingData[i][0]), (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	private void editSocialProfileTemplate(final int entityId, final String s1, final String admin, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(admin);
			SocialProfile s = this.socialProfileService.findOne(entityId);
			s.setNick(s1);
			final DataBinder binding = new DataBinder(new SocialProfile());
			s = this.socialProfileService.reconstruct(s, binding.getBindingResult());
			this.socialProfileService.save(s);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Testing functional requirement : 23.1 An actor that is authenticated must be able to manage their social profiles
	 * Positive: A admin delete a social profile
	 * Negative: A admin tries to delete a social profile of other admin
	 * Sentence coverage: 100%
	 * Data coverage: Not applicable
	 */
	@Test
	public void deleteSocialProfileDriver() {
		final Object testingData[][] = {
			{
				"socialProfileAdmin1", "admin2", IllegalArgumentException.class
			}, {
				"socialProfileAdmin1", "admin1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.deleteSocialProfileTemplate(super.getEntityId((String) testingData[i][0]), (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	private void deleteSocialProfileTemplate(final int entityId, final String admin, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(admin);
			final SocialProfile s = this.socialProfileService.findOne(entityId);
			this.socialProfileService.delete(s);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
