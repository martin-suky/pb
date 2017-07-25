package cz.suky.pb.server.dto;

import cz.suky.pb.server.domain.Bank;

import java.io.Serializable;

/**
 * Created by none_ on 16-Jul-17.
 */
public class CreateAccountRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Bank bank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
