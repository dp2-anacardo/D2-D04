package repositories;

import domain.Sponsorship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

    @Query("select s from Sponsorship s where s.position.id = ?1")
    List<Sponsorship> findAllByPosition(final int positionID);

    @Query("select s from Sponsorship s where s.provider.id = ?1")
    Collection<Sponsorship> findAllByProvider(final int providerID);
}
