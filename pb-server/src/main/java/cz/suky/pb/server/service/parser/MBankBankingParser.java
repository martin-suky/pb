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
import java.util.*;

/**
 * Created by none_ on 06-Nov-16.
 */
@Service
public class MBankBankingParser extends HtmlParser {

    public static final Map<BankFormat, ParserConfig> PARSER_CONFIG_MAP = new HashMap<>(2);

    static {
        PARSER_CONFIG_MAP.put(BankFormat.MBANK_BANKING, new ParserConfig(7, 1, 2, 3));
        PARSER_CONFIG_MAP.put(BankFormat.MBANK_EMAIL, new ParserConfig(5, 2, 3, 4));
    }

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private ParserUtil parserUtil;

    @Autowired
    public MBankBankingParser(ParserUtil parserUtil) {
        this.parserUtil = parserUtil;
    }

    @Override
    public List<BankFormat> getBankFormats() {
        return Arrays.asList(BankFormat.MBANK_BANKING, BankFormat.MBANK_EMAIL);
    }

    @Override
    protected List<Transaction> parseHtml(BankFormat bankFormat, Document parse) {
        ParserConfig parserConfig = PARSER_CONFIG_MAP.get(bankFormat);
        Element overviewTable = parse.select("table").get(parserConfig.tableIndex);
        Elements rows = overviewTable.select("tr");
        List<Transaction> result = new ArrayList<>();
        for (int i = 2; i < rows.size() -1; i++) {
            result.add(parseRow(parserConfig, rows.get(i)));
        }
        return result;
    }

    private Transaction parseRow(ParserConfig parserConfig, Element row) {
        Transaction transaction = new Transaction();
        Elements tds = row.select("td");
        LocalDate date = LocalDate.parse(tds.get(parserConfig.dateIndex).getAllElements().first().text(), dateTimeFormatter);
        transaction.setDate(LocalDateTime.from(date.atStartOfDay()));
        transaction.setDescription(tds.get(parserConfig.descriptionIndex).text());
        transaction.setAmount(parserUtil.parseAmount(tds.get(parserConfig.amountIndex).text()));
        return transaction;
    }

    private static class ParserConfig {
        public final int tableIndex;
        public final int dateIndex;
        public final int descriptionIndex;
        public final int amountIndex;

        private ParserConfig(int tableIndex, int dateIndex, int descriptionIndex, int amountIndex) {
            this.tableIndex = tableIndex;
            this.dateIndex = dateIndex;
            this.descriptionIndex = descriptionIndex;
            this.amountIndex = amountIndex;
        }
    }
}
