package de.flashheart.rlgserver.backend.data.pojo;

import java.math.BigDecimal;

public class SensorEvent {
    BigDecimal value;
    String uuid;

    public SensorEvent() {
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public SensorEvent(String uuid, BigDecimal value) {
        this.value = value;
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "SensorEvent{" +
                "value=" + value +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
