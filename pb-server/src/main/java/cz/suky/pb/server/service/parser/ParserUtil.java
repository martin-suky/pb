package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ParserUtil {
    void updateAccountBalance(Account account, List<Transaction> transactions);

    List<Transaction> filter(Account account, List<Transaction> transactions);
}
