package cz.suky.pb.server.controller;

import cz.suky.pb.server.domain.*;
import cz.suky.pb.server.dto.TransactionSearch;
import cz.suky.pb.server.dto.UploadResponse;
import cz.suky.pb.server.exception.AccountException;
import cz.suky.pb.server.exception.ParserException;
import cz.suky.pb.server.exception.TransactionException;
import cz.suky.pb.server.repository.AccountRepository;
import cz.suky.pb.server.repository.TransactionRepository;
import cz.suky.pb.server.service.parser.ParserOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by none_ on 11/01/16.
 */
@RestController
@RequestMapping("/api/account/{accountId}/transaction")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ParserOrchestrator parserOrchestrator;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<UploadResponse> upload(User user, @PathVariable Long accountId, @RequestParam String format, @RequestParam MultipartFile file) {
        Optional<Account> account = accountRepository.findAccountByOwnerAndId(user, accountId);
        Account a = account.orElseThrow(AccountException::notFound);
        Optional<BankFormat> bankFormatOptional = a.getBank().getFormats().stream().filter(b -> b.getFormatName().equals(format)).findFirst();
        try {
            return ResponseEntity.ok(parserOrchestrator.parseAndStore(a, file.getInputStream(),
                    bankFormatOptional.orElseThrow(() -> AccountException.badRequest("This format isn't valid for selected account."))));
        } catch (IOException e) {
            throw new ParserException("No valid file in data.");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Transaction>> getAll(User user, @PathVariable Long accountId) {
        Optional<Account> account = accountRepository.findAccountByOwnerAndId(user, accountId);
        return ResponseEntity.ok(transactionRepository.findByAccount(account.orElseThrow(AccountException::notFound)));
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public ResponseEntity<List<Transaction>> searchTransaction(User user, @PathVariable Long accountId, @RequestBody final TransactionSearch searchParams) {
        Optional<Account> account = accountRepository.findAccountByOwnerAndId(user, accountId);
        return ResponseEntity.ok(transactionRepository.findByAccountAndDateBetweenOrderByDate(account.orElseThrow(AccountException::notFound), searchParams.getFrom(), searchParams.getTo()));
    }

    @RequestMapping(value = "/{transactionId}", method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<Void> deleteTransaction(User user, @PathVariable Long accountId, @PathVariable Long transactionId) {
        Optional<Account> account = accountRepository.findAccountByOwnerAndId(user, accountId);
        Long result = transactionRepository.deleteByAccountAndId(account.orElseThrow(AccountException::notFound), transactionId);
        if (result == 0) {
            throw TransactionException.notFound();
        }
        return ResponseEntity.ok().build();
    }
}
