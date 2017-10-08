package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.Bank;
import cz.suky.pb.server.domain.BankFormat;
import cz.suky.pb.server.domain.MimeType;
import cz.suky.pb.server.domain.Transaction;
import cz.suky.pb.server.exception.ParserException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by none_ on 06-Nov-16.
 */
@Service
public class MBankBankingParser extends HtmlParser {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Autowired
    private ParserUtil parserUtil;

    @Override
    public BankFormat getBankFormat() {
        return BankFormat.MBANK_BANKING;
    }

    @Override
    protected List<Transaction> parseHtml(Document parse) {
        Element overviewTable = parse.select("table").get(7);
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
        LocalDate date = LocalDate.parse(tds.get(1).getAllElements().first().text(), dateTimeFormatter);
        transaction.setDate(LocalDateTime.from(date.atStartOfDay()));
        transaction.setDescription(tds.get(2).text());
        transaction.setAmount(parserUtil.parseAmount(tds.get(3).text()));
        return transaction;
    }
}
