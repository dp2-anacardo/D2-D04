package controllers.provider;

import controllers.AbstractController;
import domain.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ProviderService;

import java.util.Collection;

@Controller
@RequestMapping("/provider")
public class ProviderController extends AbstractController {

    @Autowired
    private ProviderService providerService;

    @RequestMapping(value = "/listNotLogged", method = RequestMethod.GET)
    public ModelAndView listNotLogged(){
        ModelAndView result;
        Collection<Provider> providers = this.providerService.findAll();
        result = new ModelAndView("provider/listNotLogged");
        result.addObject("providers", providers);
        result.addObject("RequestURI", "provider/listNotLogged.do");

        return result;
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam int providerID){
        ModelAndView result;
        Provider provider;
        try {
            provider = this.providerService.findOne(providerID);
            Assert.notNull(provider);
        } catch (Exception e){
            result = new ModelAndView("redirect:/");
            return  result;
        }

        result = new ModelAndView("provider/show");
        result.addObject("provider", provider);

        return result;
    }

}
