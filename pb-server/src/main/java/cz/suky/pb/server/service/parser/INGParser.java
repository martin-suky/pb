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
import java.util.Collections;
import java.util.List;

@Service
public class INGParser extends HtmlParser {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    private ParserUtil parserUtil;

    @Override
    public List<BankFormat> getBankFormats() {
        return Collections.singletonList(BankFormat.ING_BANKING);
    }

    @Override
    protected List<Transaction> parseHtml(BankFormat bankFormat, Document parse) {
        Element table = parse.getElementById("ALL_payments");
        Elements allTransactions = table.select(".tp-table-line");
        List<Transaction> result = new ArrayList<>();

        for (Element row : allTransactions) {
            result.add(parseRow(row));
        }

        return result;
    }

    private Transaction parseRow(Element row) {
        Transaction transaction = new Transaction();
        LocalDate date = LocalDate.parse(row.select(".transaction-datearrow-width span").first().text(), dateTimeFormatter);
        transaction.setDate(LocalDateTime.from(date.atStartOfDay()));
        transaction.setDescription(row.select(".transaction-detail-width span").first().text());
        transaction.setAmount(parserUtil.parseAmount(row.select(".tran-amount").first().text()));
        return transaction;
    }
}
