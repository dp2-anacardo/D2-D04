package repositories;

import domain.EducationalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EducationalDataRepository extends JpaRepository<EducationalData,Integer> {
}
