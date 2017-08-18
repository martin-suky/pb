package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.MonthlyBalance;
import cz.suky.pb.server.domain.Transaction;
import cz.suky.pb.server.repository.AccountRepository;
import cz.suky.pb.server.repository.MonthlyBalanceRepository;
import cz.suky.pb.server.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ParserUtilImpl implements ParserUtil {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MonthlyBalanceRepository monthlyBalanceRepository;

    @Override
    public void updateAccountBalance(Account account, List<Transaction> transactions) {
        MonthlyBalance monthlyBalance = null;
        Account updatedAccount = accountRepository.getOne(account.getId());
        transactions.sort(Comparator.comparing(Transaction::getDate));
        for (Transaction transaction : transactions) {
            int year = transaction.getDate().getYear();
            int month = transaction.getDate().getMonthValue();
            if (monthlyBalance == null || monthlyBalance.getYear() != year || monthlyBalance.getMonth() != month) {
                MonthlyBalance fromDb = monthlyBalanceRepository.findByAccountAndYearAndMonth(updatedAccount, year, month);
                monthlyBalance = fromDb != null ? fromDb : new MonthlyBalance(year, month);
            }
            if (transaction.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                monthlyBalance.getIncome().add(transaction.getAmount());
            } else {
                monthlyBalance.getExpense().add(transaction.getAmount());
            }
            monthlyBalance.getBalance().add(transaction.getAmount());
            updatedAccount.getBalance().add(transaction.getAmount());
        }
        monthlyBalanceRepository.save(monthlyBalance);
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
