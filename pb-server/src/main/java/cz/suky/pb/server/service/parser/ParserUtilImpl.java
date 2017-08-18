package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.Transaction;
import cz.suky.pb.server.repository.AccountRepository;
import cz.suky.pb.server.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ParserUtilImpl implements ParserUtil {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void updateAccountBalance(Account account, List<Transaction> transactions) {
        Account updatedAccount = accountRepository.getOne(account.getId());
        transactions.forEach(transaction -> updatedAccount.setBalance(updatedAccount.getBalance().add(transaction.getAmount())));
        accountRepository.save(updatedAccount);
    }

    @Override
    public List<Transaction> filter(Account account, List<Transaction> transactions) {
        int iteratorNew = 0;
        int iteratorExisting = 0;
        int countOfNew = transactions.size();
        List<Transaction> result = new ArrayList<>();
        if (transactionRepository.count() == 0) {
            return transactions;
        }
        transactions.sort(Comparator.comparing(Transaction::getDate));
        List<Transaction> existing = transactionRepository.findByAccountAndDateBetweenOrderByDate(account, transactions.get(0).getDate(), transactions.get(countOfNew - 1).getDate());
        if (existing.isEmpty()) {
            return transactions;
        }
        transactions.sort(Comparator.comparing(Transaction::hashCode));
        existing.sort(Comparator.comparing(Transaction::hashCode));
        int countOfExisting = existing.size();

        while (iteratorNew < countOfNew) {
            Transaction newT = transactions.get(iteratorNew);
            Transaction existingT = existing.get(iteratorExisting);

            if (newT.equals(existingT)) {
                iteratorNew++;
                iteratorExisting++;
            } else if (newT.hashCode() <= existingT.hashCode()) {
                result.add(newT);
                iteratorNew++;
            } else {
                iteratorExisting = iteratorExisting < countOfExisting -1 ? iteratorExisting + 1 : countOfExisting - 1;
            }
        }

        return result;
    }
}
