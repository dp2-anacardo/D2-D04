
package repositories;

import domain.Rookie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RookieRepository extends JpaRepository<Rookie, Integer> {

}
