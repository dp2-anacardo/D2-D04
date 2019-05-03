
package repositories;

import domain.Administrator;
import domain.Company;
import domain.Position;
import domain.Rookie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

    //Query 1: The average, the minimum, the maximum, and the standard deviation of the number of positions per company.

    @Query("select avg(1.0*(select count(f) from Position f where f.company.id = c.id)) from Company c")
    Double getAvgNumberOfPositions();

    @Query("select min(1.0*(select count(f) from Position f where f.company.id = c.id)) from Company c")
    Double getMinimumNumberOfPositions();

    @Query("select max(1.0*(select count(f) from Position f where f.company.id = c.id)) from Company c")
    Double getMaximumNumberOfPositions();

    @Query("select stddev(1.0*(select count(f) from Position f where f.company.id = c.id)) from Company c")
    Double getStddevNumberOfPositions();

    @Query("select avg(1.0*(select count(a) from Application a where a.rookie.id = h.id)) from Rookie h")
    Double getAvgNumberOfApplications();

    @Query("select min(1.0*(select count(a) from Application a where a.rookie.id = h.id)) from Rookie h")
    Double getMinimumNumberOfApplications();

    @Query("select max(1.0*(select count(a) from Application a where a.rookie.id = h.id)) from Rookie h")
    Double getMaximumNumberOfApplications();

    @Query("select stddev(1.0*(select count(a) from Application a where a.rookie.id = h.id)) from Rookie h")
    Double getStddevNumberOfApplications();

    @Query("select p.company from Position p group by p.company order by count(p) desc")
    List<Company> getCompaniesWithMorePositionsOffered();

    @Query("select a.rookie from Application a group by a.rookie order by count(a) desc")
    List<Rookie> getRookiesWithMoreApplications();

    @Query("select avg(p.salary) from Position p where p.isFinal = true and p.isCancelled = false")
    Double getAvgSalaryOffered();

    @Query("select min(p.salary) from Position p where p.isFinal = true and p.isCancelled = false")
    Double getMinSalaryOffered();

    @Query("select max(p.salary) from Position p where p.isFinal = true and p.isCancelled = false")
    Double getMaxSalaryOffered();

    @Query("select stddev(p.salary) from Position p where p.isFinal = true and p.isCancelled = false")
    Double getStddevSalaryOffered();

    //@Query("select p,max(p.salary) from Position p where p.isFinal = true and p.isCancelled = false")
    //List<Object> getBestPositionSalaryOffered();

    @Query("select p from Position p where p.isFinal = true and p.isCancelled = false order by p.salary desc")
    List<Position> getBestPositionSalaryOffered();

    @Query("select p from Position p where p.isFinal = true and p.isCancelled = false order by p.salary asc")
    List<Position> getWorstPositionSalaryOffered();

    @Query("select avg(h.curricula.size)*1.0 from Rookie h")
    Double getAvgNumberOfCurricula();

    @Query("select min(h.curricula.size)*1.0 from Rookie h")
    Double getMinimumNumberOfCurricula();

    @Query("select max(h.curricula.size)*1.0 from Rookie h")
    Double getMaximumNumberOfCurricula();

    @Query("select stddev(h.curricula.size)*1.0 from Rookie h")
    Double getStddevNumberOfCurricula();

    @Query("select avg(f.positions.size)*1.0 from Finder f")
    Double getAvgResultFinder();

    @Query("select min(f.positions.size)*1.0 from Finder f")
    Double getMinimumResultFinder();

    @Query("select max(f.positions.size)*1.0 from Finder f")
    Double getMaximumResultFinder();

    @Query("select stddev(f.positions.size)*1.0 from Finder f")
    Double getStddevResultFinder();

    @Query("select (count(f1)*100.0)/(select count(f2) from Finder f2) from Finder f1 where f1.positions is not empty")
    Double getRatioOfNotEmptyFinders();

    @Query("select (count(f1)*100.0)/(select count(f2) from Finder f2) from Finder f1 where f1.positions is empty")
    Double getRatioOfEmptyFinders();

    //@Query("select avg(1.0*(select ))");
}
