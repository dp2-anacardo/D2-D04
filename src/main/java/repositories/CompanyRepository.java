
package repositories;

import domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query("select avg(a.score)*0.1 from Position p join p.audits a where p.company = ?1 and a.isFinal = true")
    Double computeAuditScore(Company company);

}
