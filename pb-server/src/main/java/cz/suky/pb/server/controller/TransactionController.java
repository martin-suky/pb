package cz.suky.pb.server.controller;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.MimeType;
import cz.suky.pb.server.domain.Transaction;
import cz.suky.pb.server.domain.User;
import cz.suky.pb.server.dto.TransactionSearch;
import cz.suky.pb.server.dto.UploadResponse;
import cz.suky.pb.server.exception.ParserException;
import cz.suky.pb.server.repository.AccountRepository;
import cz.suky.pb.server.repository.TransactionRepository;
import cz.suky.pb.server.service.parser.ParserOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<UploadResponse> upload(User user, @PathVariable Long accountId, @RequestParam MultipartFile file) {
        Account accountByOwnerAndId = accountRepository.findAccountByOwnerAndId(user, accountId);
        try {
            return ResponseEntity.ok(parserOrchestrator.parseAndStore(accountByOwnerAndId, file.getInputStream(), MimeType.TEXT_HTML));
        } catch (IOException e) {
            throw new ParserException("No valid file in data.");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Transaction>> getAll(User user, @PathVariable Long accountId) {
        Account accountByOwnerAndId = accountRepository.findAccountByOwnerAndId(user, accountId);
        return ResponseEntity.ok(transactionRepository.findByAccount(accountByOwnerAndId));
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public ResponseEntity<List<Transaction>> searchTransaction(User user, @PathVariable Long accountId, @RequestBody TransactionSearch searchParams) {
        Account accountByOwnerAndId = accountRepository.findAccountByOwnerAndId(user, accountId);
        return ResponseEntity.ok(transactionRepository.findByAccountAndDateBetweenOrderByDate(accountByOwnerAndId, searchParams.getFrom(), searchParams.getTo()));
    }
}
