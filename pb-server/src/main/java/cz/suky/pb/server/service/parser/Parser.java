package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.Bank;
import cz.suky.pb.server.domain.MimeType;
import cz.suky.pb.server.domain.Transaction;

import java.io.InputStream;
import java.util.List;

/**
 * Created by none_ on 06-Nov-16.
 */
public interface Parser {

    Bank getBank();

    MimeType getMimeType();

    List<Transaction> parse(Account account, InputStream inputStream, MimeType mimeType);
}
