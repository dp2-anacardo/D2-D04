package services;

import domain.Actor;
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
public class CalculateSpamAndBanTest extends AbstractTest {

    @Autowired
    private ActorService actorService;

    @Autowired
    private AdministratorService administratorService;

    /*
     * Testing functional requirement : 24.2 An actor who is authenticated as an administrator must be able to launch a process that flags the actor
     * of the system as spammerr or not-spammers
     * Positive: An admin launch the process
     * Negative: A rookie tries to launch the process
     * Sentence coverage: 70.9%
     * Data coverage: Not applicable
     */

    @Test
    public void calculateSpamDriver() {
        Object testingData[][] = {
                {
                        "admin1", null
                }, {
                "rookie1", IllegalArgumentException.class
        }
        };
        for (int i = 0; i < testingData.length; i++) {
            this.calculateSpamTemplate((String) testingData[i][0],
                     (Class<?>) testingData[i][1]);
        }
    }

    private void calculateSpamTemplate(String user, Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(user);
            this.administratorService.computeAllSpam();
        } catch (Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);
    }

    /*
     * Testing functional requirement : 24.3 An actor who is authenticated as an administrator must be able to ban an actor with the spammer flag
     * Positive: An administrator bans a rookie with spammer flag
     * Negative: An administrator tries to bans a rookie with not-spammer flag
     * Sentence coverage: 93.1%
     * Data coverage: Not applicable
     */

    @Test
    public void banDriver() {
        Object testingData[][] = {
                {
                        "admin1", "rookie1", null
                }, {
                "admin1", "rookie2", IllegalArgumentException.class
        }
        };
        for (int i = 0; i < testingData.length; i++) {
            this.banTemplate((String) testingData[i][0], (String) testingData[i][1],
                    (Class<?>) testingData[i][2]);
        }
    }

    private void banTemplate(String admin, String user,  Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(admin);
            final Actor actor = this.actorService.findOne(super.getEntityId(user));
            this.administratorService.ban(actor);
        } catch (Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);
    }

    /*
     * Testing functional requirement : 24.3 An actor who is authenticated as an administrator must be able to unban an actor who was
     * previously banned.
     * Positive: An administrator unbans an actor who was previously banned
     * Negative: An administrator tries to unban an acto who is not banned.
     * Sentence coverage: 100%
     * Data coverage: Not applicable
     */

    @Test
    public void unbanDriver() {
        Object testingData[][] = {
                {
                        "admin1", "rookie1", "rookie1", null
                }, {
                "admin1", "rookie1","rookie2", IllegalArgumentException.class
        }
        };
        for (int i = 0; i < testingData.length; i++) {
            this.unbanTemplate((String) testingData[i][0], (String) testingData[i][1],
                    (String) testingData[i][2], (Class<?>) testingData[i][3]);
        }
    }

    private void unbanTemplate(String admin, String ban, String unban,  Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(admin);
            final Actor toBan = this.actorService.findOne(super.getEntityId(ban));
            final Actor toUnban = this.actorService.findOne(super.getEntityId(unban));
            this.administratorService.ban(toBan);
            this.administratorService.unban(toUnban);
        } catch (Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);
    }

}
