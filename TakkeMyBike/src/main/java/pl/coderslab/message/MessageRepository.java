package pl.coderslab.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository <Message, Long> {

    @Query("SELECT m FROM Message m WHERE (m.receiver.id = :id1 AND m.sender.id = :id2) OR (m.receiver.id = :id2 AND m.sender.id = :id1)")
    List <Message> findAllByInterlocutors (@Param("id1") Long id1, @Param("id2") Long id2);
    List <Message> findAllBySenderIdOrReceiverId(Long id1, Long id2);
}
