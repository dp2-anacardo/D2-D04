package controllers.company;

import controllers.AbstractController;
import domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.CompanyService;

@Controller
@RequestMapping("/company")
public class ShowCompanyController extends AbstractController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam int companyId){
        ModelAndView result;
        Company c = this.companyService.findOne(companyId);

        result = new ModelAndView("company/show");
        result.addObject("company", c);

        return result;
    }
}
