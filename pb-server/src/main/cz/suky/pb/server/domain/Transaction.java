package cz.suky.pb.server.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by none_ on 10/31/16.
 */
@Entity
public class Transaction extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private Long numberOfTransaction;
    private LocalDateTime dateOfTransaction;
    private String description;
    private BigDecimal amount;
    @ManyToOne
    private Account account;

    public Long getNumberOfTransaction() {
        return numberOfTransaction;
    }

    public void setNumberOfTransaction(Long numberOfTransaction) {
        this.numberOfTransaction = numberOfTransaction;
    }

    public LocalDateTime getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(LocalDateTime dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
