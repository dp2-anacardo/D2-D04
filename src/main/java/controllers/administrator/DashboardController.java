package controllers.administrator;

import controllers.AbstractController;
import domain.Company;
import domain.Position;
import domain.Provider;
import domain.Rookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.AdministratorService;

import java.util.List;

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

        /* ACME-ROOKIES QUERIES */

        /* 4.4.1 */
        Double avgASPositions = this.administratorService.getStatsASPositions().get(0);
        Double minASPositions = this.administratorService.getStatsASPositions().get(1);
        Double maxASPositions = this.administratorService.getStatsASPositions().get(2);
        Double stddevASPositions = this.administratorService.getStatsASPositions().get(3);

        /* 4.4.2 */
        Double avgASCompanies = this.administratorService.getStatsASCompanies().get(0);
        Double minASCompanies = this.administratorService.getStatsASCompanies().get(1);
        Double maxASCompanies = this.administratorService.getStatsASCompanies().get(2);
        Double stddevASCompanies = this.administratorService.getStatsASCompanies().get(3);

        /* 4.4.3 */
        List<String> companiesHighestAS = this.administratorService.getCompaniesHighestAS();

        /* 4.4.4 */
        Double avgSalaryPositionsWithHighestAS = this.administratorService.getAvgSalaryPositionsWithHighestAS();

        /* 11.A */
        final Double avgNumOfItems = this.administratorService.getNumberOfItemsPerProvider().get(0);
        final Double minNumOfItems = this.administratorService.getNumberOfItemsPerProvider().get(1);
        final Double maxNumOfItems = this.administratorService.getNumberOfItemsPerProvider().get(2);
        final Double stddevNumOfItems = this.administratorService.getNumberOfItemsPerProvider().get(3);

        /* 11.B */
        final List<Provider> top5Providers = this.administratorService.getTop5Providers();

        /* 14.A */
        final Double avgNumOfSpoXPro = this.administratorService.getNumberOfSponsorshipsPerProvider().get(0);
        final Double minNumOfSpoXPro = this.administratorService.getNumberOfSponsorshipsPerProvider().get(1);
        final Double maxNumOfSpoXPro = this.administratorService.getNumberOfSponsorshipsPerProvider().get(2);
        final Double stddevNumOfSpoXPro = this.administratorService.getNumberOfSponsorshipsPerProvider().get(3);

        /* 14.B */
        final Double avgNumOfSpoXPos = this.administratorService.getNumberOfSponsorshipsPerPosition().get(0);
        final Double minNumOfSpoXPos = this.administratorService.getNumberOfSponsorshipsPerPosition().get(1);
        final Double maxNumOfSpoXPos = this.administratorService.getNumberOfSponsorshipsPerPosition().get(2);
        final Double stddevNumOfSpoXPos = this.administratorService.getNumberOfSponsorshipsPerPosition().get(3);

        /* 14.C */
        final List<Provider> providersAboveAvg = this.administratorService.getProvidersAboveAverage();



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

        result.addObject("AvgNumOfItems", avgNumOfItems);
        result.addObject("MinNumOfItems", minNumOfItems);
        result.addObject("MaxNumOfItems", maxNumOfItems);
        result.addObject("StddevNumOfItems", stddevNumOfItems);

        result.addObject("Top5Providers", top5Providers);

        result.addObject("AvgNumOfSpoXPro", avgNumOfSpoXPro);
        result.addObject("MinNumOfSpoXPro", minNumOfSpoXPro);
        result.addObject("MaxNumOfSpoXPro", maxNumOfSpoXPro);
        result.addObject("StddevNumOfSpoXPro", stddevNumOfSpoXPro);

        result.addObject("AvgNumOfSpoXPos", avgNumOfSpoXPos);
        result.addObject("MinNumOfSpoXPos", minNumOfSpoXPos);
        result.addObject("MaxNumOfSpoXPos", maxNumOfSpoXPos);
        result.addObject("StddevNumOfSpoXPos", stddevNumOfSpoXPos);

        result.addObject("ProvidersAboveAvg", providersAboveAvg);

        result.addObject("avgASPositions",avgASPositions);
        result.addObject("minASPositions",minASPositions);
        result.addObject("maxASPositions",maxASPositions);
        result.addObject("stddevASPositions",stddevASPositions);

        result.addObject("avgASCompanies",avgASCompanies);
        result.addObject("minASCompanies",minASCompanies);
        result.addObject("maxASCompanies",maxASCompanies);
        result.addObject("stddevASCompanies",stddevASCompanies);

        result.addObject("companiesHighestAS",companiesHighestAS);

        result.addObject("avgSalaryPositionsWithHighestAS",avgSalaryPositionsWithHighestAS);
        return result;
    }
}
