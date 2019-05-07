
package repositories;

import domain.Finder;
import domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select p from Position p where (p.profile like %?1% or p.description like %?1% or p.title like %?1% or p.ticker like %?1%) and p.isFinal =TRUE and p.isCancelled = FALSE")
	Collection<Position> getPositionsByKeyWord(String keyWord);

	@Query("select p from Position p where (?1 member of p.skill or ?1 member of p.technology) and p.isFinal =TRUE and p.isCancelled = FALSE")
	Collection<Position> getPositionsContainsKeyWord(String keyWord);

	@Query("select p from Position p where p.deadline = ?1 and p.isFinal =TRUE and p.isCancelled = FALSE")
	Collection<Position> getPositionsByDeadline(Date deadline);

	@Query("select p from Position p where (p.deadline between ?1 and ?2) and p.isFinal =TRUE and p.isCancelled = FALSE")
	Collection<Position> getPositionsUntilDeadline(Date actualDate, Date deadline);

	@Query("select p from Position p where (p.salary >= ?1) and p.isFinal =TRUE and p.isCancelled = FALSE")
	Collection<Position> getPositionsByMinSalary(int minSalary);

	@Query("select p from Position p where p.isFinal =TRUE and p.isCancelled = FALSE")
	Collection<Position> findAllFinal();

	@Query("select f from Finder f join f.positions pos where pos.id=?1")
	Collection<Finder> findAllByPosition(int positionId);

}
