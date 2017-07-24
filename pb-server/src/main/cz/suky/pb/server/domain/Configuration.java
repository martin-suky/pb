package cz.suky.pb.server.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by none_ on 16-Jul-17.
 */
public class Configuration extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    private ConfigKey key;
    private String value;
}
