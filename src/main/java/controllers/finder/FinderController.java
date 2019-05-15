package controllers.finder;


import controllers.AbstractController;
import domain.Actor;
import domain.Finder;
import domain.Position;
import domain.Rookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.ConfigurationService;
import services.FinderService;
import services.RookieService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("finder/rookie")
public class FinderController extends AbstractController {

    @Autowired
    private FinderService finderService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private RookieService rookieService;
    @Autowired
    private ConfigurationService configurationService;


    //LIST RESULTS
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Position> positions;
        result = new ModelAndView("finder/rookie/list");

        //Sacar finder del Member
        final Actor user = this.actorService.getActorLogged();
        final Rookie rookie = this.rookieService.findOne(user.getId());

        Finder finder;
        finder = rookie.getFinder();

        //Borrar si fechaLimite < fechaActual
        final Date fechaActual = new Date();
        final Date lastUpdate = finder.getLastUpdate();
        final int horasDeGuardado = this.configurationService.getConfiguration().getMaxTime();
        final Date fechaLimite = new Date(lastUpdate.getTime() + (horasDeGuardado * 3600000L));

        if (fechaActual.after(fechaLimite)) {
            finder.setPositions(new ArrayList<Position>());
            this.finderService.save(finder);
        }

        if (finder.getPositions().isEmpty()) {
            result = new ModelAndView("redirect:/position/listNotLogged.do");

        } else {

            positions = finder.getPositions();

            result.addObject("positions", positions);
            result.addObject("finder", finder);
            result.addObject("requestURI", "finder/rookie/list.do");
        }

        return result;
    }
    //EDIT FINDER
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView result;
        Finder finder;

        final Actor user = this.actorService.getActorLogged();
        final Rookie rookie = this.rookieService.findOne(user.getId());

        finder = rookie.getFinder();
        Assert.notNull(finder);
        result = this.createEditModelAndView(finder);

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@ModelAttribute("finder") Finder finder, final BindingResult binding) {
        ModelAndView result;

        try {
            finder = this.finderService.reconstruct(finder, binding);
            if (binding.hasErrors())
                result = this.createEditModelAndView(finder);
            else {
                this.finderService.save(finder);
                result = new ModelAndView("redirect:/finder/rookie/list.do");
            }
        } catch (final Throwable oops) {
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public ModelAndView clear() {
        final ModelAndView result;

        //Sacar finder del Member
        final Actor user = this.actorService.getActorLogged();
        final Rookie rookie = this.rookieService.findOne(user.getId());

        Finder finder;
        finder = rookie.getFinder();

        finder.setMaxDeadline(null);
        finder.setDeadline(null);
        finder.setMinSalary(null);
        finder.setKeyWord(null);

        this.finderService.save(finder);

        result = new ModelAndView("redirect:/position/listNotLogged.do");

        return result;
    }

    //MODEL AND VIEWS
    protected ModelAndView createEditModelAndView(final Finder finder) {
        ModelAndView result;

        result = this.createEditModelAndView(finder, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("finder/rookie/edit");
        result.addObject("finder", finder);
        result.addObject("messageCode", messageCode);

        return result;
    }
}
