package de.flashheart.rlgserver.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {@Index(name = "PIT_INDEX", columnList = "pit")})
public class Reading extends AbstractEntity {
    @NotNull
    LocalDateTime pit; // pit of the last entry
    @NotNull
    String uuid; // of the sensor
    @NotNull
    private BigDecimal temperature;

    public Reading() {
    }

    public LocalDateTime getPit() {
        return pit;
    }

    public void setPit(LocalDateTime pit) {
        this.pit = pit;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }


}
