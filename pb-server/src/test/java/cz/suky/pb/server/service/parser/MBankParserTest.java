package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.Bank;
import cz.suky.pb.server.domain.MimeType;
import cz.suky.pb.server.domain.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by none_ on 10-Nov-16.
 */
public class MBankParserTest {

    private Account account;
    private MBankParser mBankParser;

    @Before
    public void setup() {
        account = new Account();
        account.setName("MBank mKonto");
        account.setId(1L);
        account.setBank(Bank.MBANK);

        mBankParser = new MBankParser();

    }

    @Test
    public void testParse() throws IOException {
        try (InputStream inputStream = new FileInputStream("src/test/resource/mKonto_04302659_081001_121110.html")) {
            List<Transaction> result = mBankParser.parse(account, inputStream, MimeType.TEXT_HTML);
            assertNotNull(result);
            assertEquals(9, result.size());
        }
    }
}
