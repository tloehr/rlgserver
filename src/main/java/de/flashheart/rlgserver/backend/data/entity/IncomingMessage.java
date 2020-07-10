package de.flashheart.rlgserver.backend.data.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "incoming_message")
public class IncomingMessage extends AbstractEntity {

    private String host;
    private String service;
    private LocalDateTime pit;
    private String subject;
    private String reference;
    private String key_part1;
    private String key_part2;
    private String value;

    @Basic
    @Column(nullable = false)
    public LocalDateTime getPit() {
        return pit;
    }

    public void setPit(LocalDateTime pit) {
        this.pit = pit;
    }

    @Basic
    @Column(nullable = false)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(nullable = false, length = 200)
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }


    @Basic
    @Column(nullable = false, length = 200)
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Basic
    @Column(nullable = false, length = 200)
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }


    @Basic
    @Column(nullable = true, length = 200)
    public String getKey_part1() {
        return key_part1;
    }

    public void setKey_part1(String key_part1) {
        this.key_part1 = key_part1;
    }

    @Basic
    @Column(nullable = true, length = 200)
    public String getKey_part2() {
        return key_part2;
    }

    public void setKey_part2(String key_part2) {
        this.key_part2 = key_part2;
    }

    @Basic
    @Column(nullable = false, length = 200)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
