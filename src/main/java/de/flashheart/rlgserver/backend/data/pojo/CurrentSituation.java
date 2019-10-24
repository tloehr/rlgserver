package de.flashheart.rlgserver.backend.data.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CurrentSituation {
    String machine;
    LocalDateTime pit;
    BigDecimal value;

    public CurrentSituation(String machine, LocalDateTime pit, BigDecimal value) {
        this.machine = machine;
        this.pit = pit;
        this.value = value;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public LocalDateTime getPit() {
        return pit;
    }

    public void setPit(LocalDateTime pit) {
        this.pit = pit;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
