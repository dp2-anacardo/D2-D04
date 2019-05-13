
package controllers.item;

import controllers.AbstractController;
import domain.Actor;
import domain.Item;
import domain.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.ItemService;
import services.ProviderService;

import javax.validation.ValidationException;
import java.util.Collection;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private ProviderService providerService;



    // List -------------------------------------------------------------
    @RequestMapping(value = "provider/list", method = RequestMethod.GET)
    public ModelAndView list() {
        final ModelAndView result;
        Collection<Item> items;

        try {
            final Actor principal = this.actorService.getActorLogged();
            Assert.isTrue(principal instanceof Provider);
            items = this.itemService.findAllByProvider(principal.getId());
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }

        result = new ModelAndView("item/provider/list");
        result.addObject("items", items);
        result.addObject("requestURI", "item/provider/list.do");

        return result;
    }

    // List Not Logged --------------------------------------------------
    @RequestMapping(value = "/listNotLogged", method = RequestMethod.GET)
    public ModelAndView listNotLogged(@RequestParam int providerId){
        ModelAndView result;
        Collection<Item> items;
        try {
            Provider p = this.providerService.findOne(providerId);
            Assert.notNull(p);
            items = this.itemService.findAllByProvider(providerId);

            result = new ModelAndView("item/listNotLogged");
            result.addObject("items", items);
            result.addObject("RequestURI", "item/listNotLogged.do");
        } catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    // List Not Logged --------------------------------------------------
     @RequestMapping(value = "/listAllNotLogged", method = RequestMethod.GET)
     public ModelAndView listAllNotLogged(){
        ModelAndView result;
        Collection<Item> items;
        try{
            items = this.itemService.findAll();
            Assert.notNull(items);

            result = new ModelAndView("item/listAllNotLogged");
            result.addObject("items", items);
            result.addObject("RequestURI", "item/listAllNotLogged");
        } catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }

        return result;
     }

    // Create -----------------------------------------------------------
    @RequestMapping(value = "provider/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Item item;

        item = this.itemService.create();
        result = this.createModelAndView(item);

        return result;
    }

    // Update ------------------------------------------------------------
    @RequestMapping(value = "provider/update", method = RequestMethod.GET)
    public ModelAndView update(@RequestParam final int itemID) {
        ModelAndView result;
        Item item;

        try {
            final Actor principal = this.actorService.getActorLogged();
            item = this.itemService.findOne(itemID);
            Assert.isTrue(this.itemService.findAllByProvider(principal.getId()).contains(item));
            result = this.updateModelAndView(item);
            return result;
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }
    }

    // Create Save -------------------------------------------------------------
    @RequestMapping(value = "provider/create", method = RequestMethod.POST, params = "save")
    public ModelAndView createSave(@ModelAttribute("item") Item item, final BindingResult binding) {
        ModelAndView result;

        try {
            item = this.itemService.reconstruct(item, binding);
            item = this.itemService.save(item);
            result = new ModelAndView("redirect:list.do");
        } catch (final ValidationException e) {
            result = this.createModelAndView(item, null);
            for (final ObjectError oe : binding.getAllErrors())
                if (oe.getDefaultMessage().equals("URL incorrecta") || oe.getDefaultMessage().equals("Invalid URL")) {
                    FieldError fe = (FieldError) oe;

                    if (fe.getField().equals("links[].link"))
                        result.addObject("linkError", oe.getDefaultMessage());
                    else if (fe.getField().equals("pictures[].link"))
                        result.addObject("pictureError", oe.getDefaultMessage());
                }
        } catch (final Throwable oops) {
            result = this.createModelAndView(item, "item.commit.error");
        }
        return result;
    }

    // Update Save -------------------------------------------------------------
    @RequestMapping(value = "provider/update", method = RequestMethod.POST, params = "update")
    public ModelAndView updateSave(@ModelAttribute("item") Item item, final BindingResult binding) {
        ModelAndView result;
        Item prblm;

        try {
            item = this.itemService.reconstruct(item, binding);
            item = this.itemService.save(item);
            result = new ModelAndView("redirect:list.do");
        } catch (final ValidationException e) {
            result = this.updateModelAndView(item, null);
            for (final ObjectError oe : binding.getAllErrors())
                if (oe.getDefaultMessage().equals("URL incorrecta") || oe.getDefaultMessage().equals("Invalid URL")) {
                    FieldError fe = (FieldError) oe;

                    if (fe.getField().equals("links[].link"))
                        result.addObject("linkError", oe.getDefaultMessage());
                    else if (fe.getField().equals("pictures[].link"))
                        result.addObject("pictureError", oe.getDefaultMessage());
                }
        } catch (final Throwable oops) {
            result = this.updateModelAndView(item, "item.commit.error");
        }
        return result;
    }

    // Display ---------------------------------------
    @RequestMapping(value = "show", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int itemID) {
        ModelAndView result;
        Item item;

        try {
            item = this.itemService.findOne(itemID);
            Assert.notNull(item);
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }

        result = new ModelAndView("item/show");
        result.addObject("item", item);

        return result;
    }

    // Delete GET ------------------------------------------------------
    @RequestMapping(value = "provider/delete", method = RequestMethod.GET)
    public ModelAndView deleteGet(@RequestParam final int itemID) {
        ModelAndView result;
        Item item;
        Collection<Item> items;

        try {
            try {
                final Actor principal = this.actorService.getActorLogged();
                item = this.itemService.findOne(itemID);
                items = this.itemService.findAllByProvider(principal.getId());
                Assert.isTrue(items.contains(item));
            } catch (final Exception e) {
                result = new ModelAndView("redirect:/");
                return result;
            }
            this.itemService.delete(item);
            result = new ModelAndView("redirect:list.do");
        } catch (final Throwable oops) {
            item = this.itemService.findOne(itemID);
            result = this.updateModelAndView(item, "item.commit.error");
        }

        return result;
    }

    // Delete POST ------------------------------------------------------
    @RequestMapping(value = "provider/update", method = RequestMethod.POST, params = "delete")
    public ModelAndView deletePost(@ModelAttribute("item") Item item, final BindingResult binding) {
        ModelAndView result;
        Collection<Item> items;

        try {
            try {
                final Item it = this.itemService.findOne(item.getId());
                this.itemService.delete(it);
                result = new ModelAndView("redirect:list.do");
            } catch (final Exception e) {
                result = new ModelAndView("redirect:/");
                return result;
            }
        } catch (final Throwable oops) {
            result = this.updateModelAndView(item, "item.commit.error");
        }

        return result;
    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createModelAndView(final Item item) {
        ModelAndView result;

        result = this.createModelAndView(item, null);

        return result;
    }

    protected ModelAndView createModelAndView(final Item item, final String message) {
        ModelAndView result;

        result = new ModelAndView("item/provider/create");

        result.addObject("item", item);
        result.addObject("message", message);

        return result;
    }

    protected ModelAndView updateModelAndView(final Item item) {
        ModelAndView result;

        result = this.updateModelAndView(item, null);

        return result;
    }

    protected ModelAndView updateModelAndView(final Item item, final String message) {
        ModelAndView result;

        result = new ModelAndView("item/provider/update");

        result.addObject("item", item);
        result.addObject("message", message);

        return result;
    }

}
