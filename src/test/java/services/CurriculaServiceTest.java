
package services;

import domain.Curricula;
import domain.PersonalData;
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
public class CurriculaServiceTest extends AbstractTest {

	@Autowired
	private CurriculaService	curriculaService;
	@Autowired
	private PersonalDataService	personalDataService;


	/*
	 * Testing functional requirement : Manage his or her curricula, which includes listing, showing, creating, updating, and deleting them.
	 * Positive: A rookie creates a curricula
	 * Negative: A rookie tries to create a curricula with invalid data.
	 * Sentence coverage: 84%
	 * Data coverage: 33%
	 */

	@Test
	public void createCurriculaDriver() {
		final Object testingData[][] = {
			{
				"prueba", "prueba", "123456789", "https://www.github.com/Prueba", "https://www.linkedIn.com/Prueba", "rookie1", null
			}, {
				"", "prueba", "123456789", "https://www.github.com/Prueba", "https://www.linkedIn.com/Prueba", "rookie1", ValidationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createCurriculaTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	private void createCurriculaTemplate(final String s, final String s1, final String s2, final String s3, final String s4, final String s5, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(s5);
			PersonalData p = this.personalDataService.create();
			p.setFullName(s);
			p.setStatement(s1);
			p.setPhoneNumber(s2);
			p.setGithubProfile(s3);
			p.setLinkedInProfile(s4);
			final DataBinder binding = new DataBinder(new PersonalData());
			p = this.personalDataService.reconstruct(p, binding.getBindingResult());
			this.personalDataService.save(p);
			final Curricula c = this.curriculaService.create();
			c.setPersonalData(p);
			this.curriculaService.save(c);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	/*
	 * Testing functional requirement : Manage his or her curricula, which includes listing, showing, creating, updating, and deleting them.
	 * Positive: A rookie edits a curricula
	 * Negative: A rookie tries to edit a curricula with invalid data.
	 * Sentence coverage: 100%
	 * Data coverage: 33%
	 */
	@Test
	public void editCurriculaDriver() {
		final Object testingData[][] = {
			{
				"prueba", "curricula1Rookie1", "rookie1", null
			}, {
				"", "curricula1Rookie1", "rookie1", ValidationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.editCurriculaTemplate((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	private void editCurriculaTemplate(final String s, final int entityId, final String s1, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(s1);
			PersonalData p = this.curriculaService.findOne(entityId).getPersonalData();
			p.setFullName(s);
			final DataBinder binding = new DataBinder(new PersonalData());
			p = this.personalDataService.reconstruct(p, binding.getBindingResult());
			this.personalDataService.save(p);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	/*
	 * Testing functional requirement : Manage his or her curricula, which includes listing, showing, creating, updating, and deleting them.
	 * Positive: A rookie deletes a curricula
	 * Negative: A rookie tries to delete another rookie's curricula.
	 * Sentence coverage: 100%
	 * Data coverage: Not applicable
	 */
	@Test
	public void deleteCurriculaDriver() {
		final Object testingData[][] = {
			{
				"curricula1Rookie1", "rookie2", IllegalArgumentException.class
			}, {
				"curricula1Rookie1", "rookie1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.deleteCurriculaTemplate(super.getEntityId((String) testingData[i][0]), (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	private void deleteCurriculaTemplate(final int entityId, final String s, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(s);
			final Curricula curricula = this.curriculaService.findOne(entityId);
			this.curriculaService.delete(curricula);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
