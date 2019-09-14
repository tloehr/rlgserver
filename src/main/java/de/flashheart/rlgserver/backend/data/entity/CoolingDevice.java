package de.flashheart.rlgserver.backend.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class CoolingDevice extends AbstractEntity {
    @NotNull
    String machine; // name des Gerätes. Nur für den Anwender. Ist kein Primärschlüssel oder so
    @NotNull
    @Column(unique = true)
    String uuid; // of the sensor
    @NotNull
    private BigDecimal min; // unterer Grenze für Alarm
    @NotNull
    private BigDecimal max; // obere Grenze für Alarm. Wird jedes mal vom Sensor mitgegeben.

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public CoolingDevice() {
    }

    public CoolingDevice(@NotNull String machine, @NotNull String uuid, @NotNull BigDecimal min, @NotNull BigDecimal max) {
        this.machine = machine;
        this.uuid = uuid;
        this.min = min;
        this.max = max;
    }
}
