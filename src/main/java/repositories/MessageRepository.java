
package repositories;

import domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select a.messagesReceived from Actor a where a.id=?1")
	Collection<Message> findAllReceivedInPoolByActor(int actorID);

	@Query("select a.messagesSent from Actor a where a.id=?1")
	Collection<Message> findAllSentInPoolByActor(int actorID);

	@Query("select m from Message m where m.sender.id=?1")
	Collection<Message> findAllSentByActor(int actorID);

	@Query("select m from Message m where m.recipient.id=?1")
	Collection<Message> findAllReceivedByActor(int actorID);

	@Query("select m from Message m where m.sender=null and 'REBRANDING INFORMATION' member of m.tags")
	Collection<Message> findRebrandingMessage();

}
