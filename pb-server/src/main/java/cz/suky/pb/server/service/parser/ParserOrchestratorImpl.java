package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.*;
import cz.suky.pb.server.dto.UploadResponse;
import cz.suky.pb.server.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Created by none_ on 10-Nov-16.
 */
@Service
@Transactional
public class ParserOrchestratorImpl implements ParserOrchestrator {

    @Autowired
    private List<AParser> parsers;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ParserUtil parserUtil;

    @Override
    public UploadResponse parseAndStore(Account account, InputStream inputStream, BankFormat bankFormat) {
        AParser parser = getParser(account.getBank(), bankFormat);
        List<Transaction> transactions = parser.parse(account, inputStream, bankFormat);
        List<Transaction> filtered = parserUtil.filter(account, transactions);
        List<Transaction> saved = transactionRepository.save(filtered);
        parserUtil.updateAccountBalance(account, saved);
        return new UploadResponse(transactions.size(), saved.size(), transactions.size() - filtered.size());
    }

    private AParser getParser(Bank bank, BankFormat bankFormat) {
        Optional<AParser> first = parsers.stream().filter(p -> p.getBankFormat().equals(bankFormat)).findFirst();
        return first.orElseThrow(() -> new IllegalArgumentException("No parser found for combination of bank:" + bank.getName() + " bank format:" + bankFormat));
    }
}
