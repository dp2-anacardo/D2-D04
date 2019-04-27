package services;

import domain.Actor;
import domain.Administrator;
import domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

@Service
@Transactional
public class MessageService {

    // Manage Repository
    @Autowired
    private MessageRepository messageRepository;

    //Supporting devices

    @Autowired
    private ActorService actorService;

    @Autowired
    private Validator validator;


    // CRUD methods
    public Message create() {
        Assert.notNull(this.actorService.getActorLogged());
        final Message result = new Message();
        final Calendar calendar = new GregorianCalendar();
        final Collection<String> tags = new ArrayList<String>();

        result.setTags(tags);
        result.setMoment(calendar.getTime());

        return result;
    }

    public Message findOne(final int messageID) {
        final Message result = this.messageRepository.findOne(messageID);
        Assert.notNull(result);

        return result;
    }

    public Message save(final Message message) {
        Assert.notNull(message);
        Assert.notNull(this.actorService.getActorLogged());
        final Message result;

        Actor sender = null;

        if (message.getId() == 0) {
            Assert.notNull(message);

            final String notification1 = "An application has changed its status.";
            final String notification2 = "A new position matches your criteria.";

            if (message.getSubject().equals(notification1) || message.getSubject().equals(notification2)) {

                final Actor recipient = message.getRecipient();
                Assert.notNull(recipient);

                result = this.messageRepository.save(message);

                recipient.getMessagesReceived().add(result);

            } else {
                final UserAccount userAccount = LoginService.getPrincipal();

                sender = this.actorService.findByUserAccount(userAccount);
                message.setSender(sender);

                final Actor recipient = message.getRecipient();
                Assert.notNull(recipient);

                result = this.messageRepository.save(message);

                if (sender != null)
                    sender.getMessagesSent().add(result);

                recipient.getMessagesReceived().add(result);
            }

        } else
            result = this.messageRepository.save(message);
        return result;
    }

    public void delete(final Message message) {
        Assert.notNull(message);
        final UserAccount userAccount = LoginService.getPrincipal();
        Assert.notNull(userAccount);
        final Actor actor = this.actorService.findByUsername(userAccount.getUsername());

        Assert.isTrue(message.getRecipient().equals(actor) || message.getSender().equals(actor));

        if (message.getSender() != null) {
            final Boolean sender = message.getSender().equals(actor);

            if (!message.getTags().contains("DELETED")) {
                message.getTags().add("DELETED");

                if (sender)
                    actor.getMessagesSent().remove(message);
                else
                    actor.getMessagesReceived().remove(message);

                this.messageRepository.save(message);
            } else {
                if (sender)
                    actor.getMessagesSent().remove(message);
                else
                    actor.getMessagesReceived().remove(message);

                this.messageRepository.delete(message.getId());

            }

        } else {
            actor.getMessagesReceived().remove(message);
            this.messageRepository.delete(message.getId());
        }

    }

    public void deleteForced(Message message){
        Assert.notNull(message);
        message.getSender().getMessagesSent().remove(message);
        message.getRecipient().getMessagesReceived().remove(message);
        this.messageRepository.delete(message);
    }

    public void broadcast(final Message m) {
        final Actor principal = this.actorService.getActorLogged();
        Assert.isTrue(principal instanceof Administrator);

        final Collection<Actor> actors = this.actorService.findAll();

        for (Actor a : actors) {
            Message msg = this.create();
            Message result;

            msg = m;

            msg.setRecipient(a);

            msg.getTags().add("SYSTEM");

            result = this.messageRepository.save(msg);

            a.getMessagesReceived().add(result);
        }
    }

    // Other methods
    public Collection<Message> findInPoolByActor(final int actorID) {
        final Collection<Message> result = new ArrayList<Message>();
        final Collection<Message> received = this.messageRepository.findAllReceivedInPoolByActor(actorID);
        final Collection<Message> sent = this.messageRepository.findAllSentInPoolByActor(actorID);

        Assert.notNull(received);
        result.addAll(received);

        Assert.notNull(sent);
        result.addAll(sent);

        return result;
    }


    public Collection<Message> findAllSentByActor(int actorID) {
        final Collection<Message> result = this.messageRepository.findAllSentByActor(actorID);
        Assert.notNull(result);

        return result;
    }

    public Collection<Message> findAllReceivedByActor(int actorID) {
        final Collection<Message> result = this.messageRepository.findAllReceivedByActor(actorID);
        Assert.notNull(result);

        return result;
    }


    public Message reconstruct(final Message message, final BindingResult binding) {
        final Message result;

        final Actor actor = this.actorService.getActorLogged();

        result = this.create();

        final Calendar calendar = new GregorianCalendar();
        final long time = calendar.getTimeInMillis() - 500;
        calendar.setTimeInMillis(time);

        result.setMoment(calendar.getTime());
        result.setSubject(message.getSubject());
        result.setBody(message.getBody());
        result.setTags(message.getTags());
        result.setRecipient(message.getRecipient());

        this.validator.validate(result, binding);

        if (result.getTags().size() > 0) {
            for (String tag : result.getTags()) {
                if (tag.toUpperCase().equals("DELETED"))
                    binding.rejectValue("tags", "error.tag");
                break;
            }
        }

        if (actor instanceof Administrator) {
            if (!binding.getFieldErrors("recipient").isEmpty() && binding.getErrorCount() > 1)
                throw new ValidationException();
            else if (binding.getFieldErrors("recipient").isEmpty() && binding.hasErrors())
                throw new ValidationException();
        } else if (binding.hasErrors())
            throw new ValidationException();


        return result;

    }
}
