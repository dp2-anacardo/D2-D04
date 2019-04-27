package repositories;

import domain.MiscData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MiscDataRepository extends JpaRepository<MiscData, Integer> {
}
