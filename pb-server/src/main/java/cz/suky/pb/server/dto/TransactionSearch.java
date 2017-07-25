package cz.suky.pb.server.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by none_ on 06-Nov-16.
 */
public class TransactionSearch implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime from;
    private LocalDateTime to;

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }
}
