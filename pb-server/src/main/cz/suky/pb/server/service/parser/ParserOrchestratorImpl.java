package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.Bank;
import cz.suky.pb.server.domain.MimeType;
import cz.suky.pb.server.domain.Transaction;
import cz.suky.pb.server.dto.UploadResponse;
import cz.suky.pb.server.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * Created by none_ on 10-Nov-16.
 */
@Service
public class ParserOrchestratorImpl implements ParserOrchestrator {

    @Autowired
    private List<AParser> parsers;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public UploadResponse parseAndStore(Account account, InputStream inputStream, MimeType mimeType) {
        AParser parser = getParser(account.getBank(), mimeType);
        List<Transaction> transactions = parser.parse(account, inputStream, mimeType);
        List<Transaction> saved = transactionRepository.save(transactions);
        return new UploadResponse(saved.size());
    }

    private AParser getParser(Bank bank, MimeType mimeType) {
        for (AParser parser : parsers) {
            if (parser.getBank().equals(bank) && parser.getMimeType().equals(mimeType)) {
                return parser;
            }
        }
        throw new IllegalArgumentException("No parser found for combination of bank:" + bank.getName() + " mimeType:" + mimeType);
    }
}
