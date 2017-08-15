package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.Bank;
import cz.suky.pb.server.domain.MimeType;
import cz.suky.pb.server.domain.Transaction;
import cz.suky.pb.server.exception.ParserException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by none_ on 06-Nov-16.
 */
@Service
public class MBankParser extends HtmlParser {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private DecimalFormat decimalFormat = new DecimalFormat();

    @Override
    public Bank getBank() {
        return Bank.MBANK;
    }

    @Override
    public MimeType getMimeType() {
        return MimeType.TEXT_HTML;
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
        transaction.setAmount(parseAmount(tds.get(3).text()));
        return transaction;
    }

    private BigDecimal parseAmount(String text) {
        String replace = text.replace(".", "").replace(" ", "").replace(',', '.');
        decimalFormat.setParseBigDecimal(true);
        try {
            return (BigDecimal) decimalFormat.parse(replace);
        } catch (ParseException e) {
            throw new ParserException("mBank parser failed: cannot parse amount.");
        }
    }

    private void checkElementAndThrow(Element element, String message) {
        if (element == null) {
            throw new ParserException("mBank parser failed: " + message);
        }
    }
}
