package com.example.sae.repository;

import com.example.sae.models.db.Notification;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface NotificationRepository extends Repository<Notification, Long> {

    Collection<Notification> findTop15ByEcurieIdOrderByDateDesc(int ecurie_id);

    int countByEcurieId(int ecurieId);

    int countByReadedFalseAndEcurieId(int ecurieId);

    @Modifying
    @Query("UPDATE notification SET readed = 1 WHERE ecurie_id = :pid")
    void markAllNotificationReadedByEcurieId(@Param("pid") int ecurie_id);
}
