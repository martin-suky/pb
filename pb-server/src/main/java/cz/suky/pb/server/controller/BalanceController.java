package cz.suky.pb.server.controller;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.MonthlyBalance;
import cz.suky.pb.server.domain.User;
import cz.suky.pb.server.repository.AccountRepository;
import cz.suky.pb.server.repository.MonthlyBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/account/{accountId}/balance")
public class BalanceController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MonthlyBalanceRepository monthlyBalanceRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<MonthlyBalance>> getAll(User user, @PathVariable Long accountId) {
        Account accountByOwnerAndId = accountRepository.findAccountByOwnerAndId(user, accountId);
        return ResponseEntity.ok(monthlyBalanceRepository.findByAccountOrderByYearAscMonthAsc(accountByOwnerAndId));
    }
}
