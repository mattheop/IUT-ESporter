package com.example.sae.models.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notification {

    @Id
    @Column("notification_id")
    private Long id;

    @Column("ecurie_id")
    private int ecurieId;

    @Column("ecurie_id")
    private Ecurie ecurie;

    private String message;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column("send_date")
    private LocalDateTime date;

    private boolean readed;

    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    public int getEcurieId() {
        return ecurieId;
    }

    public void setEcurieId(int ecurieID) {
        this.ecurieId = ecurieID;
    }

    public Ecurie getEcurie() {
        return ecurie;
    }

    public void setEcurie(Ecurie ecurie) {
        this.ecurie = ecurie;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getFormattedDate() {
        return this.date.format(DateTimeFormatter.ofPattern("hh:mm le dd/dd/yyyy "));
    }

}
