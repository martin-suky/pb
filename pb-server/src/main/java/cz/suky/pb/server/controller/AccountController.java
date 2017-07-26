package cz.suky.pb.server.controller;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.User;
import cz.suky.pb.server.dto.CreateAccountRequest;
import cz.suky.pb.server.exception.AccountException;
import cz.suky.pb.server.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Account controller
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Account>> getAccounts(User user) {
        return ResponseEntity.ok(accountRepository.findAccountsByOwner(user));
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<Account> getAccount(User user, @PathVariable Long accountId) {
        Account account = accountRepository.findAccountByOwnerAndId(user, accountId);
        if (null == account) {
            throw AccountException.notFound();
        }
        return ResponseEntity.ok(account);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Account> saveAccount(User user, @RequestBody CreateAccountRequest request) {
        Account account = new Account();
        account.setBank(request.getBank());
        account.setOwner(user);
        account.setName(request.getName());
        return ResponseEntity.ok(accountRepository.save(account));
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.PUT)
    public ResponseEntity<Account> updateAccount(User user, Account account) {
        return null;
    }


}
