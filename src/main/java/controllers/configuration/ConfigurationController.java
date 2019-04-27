package controllers.configuration;

import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Configuration;
import forms.ConfigurationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.AdministratorService;
import services.ConfigurationService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("configuration/administrator")
public class ConfigurationController extends AbstractController {

    @Autowired
    private ActorService			actorService;
    @Autowired
    private AdministratorService	administratorService;
    @Autowired
    private ConfigurationService	configurationService;


    // WORD DELETES
    @RequestMapping(value = "/deleteSWord", method = RequestMethod.GET)
    public ModelAndView deleteSWord(@RequestParam(value = "spamWord") final String spamWord) {
        final ModelAndView result;
        final Configuration config = this.configurationService.findAll().get(0);

        final Actor user = this.actorService.getActorLogged();
        final Administrator admin = this.administratorService.findOne(user.getId());
        Assert.notNull(admin);

        final Collection<String> sW = config.getSpamWords();
        sW.remove(spamWord);
        config.setSpamWords(sW);

        this.configurationService.save(config);
        result = new ModelAndView("redirect:/configuration/administrator/edit.do");

        return result;
    }


    // WORD ADDS
    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "addWord")
    public ModelAndView addSW(@ModelAttribute("configF") @Valid final ConfigurationForm configF, final BindingResult binding) {
        ModelAndView result;
        Configuration config;

        final Actor user = this.actorService.getActorLogged();
        final Administrator admin = this.administratorService.findOne(user.getId());
        Assert.notNull(admin);

        config = this.configurationService.reconstructAddWord(configF, binding);

        if (binding.hasErrors()) {

            config = this.configurationService.findAll().get(0);

            configF.setSpamWords(config.getSpamWords());

            result = this.editModelAndView(configF, null);
        } else
            try {
                this.configurationService.save(config);
                result = new ModelAndView("redirect:/configuration/administrator/edit.do");
            } catch (final Throwable oops) {
                result = this.editModelAndView(configF, "configuration.edit.error"); //"Administrator.commit.error"
            }
        return result;
    }

    // EDIT GET & POST
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView result;

        final Actor user = this.actorService.getActorLogged();
        final Administrator admin = this.administratorService.findOne(user.getId());
        Assert.notNull(admin);

        result = this.editModelAndView(this.configurationService.findAll().get(0));

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@ModelAttribute("configF") final ConfigurationForm configF, final BindingResult binding) {
        ModelAndView result;
        Configuration config;

        config = this.configurationService.reconstructEdit(configF, binding);

        configF.setSpamWords(config.getSpamWords());

        if (binding.hasErrors())
            result = this.editModelAndView(configF, null);
        else
            try {
                this.configurationService.save(config);
                result = new ModelAndView("redirect:/configuration/administrator/show.do");
            } catch (final Throwable oops) {
                result = this.editModelAndView(configF, "configuration.edit.error"); //"Administrator.commit.error"
            }
        return result;
    }
    // SHOW/DISPLAY
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView show() {
        ModelAndView result;

        final Configuration config = this.configurationService.findAll().get(0);

        result = new ModelAndView("configuration/administrator/show");
        result.addObject("config", config);

        return result;
    }

    // MODEL&VIEW
    protected ModelAndView editModelAndView(final Configuration config) {
        ModelAndView result;
        final ConfigurationForm configF = new ConfigurationForm(config);
        result = this.editModelAndView(configF, null);
        return result;
    }

    protected ModelAndView editModelAndView(final ConfigurationForm configF, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("configuration/administrator/edit");
        result.addObject("configF", configF);
        result.addObject("messageCode", messageCode);

        return result;
    }

}
