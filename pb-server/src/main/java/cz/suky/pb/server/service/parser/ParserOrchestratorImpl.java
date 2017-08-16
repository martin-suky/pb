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
import java.util.ArrayList;
import java.util.Comparator;
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
        List<Transaction> filtered = this.filter(account, transactions);
        List<Transaction> saved = transactionRepository.save(filtered);
        return new UploadResponse(transactions.size(), saved.size(), transactions.size() - filtered.size());
    }

    private List<Transaction> filter(Account account, List<Transaction> transactions) {
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

    private AParser getParser(Bank bank, MimeType mimeType) {
        for (AParser parser : parsers) {
            if (parser.getBank().equals(bank) && parser.getMimeType().equals(mimeType)) {
                return parser;
            }
        }
        throw new IllegalArgumentException("No parser found for combination of bank:" + bank.getName() + " mimeType:" + mimeType);
    }
}
