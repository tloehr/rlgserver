package de.flashheart.rlgserver.backend.data.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
//@Table(name = "incoming_message")
public class IncomingMessage extends AbstractEntity {
    private String host;
    private String service;
    private LocalDateTime pit;
    private String subject;
    private String reference;
    private String key1;
    private String key2;
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
    @Column(nullable = true, length = 200)
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
    @Column(nullable = false, length = 200)
    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    @Basic
    @Column(nullable = true, length = 200)
    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    @Basic
    @Column(nullable = false, length = 200)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "IncomingMessage{" +
                "host='" + host + '\'' +
                ", service='" + service + '\'' +
                ", pit=" + pit +
                ", subject='" + subject + '\'' +
                ", reference='" + reference + '\'' +
                ", key1='" + key1 + '\'' +
                ", key2='" + key2 + '\'' +
                ", value='" + value + '\'' +
                '}' + super.toString();
    }

}
