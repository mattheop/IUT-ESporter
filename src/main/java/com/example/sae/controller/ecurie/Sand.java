package com.example.sae.controller.ecurie;

import com.example.sae.models.db.Notification;
import com.example.sae.models.db.Rencontre;
import com.example.sae.repository.NotificationRepository;
import com.example.sae.repository.RencontreRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class Sand {

    private NotificationRepository notificationRepository;

    public Sand(NotificationRepository notificationRepository) {

        this.notificationRepository = notificationRepository;
    }

    @GetMapping("/sand")
    public Collection<Notification> index() {

        notificationRepository.markAllNotificationReadedByEcurieId(1);
        Collection<Notification> allByEcurieId = notificationRepository.findAllByEcurieId(1);

        return allByEcurieId;
    }
}
