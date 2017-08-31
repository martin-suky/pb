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
import java.util.*;
import java.util.function.Function;

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
            monthlyBalance = getMonthlyBalance(updatedAccount, year, month);

            updateAccountBalance(updatedAccount, transaction.getAmount());
            updateMonthlyBalance(monthlyBalance, transaction.getAmount());
        }
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
            Transaction existingT = iteratorExisting < existing.size() ? existing.get(iteratorExisting) : null;

            if (existingT == null) {
                result.add(newT);
                iteratorNew ++;
            } else if (newT.equals(existingT)) {
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

    private void updateMonthlyBalance(MonthlyBalance monthlyBalance, BigDecimal amount) {
        monthlyBalance.setBalance(monthlyBalance.getBalance().add(amount));
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            monthlyBalance.setIncome(monthlyBalance.getIncome().add(amount));
        } else {
            monthlyBalance.setExpense(monthlyBalance.getExpense().add(amount));
        }
    }

    private void updateAccountBalance(Account account, BigDecimal ammount) {
        account.setBalance(account.getBalance().add(ammount));
    }

    private MonthlyBalance getMonthlyBalance(Account updatedAccount, int year, int month) {
            MonthlyBalance balance = monthlyBalanceRepository.findByAccountAndYearAndMonth(updatedAccount, year, month);
            if (balance == null) {
                balance = monthlyBalanceRepository.save(new MonthlyBalance(updatedAccount, year, month));
            }
            return balance;
    }

}
