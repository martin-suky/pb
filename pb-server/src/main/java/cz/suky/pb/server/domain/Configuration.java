package cz.suky.pb.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by none_ on 16-Jul-17.
 */
@Entity
public class Configuration extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private ConfigKey key;
    private String value;

    public Configuration() {
    }

    public Configuration(ConfigKey key) {
        this.key = key;
    }

    public ConfigKey getKey() {
        return key;
    }

    public void setKey(ConfigKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean getValueAsBoolean() {
        if (value == null) {
            return false;
        } else {
            return Boolean.parseBoolean(value);
        }
    }
    public void setValueAsBoolean(boolean value) {
        this.value = Boolean.toString(value);
    }
}
