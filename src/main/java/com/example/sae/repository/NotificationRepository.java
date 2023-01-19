package com.example.sae.repository;

import com.example.sae.models.db.Notification;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface NotificationRepository extends Repository<Notification, Long> {

    Collection<Notification> findAllByEcurieId(int ecurie_id);

    @Modifying
    @Query("UPDATE notification SET readed = 1 WHERE ecurie_id = :pid")
    void markAllNotificationReadedByEcurieId(@Param("pid") int ecurie_id);
}
