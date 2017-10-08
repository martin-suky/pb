package cz.suky.pb.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BankFormat {
    MBANK_BANKING("mBank Banking", new File(MimeType.TEXT_HTML, Charset.UTF8)),
    MBANK_EMAIL("mBank Email", new File(MimeType.TEXT_HTML, Charset.ISO88592)),
    ING_BANKING("mBank Banking", new File(MimeType.TEXT_HTML, Charset.UTF8));

    private final String formatName;
    private final File file;

    BankFormat(String formatName, File file) {
        this.formatName = formatName;
        this.file = file;
    }

    public String getFormatName() {
        return formatName;
    }

    public File getFile() {
        return file;
    }
}
