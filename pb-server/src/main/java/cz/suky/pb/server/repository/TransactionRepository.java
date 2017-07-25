package cz.suky.pb.server.repository;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.Transaction;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by none_ on 06-Nov-16.
 */
public interface TransactionRepository extends AbstractEntityRepository<Transaction> {
    List<Transaction> findByAccount(Account accountByOwnerAndId);

    List<Transaction> findByAccountAndDateOfTransactionBetween(Account accountByOwnerAndId, LocalDateTime from, LocalDateTime to);
}
