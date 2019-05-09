
package repositories;

import domain.Auditor;
import domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select p from Position p where p.isFinal = true and p.isCancelled = false")
	List<Position> getPositionsAvailable();

	@Query("select p from Position p join p.company c where p.isFinal = true and c.id=?1 and p.isCancelled = false")
	Collection<Position> getPositionsByCompanyAvailable(int companyId);

	@Query("select p from Position p join p.company c where c.id = ?1")
	Collection<Position> getPositionsByCompanyAll(int companyId);

	@Query("select a.auditor from Position p join p.audits a where p.isFinal=true and p.isCancelled=false and p.id=?1")
	Collection<Auditor> getAuditorsByPosition(int positionId);


}
