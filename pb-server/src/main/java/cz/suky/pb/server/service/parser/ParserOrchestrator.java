package cz.suky.pb.server.service.parser;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.MimeType;
import cz.suky.pb.server.dto.UploadResponse;

import javax.transaction.Transactional;
import java.io.InputStream;

/**
 * Created by none_ on 10-Nov-16.
 */
public interface ParserOrchestrator {

    UploadResponse parseAndStore(Account account, InputStream inputStream, MimeType mimeType);
}
