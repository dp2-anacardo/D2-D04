package services;

import domain.Message;
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
public class ManageMessagesTest extends AbstractTest {

    @Autowired
    private ActorService actorService;

    @Autowired
    private MessageService messageService;


    /*
     * Testing functional requirement : 23.2 An actor that is authenticated must be able to manage their messages
     * Positive: An actor sends a message
     * Negative: An actor tries to send a message with invalid data
     * Sentence coverage: 84%
     * Data coverage: 20%
     */

    @Test
    public void createMessageDriver() {
        Object testingData[][] = {
                {
                        "rookie1", "This is the subject 1", "This is the body 1", "TAG1", "company1", null
                }, {
                "rookie2", "", "This is the body 2", "", "company2", ValidationException.class
        }
        };
        for (int i = 0; i < testingData.length; i++) {
            this.createMessageTemplate((String) testingData[i][0], (String) testingData[i][1],
                    (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4]
                    , (Class<?>) testingData[i][5]);
        }
    }

    private void createMessageTemplate(String sender, String subject, String body,
                                       String tag, String recipient, Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(sender);

            Message m = this.messageService.create();
            m.setSender(this.actorService.getActorLogged());
            m.setSubject(subject);
            m.setBody(body);
            m.getTags().add(tag);
            m.setRecipient(this.actorService.findOne(super.getEntityId(recipient)));

            final DataBinder binding = new DataBinder(new Message());
            m = this.messageService.reconstruct(m, binding.getBindingResult());

            this.messageService.save(m);

        } catch (Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);
    }


    /*
     * Testing functional requirement : 23.2 An actor that is authenticated must be able to manage their messages
     * Positive: An actor deletes a message
     * Negative: A company tries to delete a message that not exists
     * Sentence coverage: 75.5%
     * Data coverage: Not applicable
     */

    @Test
    public void deleteMessageDriver() {
        Object testingData[][] = {
                {
                        "rookie1", "message3", null
                }, {
                "rookie2", "message5", AssertionError.class
        }
        };
        for (int i = 0; i < testingData.length; i++) {
            this.deleteMessageTemplate((String) testingData[i][0],
                    (String) testingData[i][1], (Class<?>) testingData[i][2]);
        }
    }

    private void deleteMessageTemplate(String user, String message, Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(user);
            Message m = this.messageService.findOne(super.getEntityId(message));
            this.messageService.delete(m);
        } catch (Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);
    }

    /*
     * Testing functional requirement : 24.1 An actor that is authenticated as a administraotr must be able to broadcast a message
     * Positive: An administrator broadcast a message.
     * Negative: An administrator tries to broadcast a message with invalid fields.
     * Sentence coverage: 92%
     * Data coverage: 50%
     */

    @Test
    public void broadcastDriver() {
        Object testingData[][] = {
                {
                        "admin1", "BR Title 1", "Br Body 1", null
                }, {
                "admin1", "BR Title 1", "", ValidationException.class
        }
        };
        for (int i = 0; i < testingData.length; i++) {
            this.broadcastTemplate((String) testingData[i][0],
                    (String) testingData[i][1], (String) testingData[i][2],
                    (Class<?>) testingData[i][3]);
        }
    }

    private void broadcastTemplate(String user, String title, String body, Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(user);
            Message m = this.messageService.create();
            m.setSubject(title);
            m.setBody(body);

            final DataBinder binding = new DataBinder(new Message());
            m = this.messageService.reconstruct(m, binding.getBindingResult());

            this.messageService.broadcast(m);
        } catch (Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);
    }

}
