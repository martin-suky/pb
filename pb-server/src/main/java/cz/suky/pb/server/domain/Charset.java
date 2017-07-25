package cz.suky.pb.server.domain;

/**
 * Created by none_ on 06-Nov-16.
 */
public enum Charset {
    UTF8("UTF-8"), ISO88592("iso-8859-2");

    private final String charset;

    Charset(String charset) {
        this.charset = charset;
    }

    public String getCharset() {
        return charset;
    }
}
