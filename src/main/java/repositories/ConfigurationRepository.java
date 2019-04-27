package repositories;

import domain.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {

    @Query("select c from Configuration c")
    Collection<Configuration> getConfiguration();
}
