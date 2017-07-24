package cz.suky.pb.server.domain;

/**
 * Created by none_ on 06-Nov-16.
 */
public enum MimeType {
    TEXT_HTML("text/html");

    private final String mimeType;

    MimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }
}
