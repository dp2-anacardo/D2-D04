package controllers.administrator;

import java.util.List;

import domain.Company;
import domain.Rookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Position;
import services.AdministratorService;

@Controller
@RequestMapping("administrator")
public class DashboardController extends AbstractController {

    @Autowired
    private AdministratorService	administratorService;


    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        final ModelAndView result;

        /* Q1 */
        final Double avgNumberOfPositions = this.administratorService.getStatsPositionsPerCompany().get(0);
        final Double minNumberOfPositions = this.administratorService.getStatsPositionsPerCompany().get(1);
        final Double maxNumberOfPositions = this.administratorService.getStatsPositionsPerCompany().get(2);
        final Double stddevNumberOfPositions = this.administratorService.getStatsPositionsPerCompany().get(3);

        /* Q2 */
        final Double avgNumberOfApps = this.administratorService.getStatsApplicationsPerRookie().get(0);
        final Double minNumberOfApps = this.administratorService.getStatsApplicationsPerRookie().get(1);
        final Double maxNumberOfApps = this.administratorService.getStatsApplicationsPerRookie().get(2);
        final Double stddevNumberOfApps = this.administratorService.getStatsApplicationsPerRookie().get(3);

        /* Q3 */
        final List<Company> companiesWithOfferedMorePositions = this.administratorService.getCompaniesWithOfferedMorePositions();

        /* Q4 */
        final List<Rookie> rookiesWithMoreApplications = this.administratorService.getRookiesWithMoreApplications();

        /* Q5 */
        final Double avgSalaryOffered = this.administratorService.getStatsSalariesOffered().get(0);
        final Double minSalaryOffered = this.administratorService.getStatsSalariesOffered().get(1);
        final Double maxSalaryOffered = this.administratorService.getStatsSalariesOffered().get(2);
        final Double stddevSalaryOffered = this.administratorService.getStatsSalariesOffered().get(3);

        /* Q6 */
        final Position bestPositionSalaryOffered = this.administratorService.getBestPositionSalaryOffered();

        /* Q7 */
        final Position worstPositionSalaryOffered = this.administratorService.getWorstPositionSalaryOffered();

        /* Q8 */
        final Double avgNumOfCurricula = this.administratorService.getStatsCurricula().get(0);
        final Double minNumOfCurricula = this.administratorService.getStatsCurricula().get(1);
        final Double maxNumOfCurricula = this.administratorService.getStatsCurricula().get(2);
        final Double stddevNumOfCurricula = this.administratorService.getStatsCurricula().get(3);

        /* Q9 */
        final Double avgResultsOfFinder = this.administratorService.getStatsFinder().get(0);
        final Double minResultsOfFinder = this.administratorService.getStatsFinder().get(1);
        final Double maxResultsOfFinder = this.administratorService.getStatsFinder().get(2);
        final Double stddevResultsOfFinder = this.administratorService.getStatsFinder().get(3);

        /* Q12 */
        final Double ratioOfNotEmptyFinders = this.administratorService.getRatioOfNotEmptyFinders();

        /* Q13 */
        final Double ratioOfEmptyFinders = this.administratorService.getRatioOfEmptyFinders();


        /* ADD OBJECTS */
        result = new ModelAndView("administrator/dashboard");

        result.addObject("AvgNumberOfPositions", avgNumberOfPositions);
        result.addObject("MinNumberOfPositions", minNumberOfPositions);
        result.addObject("MaxNumberOfPositions", maxNumberOfPositions);
        result.addObject("StddevNumberOfPositions", stddevNumberOfPositions);

        result.addObject("AvgNumberOfApps", avgNumberOfApps);
        result.addObject("MinNumberOfApps", minNumberOfApps);
        result.addObject("MaxNumberOfApps", maxNumberOfApps);
        result.addObject("StddevNumberOfApps", stddevNumberOfApps);

        result.addObject("CompaniesWithOfferedMorePositions", companiesWithOfferedMorePositions);

        result.addObject("RookiesWithMoreApplications", rookiesWithMoreApplications);

        result.addObject("AvgSalaryOffered", avgSalaryOffered);
        result.addObject("MinSalaryOffered", minSalaryOffered);
        result.addObject("MaxSalaryOffered", maxSalaryOffered);
        result.addObject("StddevSalaryOffered", stddevSalaryOffered);

        if(bestPositionSalaryOffered != null)
            result.addObject("BestPositionSalaryOffered", bestPositionSalaryOffered);
        else
            result.addObject("BestPositionSalaryOffered", "");

        if(worstPositionSalaryOffered != null)
            result.addObject("WorstPositionSalaryOffered", worstPositionSalaryOffered);
        else
            result.addObject("WorstPositionSalaryOffered", "");

        result.addObject("AvgNumOfCurricula", avgNumOfCurricula);
        result.addObject("MinNumOfCurricula", minNumOfCurricula);
        result.addObject("MaxNumOfCurricula", maxNumOfCurricula);
        result.addObject("StddevNumOfCurricula", stddevNumOfCurricula);

        result.addObject("AvgResultsOfFinder", avgResultsOfFinder);
        result.addObject("MinResultsOfFinder", minResultsOfFinder);
        result.addObject("MaxResultsOfFinder", maxResultsOfFinder);
        result.addObject("StddevResultsOfFinder", stddevResultsOfFinder);

        result.addObject("RatioOfNotEmptyFinders", ratioOfNotEmptyFinders);

        result.addObject("RatioOfEmptyFinders", ratioOfEmptyFinders);

        return result;
    }
}
