package cz.suky.pb.server.domain;


import java.util.Arrays;
import java.util.List;

/**
 * Created by none_ on 06-Nov-16.
 */
public enum Bank {
    MBANK("mBank", Arrays.asList(BankFormat.MBANK_BANKING,
                                BankFormat.MBANK_EMAIL)),
    ING("ING", Arrays.asList(BankFormat.ING_BANKING));

    private String name;
    private List<BankFormat> inputs;

    Bank(String name, List<BankFormat> inputs) {
        this.name = name;
        this.inputs = inputs;
    }

    public String getName() {
        return name;
    }

    public List<BankFormat> getFormats() {
        return inputs;
    }

}
