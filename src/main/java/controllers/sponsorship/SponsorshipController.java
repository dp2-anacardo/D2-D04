
package controllers.sponsorship;

import controllers.AbstractController;
import domain.Position;
import domain.Provider;
import domain.Sponsorship;
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
import services.PositionService;
import services.SponsorshipService;

import javax.validation.ValidationException;
import java.util.Collection;

@Controller
@RequestMapping("sponsorship")
public class SponsorshipController extends AbstractController {

    @Autowired
    private SponsorshipService sponsorshipService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private PositionService positionService;




    // List -------------------------------------------------------------
    @RequestMapping(value = "/provider/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Sponsorship> sponsorships;

        final Provider principal = (Provider) this.actorService.getActorLogged();

        sponsorships = this.sponsorshipService.findAllByProvider(principal.getId());

        result = new ModelAndView("sponsorship/provider/list");
        result.addObject("sponsorships", sponsorships);
        result.addObject("requestURI", "sponsorship/provider/list.do");

        return result;
    }

    // Create ---------------------------------------------------------------
    @RequestMapping(value = "/provider/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Sponsorship sponsorship;

        sponsorship = this.sponsorshipService.create();
        result = this.createEditModelAndView(sponsorship);

        return result;
    }

    // Update ------------------------------------------------------------
    @RequestMapping(value = "provider/update", method = RequestMethod.GET)
    public ModelAndView update(@RequestParam final int sponsorshipId) {
        ModelAndView result;
        Sponsorship sponsorship;

        try {
            final Provider principal = (Provider) this.actorService.getActorLogged();
            sponsorship = this.sponsorshipService.findOne(sponsorshipId);
            Assert.isTrue(sponsorship.getProvider().equals(principal));
            result = this.updateModelAndView(sponsorship);
            return result;
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }
    }

    // Save -------------------------------------------------------------
    @RequestMapping(value = "/provider/create", method = RequestMethod.POST, params = "save")
    public ModelAndView save(Sponsorship sponsorship, final BindingResult binding) {
        ModelAndView result;

        try {
            sponsorship = this.sponsorshipService.reconstruct(sponsorship, binding);
            this.sponsorshipService.save(sponsorship);
            result = new ModelAndView("redirect:list.do");

        } catch (ValidationException e) {
            result = this.createEditModelAndView(sponsorship, null);
        } catch (final Throwable oops) {
            result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
        }

        return result;
    }

    // Update Save -------------------------------------------------------------
    @RequestMapping(value = "provider/update", method = RequestMethod.POST, params = "update")
    public ModelAndView updateSave(@ModelAttribute("sponsorship") Sponsorship sponsorship, final BindingResult binding) {
        ModelAndView result;

        try {
            sponsorship = this.sponsorshipService.reconstruct(sponsorship, binding);
            sponsorship = this.sponsorshipService.save(sponsorship);
            result = new ModelAndView("redirect:list.do");
        } catch (final ValidationException e) {
            result = this.updateModelAndView(sponsorship, null);
        } catch (final Throwable oops) {
            result = this.updateModelAndView(sponsorship, "item.commit.error");
        }
        return result;
    }

    // Show ------------------------------------------------------
    @RequestMapping(value = "/provider/show", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam final int sponsorshipId) {
        ModelAndView result;
        final Sponsorship sponsorship;

        try {
            final Provider principal = (Provider) this.actorService.getActorLogged();
            sponsorship = this.sponsorshipService.findOne(sponsorshipId);
            Assert.isTrue(sponsorship.getProvider().equals(principal));
            result = new ModelAndView("sponsorship/provider/show");
            result.addObject("sponsorship", sponsorship);
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    // Delete GET ------------------------------------------------------
    @RequestMapping(value = "provider/delete", method = RequestMethod.GET)
    public ModelAndView deleteGet(@RequestParam final int sponsorshipId) {
        ModelAndView result;
        Sponsorship sponsorship;

        try {
            try {
                final Provider principal = (Provider) this.actorService.getActorLogged();
                sponsorship = this.sponsorshipService.findOne(sponsorshipId);
                Assert.isTrue(sponsorship.getProvider().equals(principal));
            } catch (final Exception e) {
                result = new ModelAndView("redirect:/");
                return result;
            }
            this.sponsorshipService.delete(sponsorship);
            result = new ModelAndView("redirect:list.do");
        } catch (final Throwable oops) {
            sponsorship = this.sponsorshipService.findOne(sponsorshipId);
            result = this.createEditModelAndView(sponsorship, "problem.commit.error");
        }

        return result;
    }

    // Delete POST ------------------------------------------------------
    @RequestMapping(value = "provider/update", method = RequestMethod.POST, params = "delete")
    public ModelAndView deletePost(@ModelAttribute("sponsorship") Sponsorship sponsorship, final BindingResult binding) {
        ModelAndView result;

        try {
            try {
                final Sponsorship sp = this.sponsorshipService.findOne(sponsorship.getId());
                this.sponsorshipService.delete(sp);
                result = new ModelAndView("redirect:list.do");
            } catch (final Exception e) {
                result = new ModelAndView("redirect:/");
                return result;
            }
        } catch (final Throwable oops) {
            result = this.updateModelAndView(sponsorship, "problem.commit.error");
        }

        return result;
    }

    // Ancillary methods ------------------------------------------------------
    protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
        ModelAndView result;

        result = this.createEditModelAndView(sponsorship, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String message) {
        ModelAndView result;

        result = new ModelAndView("sponsorship/provider/create");
        final Collection<Position> positionList = this.positionService.getPositionsAvilables();

        result.addObject("positionList", positionList);
        result.addObject("sponsorship", sponsorship);
        result.addObject("message", message);

        return result;
    }

    protected ModelAndView updateModelAndView(final Sponsorship sponsorship) {
        ModelAndView result;

        result = this.updateModelAndView(sponsorship, null);

        return result;
    }

    protected ModelAndView updateModelAndView(final Sponsorship sponsorship, final String message) {
        ModelAndView result;

        result = new ModelAndView("sponsorship/provider/update");

        result.addObject("sponsorship", sponsorship);
        result.addObject("message", message);

        return result;
    }

    private ModelAndView forbiddenOpperation() {
        return new ModelAndView("redirect:/");
    }

}
