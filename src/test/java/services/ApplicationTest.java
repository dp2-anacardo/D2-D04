package services;

import domain.Application;
import domain.Curricula;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

import javax.transaction.Transactional;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationTest extends AbstractTest {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CurriculaService curriculaService;

    //In this test we are testing the requirement 9.3(reject applications).
    //In the negative cases we are testing that accepted applications, rejected applications, pending applications
    //and applications that are managed by another company can not be rejected.
    //Also we are testing that the reject comment that the company must write can not
    //be blank.
    //Sequence coverage: 100%
    //Data coverage: 100%

    @Test
    public void rejectApplicationDriver() {
        final Object testingData[][] = {
                {
                        "company1", "application1", "rejectComment", null
                }, {
                "company1", "application1", "", IllegalArgumentException.class
        }, {
                "company1", "application2", "rejectComment", IllegalArgumentException.class
        }, {
                "company1", "application3", "rejectComment", IllegalArgumentException.class
        }, {
                "company1", "application4", "rejectComment", IllegalArgumentException.class
        }, {
                "company2", "application1", "rejectComment", IllegalArgumentException.class
        }
        };
        for (int i = 0; i < testingData.length; i++)
            this.rejectParadeTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
    }

    private void rejectParadeTemplate(final String username, final String application, final String rejectComment, final Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(username);

            final int applicationId = super.getEntityId(application);
            final Application a = this.applicationService.findOne(applicationId);
            a.setRejectComment(rejectComment);
            this.applicationService.rejectApplication(a);
            this.applicationService.saveCompany(a);

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);

    }

    //In this test we are testing the requirement 9.3(accept applications).
    //In the negative cases we are testing that accepted applications, rejected applications, pending applications
    //and applications that are managed by another company can not be accepted.
    //Sequence coverage: 100%
    //Data coverage: Not applicable

    @Test
    public void acceptParadeDriver() {
        final Object testingData[][] = {
                {
                        "company1", "application1", null
                }, {
                "company1", "application2", IllegalArgumentException.class
        }, {
                "company1", "application3", IllegalArgumentException.class
        }, {
                "company1", "application4", IllegalArgumentException.class
        }, {
                "company2", "application1", IllegalArgumentException.class
        }
        };
        for (int i = 0; i < testingData.length; i++)
            this.acceptParadeTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
    }

    private void acceptParadeTemplate(final String username, final String application, final Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(username);

            final int applicationId = super.getEntityId(application);
            final Application a = this.applicationService.findOne(applicationId);
            this.applicationService.acceptApplication(a);

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);

    }

    //In this test we are testing the requirement 10.1(creating applications).
    //In the negative cases we are testing that rookies can not create an application if they do not
    //select a curricula, and they can not create an application in a position that is cancelled or final.
    //Sequence coverage: 100%
    //Data coverage: 100%

    @Test
    public void createApplicationDriver() {
        final Object testingData[][] = {
                {
                        "rookie1", "position1", "curricula2Rookie1", null
                }, {
                "rookie1", "position1", "", AssertionError.class
                }, {
                "rookie1", "position2", "curricula2Rookie1", IllegalArgumentException.class
                }, {
                "rookie1", "position3", "curricula2Rookie1", IllegalArgumentException.class
        }
        };
        for (int i = 0; i < testingData.length; i++)
            this.createApplicationTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
    }

    private void createApplicationTemplate(final String username, final String position, final String curricula, final Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(username);

            final int positionId = super.getEntityId(position);
            final int curriculaId = super.getEntityId(curricula);
            final Curricula c = this.curriculaService.findOne(curriculaId);
            Application application = this.applicationService.create();
            application.setCurricula(c);
            this.applicationService.saveRookie(application, positionId);

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);

    }

    //In this test we are testing the requirement 10.1(updating applications).
    //In the negative cases we are testing that rookies can not update an application if they do not
    //write an explanation or a link, and they can not update an application that is accepted, rejected or submitted.
    //Sequence coverage: 100%
    //Data coverage: 100%

    @Test
    public void updateApplicationDriver() {
        final Object testingData[][] = {
                {
                        "rookie2", "application4", "explanation","http://www.link.com", null
                }, {
                "rookie2", "application4", "", "", IllegalArgumentException.class
        }, {
                "rookie2", "application1", "explanation", "http://www.link.com", IllegalArgumentException.class
        }, {
                "rookie1", "application2", "explanation", "http://www.link.com", IllegalArgumentException.class
        }, {
                "rookie1", "application3", "explanation", "http://www.link.com", IllegalArgumentException.class
        }
        };
        for (int i = 0; i < testingData.length; i++)
            this.updateApplicationTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
    }

    private void updateApplicationTemplate(final String username, final String application, final String explanation, String link, final Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(username);

            final int applicationId = super.getEntityId(application);
            Application a = this.applicationService.findOne(applicationId);
            a.setExplanation(explanation);
            a.setLink(link);
            this.applicationService.saveRookieUpdate(a);

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);

    }

}
