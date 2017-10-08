package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.*;

import java.io.InputStream;
import java.util.List;

/**
 * Created by none_ on 06-Nov-16.
 */
public interface Parser {

    BankFormat getBankFormat();

    List<Transaction> parse(Account account, InputStream inputStream, BankFormat bankFormat);
}
