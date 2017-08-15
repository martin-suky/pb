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

    private LocalDateTime date;
    private String description;
    private BigDecimal amount;
    @ManyToOne
    private Account account;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (!date.equals(that.date)) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (!amount.equals(that.amount)) return false;
        return account.equals(that.account);
    }

    @Override
    public int hashCode() {
        int result = 31 * date.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + amount.hashCode();
        result = 31 * result + account.hashCode();
        return result;
    }
}
