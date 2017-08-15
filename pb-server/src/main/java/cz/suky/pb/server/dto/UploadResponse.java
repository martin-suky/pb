package cz.suky.pb.server.dto;

import java.io.Serializable;

/**
 * Created by none_ on 17-Jul-17.
 */
public class UploadResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private int parsed;
    private int stored;
    private int duplicate;

    public UploadResponse(int parsed, int stored, int duplicate) {
        this.parsed = parsed;
        this.stored = stored;
        this.duplicate = duplicate;
    }

    public int getParsed() {
        return parsed;
    }

    public void setParsed(int parsed) {
        this.parsed = parsed;
    }

    public int getStored() {
        return stored;
    }

    public void setStored(int stored) {
        this.stored = stored;
    }

    public int getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(int duplicate) {
        this.duplicate = duplicate;
    }
}
