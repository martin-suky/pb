package cz.suky.pb.server.domain;


import java.util.Arrays;
import java.util.List;

/**
 * Created by none_ on 06-Nov-16.
 */
public enum Bank {
    MBANK("mBank", Arrays.asList(new File(MimeType.TEXT_HTML, Charset.UTF8))),
    ING("ING", Arrays.asList(new File(MimeType.TEXT_HTML, Charset.UTF8)));

    private String name;
    private List<File> inputs;

    Bank(String name, List<File> inputs) {
        this.name = name;
        this.inputs = inputs;
    }

    public String getName() {
        return name;
    }

    public List<File> getInputs() {
        return inputs;
    }

    public Charset getCharsetForMimeType(MimeType mimeType) {
        for (File file : this.getInputs()) {
            if (file.mimeType.equals(mimeType)) {
                return file.charset;
            }
        }
        return null;
    }
}
