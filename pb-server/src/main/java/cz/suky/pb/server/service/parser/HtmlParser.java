package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.BankFormat;
import cz.suky.pb.server.domain.MimeType;
import cz.suky.pb.server.domain.Transaction;
import cz.suky.pb.server.exception.ParserException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by none_ on 06-Nov-16.
 */
public abstract class HtmlParser extends AParser {

    @Override
    protected List<Transaction> parseInternal(Account account, InputStream inputStream, BankFormat bankFormat) {
        if (!MimeType.TEXT_HTML.equals(bankFormat.getFile().mimeType)) {
            throw new IllegalArgumentException("Invalid mime type. Expected text/html.");
        }

        String htmlFile;
        try (InputStreamReader in = new InputStreamReader(inputStream, bankFormat.getFile().charset.getCharset())) {
            htmlFile = new BufferedReader(in).lines().parallel().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new ParserException("Error while parsing file", e);
        }

        return parseHtml(bankFormat, Jsoup.parse(htmlFile));
    }

    protected abstract List<Transaction> parseHtml(BankFormat bankFormat, Document parse);

}
