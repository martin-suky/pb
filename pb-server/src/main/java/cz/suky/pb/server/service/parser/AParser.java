package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.BankFormat;
import cz.suky.pb.server.domain.Transaction;
import cz.suky.pb.server.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.List;

/**
 * Created by none_ on 06-Nov-16.
 */
public abstract class AParser implements Parser {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> parse(Account account, InputStream inputStream, BankFormat bankFormat) {
        List<Transaction> transactions = parseInternal(account, inputStream, bankFormat);
        fillAccount(account, transactions);
        return transactions;
    }

    protected abstract List<Transaction> parseInternal(Account account, InputStream inputStream, BankFormat bankFormat);

    private void fillAccount(final Account account, final List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            transaction.setAccount(account);
        }
    }
}
