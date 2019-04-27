package repositories;

import domain.PositionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionDataRepository extends JpaRepository<PositionData, Integer> {
}
