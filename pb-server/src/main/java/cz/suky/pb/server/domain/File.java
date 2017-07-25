package cz.suky.pb.server.domain;

import java.io.Serializable;

/**
 * Created by none_ on 06-Nov-16.
 */
public class File implements Serializable {
    private static final long serialVersionUID = 1L;

    public MimeType mimeType;
    public Charset charset;

    public File(MimeType mimeType, Charset charset) {
        this.mimeType = mimeType;
        this.charset = charset;
    }
}
