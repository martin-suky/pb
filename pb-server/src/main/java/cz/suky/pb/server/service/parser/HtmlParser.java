package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.MimeType;
import cz.suky.pb.server.domain.Transaction;
import cz.suky.pb.server.exception.ParserException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by none_ on 06-Nov-16.
 */
public abstract class HtmlParser extends AParser {

    @Override
    protected List<Transaction> parseInternal(Account account, InputStream inputStream, MimeType mimeType) {
        if (!MimeType.TEXT_HTML.equals(mimeType)) {
            throw new IllegalArgumentException("Invalid mime type. Expected text/html.");
        }

        String htmlFile;
        try {
            htmlFile = new BufferedReader(new InputStreamReader(inputStream, getBank().getCharsetForMimeType(mimeType).getCharset())).lines().parallel().collect(Collectors.joining("\n"));
        } catch (UnsupportedEncodingException e) {
            throw new ParserException("Error while parsing file", e);
        }

        return parseHtml(Jsoup.parse(htmlFile));
    }

    protected abstract List<Transaction> parseHtml(Document parse);

}
