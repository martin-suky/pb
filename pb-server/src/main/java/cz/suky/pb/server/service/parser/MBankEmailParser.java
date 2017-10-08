package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.BankFormat;
import cz.suky.pb.server.domain.Transaction;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by none_ on 06-Nov-16.
 */
@Service
public class MBankEmailParser extends HtmlParser {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final ParserUtil parserUtil;

    @Autowired
    public MBankEmailParser(ParserUtil parserUtil) {
        this.parserUtil = parserUtil;
    }

    @Override
    public BankFormat getBankFormat() {
        return BankFormat.MBANK_EMAIL;
    }

    @Override
    protected List<Transaction> parseHtml(Document parse) {
        Element overviewTable = parse.select("table").get(5);
        Elements rows = overviewTable.select("tr");
        List<Transaction> result = new ArrayList<>();
        for (int i = 2; i < rows.size() -1; i++) {
            result.add(parseRow(rows.get(i)));
        }
        return result;
    }

    private Transaction parseRow(Element row) {
        Transaction transaction = new Transaction();
        Elements tds = row.select("td");
        LocalDate date = LocalDate.parse(tds.get(2).getAllElements().first().text(), dateTimeFormatter);
        transaction.setDate(LocalDateTime.from(date.atStartOfDay()));
        transaction.setDescription(tds.get(3).text());
        transaction.setAmount(parserUtil.parseAmount(tds.get(4).text()));
        return transaction;
    }
}
