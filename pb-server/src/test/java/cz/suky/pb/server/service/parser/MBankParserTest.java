package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.*;
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
    private Parser mBankParser;

    @Before
    public void setup() {
        account = new Account();
        account.setName("MBank mKonto");
        account.setId(1L);
        account.setBank(Bank.MBANK);

        this.mBankParser = new MBankBankingParser(new ParserUtilImpl());
    }

    @Test
    public void testParse() throws IOException {
        try (InputStream inputStream = new FileInputStream("src/test/resource/mKonto_nr_2659    _za_2017-09.htm")) {
            List<Transaction> result = mBankParser.parse(account, inputStream, BankFormat.MBANK_EMAIL);
            assertNotNull(result);
            assertEquals(9, result.size());
        }
    }
}
