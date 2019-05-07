
package repositories;

import domain.Application;
import domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.rookie.id = ?1")
	Collection<Application> getApplicationsByRookie(int rookieId);

	@Query("select a from Position p join p.company c join p.applications a where a.status != 'PENDING' and c.id = ?1")
	Collection<Application> getApplicationsByCompany(int companyId);

	@Query("select a from Position p join p.applications a where a.status != 'PENDING' and p.id = ?1")
	Collection<Application> getApplicationsByPosition(int positionId);

	@Query("select p from Position p join p.applications a where a.id =?1")
	Position getPositionByApplication(int applicationId);

	@Query("select p.applications from Position p where p.id= ?1")
	Collection<Application> getAllApplicationsByPosition(int positionId);

}
