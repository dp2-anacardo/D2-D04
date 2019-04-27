
package controllers.message;

import controllers.AbstractController;
import domain.Actor;
import domain.Message;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.MessageService;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActorService actorService;

    @ExceptionHandler(TypeMismatchException.class)
    public ModelAndView handleMismatchException(final TypeMismatchException oops) {
        return new ModelAndView("redirect:/");
    }

    // List -------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        final ModelAndView result;
        Collection<Message> messages;

        try {
            final Actor principal = this.actorService.getActorLogged();
            messages = this.messageService.findInPoolByActor(principal.getId());
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }

        ArrayList<Message> msgs = new ArrayList<Message>();
        ArrayList<String> tgs = new ArrayList<String>();

        for (Message m : messages)
            if (m.getTags().size() > 0) {
                for (String t : m.getTags()) {
                    msgs.add(m);
                    tgs.add(t);
                }

            } else {
                msgs.add(m);
                tgs.add("");
            }

        result = new ModelAndView("message/list");
        result.addObject("messages", msgs);
        result.addObject("tgs", tgs);
        result.addObject("requestURI", "message/list.do");

        return result;
    }

    // Create -----------------------------------------------------------
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Message mesage;

        mesage = this.messageService.create();
        result = this.createModelAndView(mesage);

        return result;
    }

    // Create Broadcast ------------------------------------------------------------------------
    @RequestMapping(value = "administrator/broadcast", method = RequestMethod.GET)
    public ModelAndView broadcast() {
        ModelAndView result;
        Message mesage;

        mesage = this.messageService.create();

        result = new ModelAndView("message/administrator/broadcast");
        result.addObject("mesage", mesage);

        return result;
    }

    // Send Broadcast  -------------------------------------------------------------
    @RequestMapping(value = "administrator/broadcast", method = RequestMethod.POST, params = "send")
    public ModelAndView sendBroadcast(@ModelAttribute("mesage") Message mesage, final BindingResult binding) {
        ModelAndView result;

        try {

            mesage = this.messageService.reconstruct(mesage, binding);
            this.messageService.broadcast(mesage);
            result = new ModelAndView("redirect:/message/list.do");
        } catch (final ValidationException e) {
            result = this.createBroadcastModelAndView(mesage, null);
        } catch (final Throwable oops) {
            result = this.createBroadcastModelAndView(mesage, "problem.commit.error");
        }
        return result;
    }

    // Send -------------------------------------------------------------
    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@ModelAttribute("mesage") Message mesage, final BindingResult binding) {
        ModelAndView result;
        Message msg;

        try {

            mesage = this.messageService.reconstruct(mesage, binding);
            mesage = this.messageService.save(mesage);
            result = new ModelAndView("redirect:list.do");
        } catch (final ValidationException e) {
            result = this.createModelAndView(mesage, null);
        } catch (final Throwable oops) {
            result = this.createModelAndView(mesage, "problem.commit.error");
        }
        return result;
    }

    // Display ---------------------------------------
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int messageID) {
        ModelAndView result;
        Message message;
        Collection<Message> pool;

        try {
            final Actor principal = this.actorService.getActorLogged();
            message = this.messageService.findOne(messageID);
            pool = this.messageService.findInPoolByActor(principal.getId());
            Assert.isTrue(pool.contains(message));
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }

        result = new ModelAndView("message/display");
        result.addObject("mesage", message);

        return result;
    }

    // Delete ------------------------------------------------------
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam final int messageID) {
        ModelAndView result;
        Message message;
        Collection<Message> pool;

        try {
            try {
                final Actor principal = this.actorService.getActorLogged();
                message = this.messageService.findOne(messageID);
                pool = this.messageService.findInPoolByActor(principal.getId());
                Assert.isTrue(pool.contains(message));
            } catch (final Exception e) {
                result = new ModelAndView("redirect:/");
                return result;
            }
            this.messageService.delete(message);
            result = new ModelAndView("redirect:list.do");
        } catch (final Throwable oops) {
            message = this.messageService.findOne(messageID);
            result = this.createModelAndView(message, "messageBox.commit.error");
        }

        return result;
    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createModelAndView(final Message mesage) {
        ModelAndView result;

        result = this.createModelAndView(mesage, null);

        return result;
    }

    protected ModelAndView createModelAndView(final Message mesage, final String message) {
        ModelAndView result;

        final Collection<Actor> actorList = this.actorService.findAll();
        actorList.remove(this.actorService.getActorLogged());

        result = new ModelAndView("message/create");
        result.addObject("mesage", mesage);
        result.addObject("message", message);
        result.addObject("actorList", actorList);

        return result;
    }

    protected ModelAndView createBroadcastModelAndView(final Message mesage) {
        ModelAndView result;

        result = this.createBroadcastModelAndView(mesage, null);

        return result;
    }

    protected ModelAndView createBroadcastModelAndView(final Message mesage, final String message) {
        ModelAndView result;

        result = new ModelAndView("message/administrator/broadcast");
        result.addObject("mesage", mesage);
        result.addObject("message", message);

        return result;
    }

}
