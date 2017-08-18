package cz.suky.pb.server.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

/**
 * Created by none_ on 10/31/16.
 */
@Entity
public class Account extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private String name;
    private BigDecimal balance;
    @ManyToOne(optional = false)
    private User owner;
    @Enumerated(EnumType.STRING)
    private Bank bank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
