package de.flashheart.rlgserver.backend.data.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Reading extends AbstractEntity {
    @NotNull
    LocalDateTime pit; // pit of the last entry
    @NotNull
    String uuid; // of the machine
    @NotNull
    String machine; // name des Gerätes. Nur für den Anwender. Ist kein Primärschlüssel oder so
    @NotNull
    private BigDecimal temperature;
    @NotNull
    private BigDecimal min; // unterer Grenze für Alarm
    @NotNull
    private BigDecimal max; // obere Grenze für Alarm. Wird jedes mal vom Sensor mitgegeben.

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

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }
}
