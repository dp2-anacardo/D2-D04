package repositories;

import domain.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

    @Query("select a from Audit a where a.auditor.id = ?1")
    Collection<Audit> getAuditsByAuditor(int auditorId);
}
