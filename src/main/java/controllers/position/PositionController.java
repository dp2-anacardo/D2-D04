
package controllers.position;

import controllers.AbstractController;
import domain.*;
import forms.SearchForm;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController {

	@Autowired
	private PositionService	positionService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private CompanyService	companyService;

	@Autowired
	private ProblemService	problemService;

	@Autowired
	private SponsorshipService sponsorshipService;

	@Autowired
	private ApplicationService applicationService;



	@RequestMapping(value = "/listNotLogged", method = RequestMethod.GET)
	public ModelAndView listNotLogged() {
		ModelAndView result;
		final List<Position> positionsAvailables = this.positionService.getPositionsAvilables();
		result = new ModelAndView("position/listNotLogged");

		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			final Actor actor = this.actorService.getActorLogged();
			if(actor instanceof Auditor){
				final ArrayList<Boolean> created = new ArrayList<Boolean>();
				for(Position p : positionsAvailables) {
					Boolean res = false;
					for (Audit a : p.getAudits())
						if (a.getAuditor().equals((Auditor) actor)){
							res = true;
							break;
						}
					created.add(res);
				}
				result.addObject("created", created);
			}
		}
		result.addObject("positions", positionsAvailables);
		result.addObject("RequestURI", "position/listNotLogged.do");

		return result;
	}

	@RequestMapping(value = "/listByCompany", method = RequestMethod.GET)
	public ModelAndView listByCompany(@RequestParam final int companyId) {
		ModelAndView result;
		try {
			Assert.notNull(this.companyService.findOne(companyId));
			final Collection<Position> positions = this.positionService.getPositionsByCompanyAvailable(companyId);
			result = new ModelAndView("position/listNotLogged");
			result.addObject("positions", positions);
			result.addObject("RequestURI", "position/listByCompany.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/company/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final int id = this.actorService.getActorLogged().getId();
			final Company c = this.companyService.findOne(id);
			final Collection<Position> positions = this.positionService.getPositionsByCompanyAll(c);

			result = new ModelAndView("position/company/list");
			result.addObject("positions", positions);
			result.addObject("RequestURI", "position/company/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;

	}

	@RequestMapping(value = "/company/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Position position;
		position = new Position();
		final Company c = this.companyService.findOne(this.actorService.getActorLogged().getId());
		final Collection<Problem> problems = this.problemService.getProblemsFinalByCompany(c);
		result = this.createEditModelAndView(position);
		result.addObject("problems", problems);
		return result;
	}

	@RequestMapping(value = "/company/edit", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView saveDraft(Position position, final BindingResult binding) {
		ModelAndView result;
		final Collection<Problem> problems = this.problemService.getProblemsFinalByCompany((Company) this.actorService.getActorLogged());

		try {
			final Company c = this.companyService.findOne(this.actorService.getActorLogged().getId());
			if (position.getId() == 0) {
				position.setCompany(c);
				position.setIsFinal(false);
			}
			position = this.positionService.reconstruct(position, binding);
			position = this.positionService.saveDraft(position);
			result = new ModelAndView("redirect:list.do");
		} catch (final ValidationException e) {
			result = this.createEditModelAndView(position, null);
			result.addObject("problems", problems);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(position, "position.commit.error");
			result.addObject("problems", problems);
		}
		return result;
	}

	@RequestMapping(value = "/company/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(Position position, final BindingResult binding) {
		ModelAndView result;
		final Collection<Problem> problems = this.problemService.getProblemsFinalByCompany((Company) this.actorService.getActorLogged());
		try {
			final Actor a = this.actorService.getActorLogged();
			final Company c = this.companyService.findOne(a.getId());
			if (position.getId() == 0) {
				position.setCompany(c);
				position.setIsFinal(true);
			}
			position = this.positionService.reconstruct(position, binding);
			position = this.positionService.saveFinal(position);
			result = new ModelAndView("redirect:list.do");
		} catch (final ValidationException e) {
			result = this.createEditModelAndView(position, null);
			result.addObject("problems", problems);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(position, "position.commit.error");
			result.addObject("problems", problems);
		}
		return result;
	}

	@RequestMapping(value = "/company/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int positionId) {
		ModelAndView result;
		final Position position = this.positionService.findOne(positionId);
		final Company c = this.companyService.findOne(this.actorService.getActorLogged().getId());
		try {
			Assert.notNull(c);
			Assert.isTrue(position.getIsFinal());
			Assert.isTrue(position.getCompany().equals(c));
			position.setIsCancelled(true);

			final Collection<Sponsorship> sponsorships = this.sponsorshipService.findAllByPosition(positionId);
			for(Sponsorship s : sponsorships)
				this.sponsorshipService.deleteForced(s);

			final Collection<Application> applications = this.applicationService.getAllApplicationsByPosition(positionId);
			for(Application a : applications)
				if(!a.getStatus().equals("REJECTED")) {
					a.setStatus("CANCELLED");
					this.applicationService.saveCompany(a);
				}

			this.positionService.save(position);
			result = new ModelAndView("redirect:list.do");
		} catch (final ValidationException e) {
			result = this.createEditModelAndView(position, null);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(position, "position.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/company/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionId) {
		ModelAndView result;
		Position position;

		position = this.positionService.findOne(positionId);
		final Company c = this.companyService.findOne(this.actorService.getActorLogged().getId());

		if (position == null || position.getIsFinal() == true || !(position.getCompany().equals(c)))
			result = new ModelAndView("redirect:/");
		else
			try {
				final Collection<Problem> problems = this.problemService.getProblemsFinalByCompany(c);
				result = this.createEditModelAndView(position);
				result.addObject("problems", problems);
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/");
			}
		return result;
	}

	@RequestMapping(value = "/company/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int positionId) {
		ModelAndView result;
		try {
			final Position p = this.positionService.findOne(positionId);
			final Company c = this.companyService.findOne(this.actorService.getActorLogged().getId());
			Assert.notNull(p);
			Assert.notNull(c);
			Assert.isTrue(p.getIsFinal() == false);
			Assert.isTrue(p.getCompany().equals(c));
			this.positionService.delete(p);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int positionId) {
		ModelAndView result;

		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try {
			final Position p = this.positionService.findOne(positionId);
			Assert.notNull(p);
			if((authentication instanceof AnonymousAuthenticationToken) || !(this.actorService.getActorLogged() instanceof Company)) {
				Assert.isTrue(p.getIsCancelled() == false);
				Assert.isTrue(p.getIsFinal());
				result = new ModelAndView("position/show");
				result.addObject("position", p);
			} else if(p.getCompany().getId() == this.actorService.getActorLogged().getId()){
				result = new ModelAndView("position/show");
				result.addObject("position", p);
			} else {
				Assert.isTrue(p.getIsCancelled() == false);
				Assert.isTrue(p.getIsFinal());
				result = new ModelAndView("position/show");
				result.addObject("position", p);
			}

			Sponsorship banner = this.sponsorshipService.showSponsorship(positionId);

			if (banner != null)
				result.addObject("sponsorshipBanner", banner.getBanner());

		} catch (Throwable oops){
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		final ModelAndView result = new ModelAndView("position/search");
		result.addObject("search", new SearchForm());
		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
	public ModelAndView search(@ModelAttribute("search") @Valid final SearchForm search, final BindingResult binding) {
		ModelAndView result;
		final Collection<Position> positions;

		if (binding.hasErrors())
			result = new ModelAndView("redirect:/");
		else
			try {

				positions = this.positionService.searchPositions(search.getKeyword());

				if (positions.isEmpty())
					result = new ModelAndView("redirect:/position/listNotLogged.do");
				else {
					result = new ModelAndView("position/search");
					result.addObject("positions", positions);
				}
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/");
			}
		return result;
	}

	private ModelAndView createEditModelAndView(final Position position) {
		ModelAndView result;
		result = this.createEditModelAndView(position, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Position position, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("position/company/edit");
		result.addObject("position", position);
		result.addObject("message", messageCode);
		return result;
	}

}
