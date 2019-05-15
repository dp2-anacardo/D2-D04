
package controllers.application;

import controllers.AbstractController;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import security.LoginService;
import services.*;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Controller
@RequestMapping("application")
public class ApplicationController extends AbstractController {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private RookieService		rookieService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private CurriculaService	curriculaService;


	@RequestMapping(value = "/rookie/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Application> applications;

		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Rookie rookie = this.rookieService.findOne(user.getId());
		applications = this.applicationService.getApplicationsByRookie(rookie);

		final HashMap<Integer, Position> appPosition = new HashMap<Integer, Position>();
		for(Application a : applications)
			appPosition.put(a.getId(),this.applicationService.getPositionByApplication(a.getId()));

		result = new ModelAndView("application/rookie/list");
		result.addObject("applications", applications);
		result.addObject("appPosition", appPosition);
		result.addObject("requestURI", "application/rookie/list.do");
		return result;
	}

	@RequestMapping(value = "/company/list", method = RequestMethod.GET)
	public ModelAndView listCompany(@RequestParam int positionId) {
		ModelAndView result;
		Collection<Application> applications;

		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Company company = this.companyService.findOne(user.getId());

		try {
			Position position = positionService.findOne(positionId);
			Assert.isTrue(position.getCompany().equals(company));
			applications = this.applicationService.getApplicationsByPosition(positionId);
			result = new ModelAndView("application/company/list");
			result.addObject("applications", applications);
			result.addObject("requestURI", "application/company/list.do?positionId="+positionId);
		}catch (Throwable oops){
			result = new ModelAndView("redirect:/position/company/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/rookie/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;
		Actor actor;
		Rookie rookie;

		try {
			Assert.notNull(applicationId);
			actor = this.actorService.getActorLogged();
			rookie = this.rookieService.findOne(actor.getId());
			application = this.applicationService.findOne(applicationId);
			Assert.isTrue(application.getRookie().equals(rookie));
			final Position position = this.applicationService.getPositionByApplication(applicationId);
			result = new ModelAndView("application/rookie/show");
			result.addObject("application", application);
			result.addObject("position", position);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/application/rookie/list.do");
		}
		return result;
	}



	@RequestMapping(value = "/company/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		try {
			Assert.notNull(applicationId);
			application = this.applicationService.findOne(applicationId);
			this.applicationService.acceptApplication(application);
			this.applicationService.saveCompany(application);
			int positionId = this.applicationService.getPositionByApplication(applicationId).getId();
			result = new ModelAndView("redirect:/application/company/list.do?positionId="+positionId);
			return result;
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/");
			return result;
		}
	}

	@RequestMapping(value = "/company/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		try {
			Assert.notNull(applicationId);
			Position position = this.applicationService.getPositionByApplication(applicationId);
			Assert.isTrue(!position.getIsCancelled());
			application = this.applicationService.findOne(applicationId);
			result = this.rejectModelAndView(application);
			return result;
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/");
			return result;
		}
	}

	@RequestMapping(value = "/company/reject", method = RequestMethod.POST, params = "reject")
	public ModelAndView reject(Application application, final BindingResult binding) {
		ModelAndView result;
		application = this.applicationService.reconstructReject(application, binding);

		if (application.getRejectComment().equals("")) {
			binding.rejectValue("rejectComment", "error.rejectComment");
			result = this.rejectModelAndView(application);
			return result;
		} else if (binding.hasErrors()) {
			result = this.rejectModelAndView(application);
			return result;
		} else{
				int positionId = this.applicationService.getPositionByApplication(application.getId()).getId();
				this.applicationService.rejectApplication(application);
				this.applicationService.saveCompany(application);
				result = new ModelAndView("redirect:/application/company/list.do?positionId="+positionId);
				return result;
		}
	}

	protected ModelAndView rejectModelAndView(final Application application) {
		ModelAndView result;

		result = this.rejectModelAndView(application, null);

		return result;
	}

	protected ModelAndView rejectModelAndView(final Application application, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("application/company/reject");
		result.addObject("application", application);
		result.addObject("message", messageCode);

		return result;
	}


	//PARTE DEL ROOKIE--------------------------------------------------------------------------------------------------

	@RequestMapping(value = "/rookie/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int positionId){
		ModelAndView result;
		try{
			Position position = this.positionService.findOne(positionId);
			Rookie rookie = rookieService.findOne(actorService.getActorLogged().getId());
			Collection<Application> applications = applicationService.getApplicationsByRookie(rookie);
			Collection<Application> applicationsPosition = applicationService.getAllApplicationsByPosition(positionId);
			Assert.isTrue(!applicationsPosition.contains(applications));
			Assert.isTrue(!position.getIsCancelled());
			Actor a = this.actorService.getActorLogged();
			Rookie h = this.rookieService.findOne(a.getId());
			Application application = this.applicationService.create();
			result = new ModelAndView("application/rookie/create");
			result.addObject("application",application);
			result.addObject("positionId",positionId);
			result.addObject("curricula",h.getCurricula());
		}catch(Throwable oops){
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value="/rookie/create", method = RequestMethod.POST)
	public ModelAndView saveCreate(@ModelAttribute("application")Application application,@RequestParam int positionId,BindingResult binding){
		ModelAndView result;
		try{
			this.applicationService.saveRookie(application,positionId);
			result = new ModelAndView("redirect:/application/rookie/list.do");
		}catch (Throwable oops){
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/rookie/update", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam int applicationId){
		ModelAndView result;
		try{
			Position position = this.applicationService.getPositionByApplication(applicationId);
			Assert.isTrue(!position.getIsCancelled());
			Application a = this.applicationService.findOne(applicationId);
			Actor ac = this.actorService.getActorLogged();
			Rookie h = this.rookieService.findOne(ac.getId());
			Assert.isTrue(a.getRookie().equals(h));

			result = new ModelAndView("application/rookie/update");
			result.addObject("application",a);
		}catch (Throwable oops){
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/rookie/update", method = RequestMethod.POST)
	public ModelAndView saveUpdate(@ModelAttribute("application")Application application,BindingResult binding){
		ModelAndView result;
		if(StringUtils.isEmpty(application.getExplanation()) || StringUtils.isEmpty(application.getLink()) ) {
			binding.rejectValue("explanation", "error.explanation");
			binding.rejectValue("link", "error.link");
			result = new ModelAndView("application/rookie/update");
			result.addObject("application",application);
		}else{
			try {
				application = this.applicationService.reconstruct(application, binding);
				this.applicationService.saveRookieUpdate(application);
				result = new ModelAndView("redirect:/application/rookie/list.do");
			}catch (ValidationException v){
				result = new ModelAndView("application/rookie/update");
				result.addObject(application);
			}catch (Throwable oops){
				result = new ModelAndView("application/rookie/update");
				result.addObject("application",application);
				result.addObject("message","application.commit.error");
			}
		}
		return result;
	}
}
