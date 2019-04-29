package controllers.provider;

import controllers.AbstractController;
import domain.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.ProviderService;

import java.util.List;

@Controller
@RequestMapping("/provider")
public class ProviderController extends AbstractController {

    @Autowired
    private ProviderService providerService;

    @RequestMapping(value = "/listNotLogged", method = RequestMethod.GET)
    public ModelAndView listNotLogged(){
        ModelAndView result;
        //List<Provider> providers = this.providerService.findAll();
        result = new ModelAndView("provider/listNotLogged");
        //gitresult.addObject("providers", providers);
        result.addObject("RequestURI", "provider/listNotLogged.do");

        return result;
    }

}
