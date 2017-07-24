package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.MimeType;
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
    public List<Transaction> parse(Account account, InputStream inputStream, MimeType mimeType) {
        List<Transaction> transactions = parseInternal(account, inputStream, mimeType);
        fillAccount(account, transactions);
        return transactions;
    }

    protected abstract List<Transaction> parseInternal(Account account, InputStream inputStream, MimeType mimeType);

    private void fillAccount(final Account account, final List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            transaction.setAccount(account);
        }
    }
}
