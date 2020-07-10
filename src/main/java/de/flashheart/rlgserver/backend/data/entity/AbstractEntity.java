package de.flashheart.rlgserver.backend.data.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return super.hashCode();
        }

        return 31 + id.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        boolean equal;

        if (id == null) {
            // New entities are only equal if the instance if the same
            equal = super.equals(other);
        } else if (this == other) {
            equal = true;
        } else if (!(other instanceof DefaultEntity)) {
            equal = false;
        } else {
            equal = id.equals(((DefaultEntity) other).id);
        }

        return equal;
    }

    @Override
    public String toString() {
        return Tools.toString(this, this.getClass());
    }
}
