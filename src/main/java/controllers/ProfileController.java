/*
 * WelcomeController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import security.LoginService;
import security.UserAccount;
import services.*;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private ProviderService			providerService;


	@RequestMapping(value = "/myInformation", method = RequestMethod.GET)
	public ModelAndView myInformation() {
		final ModelAndView result = new ModelAndView("profile/myInformation");
		final Actor user = this.actorService.getActorLogged();
		final UserAccount userAccount = LoginService.getPrincipal();

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("ROOKIE")) {
			Rookie rookie;
			rookie = this.rookieService.findOne(user.getId());
			Assert.notNull(rookie);
			result.addObject("rookie", rookie);
		}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN")) {
			Administrator administrador1;
			administrador1 = this.administratorService.findOne(user.getId());
			Assert.notNull(administrador1);
			result.addObject("administrator", administrador1);
		}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("COMPANY")) {
			Company company;
			company = this.companyService.findOne(user.getId());
			Assert.notNull(company);
			result.addObject("company", company);
		}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("AUDITOR")) {
			Auditor a;
			a = this.auditorService.findOne(user.getId());
			Assert.notNull(a);
			result.addObject("auditor", a);
		}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("PROVIDER")) {
			Provider p;
			p = this.providerService.findOne(user.getId());
			Assert.notNull(p);
			result.addObject("provider", p);
		}
		return result;
	}

	@RequestMapping(value = "/deleteInformation", method = RequestMethod.GET)
	public ModelAndView deleteInformation(){
		ModelAndView result;

		try {
			Assert.isTrue(this.actorService.getActorLogged() != null);
			this.actorService.deleteInformation();
			result = new ModelAndView("redirect:/j_spring_security_logout");
		} catch (final Exception e) {
			return this.forbiddenOperation();
		}
		return result;
	}

	private ModelAndView forbiddenOperation() {
		return new ModelAndView("redirect:/");
	}
	@RequestMapping(value = "/exportJSON", method = RequestMethod.GET)
	public ModelAndView exportJSON () {
		final ModelAndView result = new ModelAndView("profile/exportJSON");
		final Actor user = this.actorService.getActorLogged();
		final UserAccount userAccount = LoginService.getPrincipal();
		final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN")) {
			Administrator administrador1;
			administrador1 = this.administratorService.findOne(user.getId());
			Assert.notNull(administrador1);
			final String json = gson.toJson(administrador1);
			result.addObject("json", json);
		}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("ROOKIE")) {
			Rookie rookie;
			rookie = this.rookieService.findOne(user.getId());
			Assert.notNull(rookie);
			final String json = gson.toJson(rookie);
			result.addObject("json", json);
		}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("COMPANY")) {
			Company member;
			member = this.companyService.findOne(user.getId());
			Assert.notNull(member);
			final String json = gson.toJson(member);
			result.addObject("json", json);
		}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("AUDITOR")) {
			Auditor a;
			a = this.auditorService.findOne(user.getId());
			Assert.notNull(a);
			final String json = gson.toJson(a);
			result.addObject("json", json);
		}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("PROVIDER")) {
			Provider p;
			p = this.providerService.findOne(user.getId());
			Assert.notNull(p);
			final String json = gson.toJson(p);
			result.addObject("json", json);
		}
		return result;
	}

}
