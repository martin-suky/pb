package cz.suky.pb.server.dto;

import java.io.Serializable;

/**
 * Created by none_ on 17-Jul-17.
 */
public class UploadResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private int numberOfParsedTransactions;

    public UploadResponse(int numberOfParsedTransactions) {
        this.numberOfParsedTransactions = numberOfParsedTransactions;
    }

    public int getNumberOfParsedTransactions() {
        return numberOfParsedTransactions;
    }

    public void setNumberOfParsedTransactions(int numberOfParsedTransactions) {
        this.numberOfParsedTransactions = numberOfParsedTransactions;
    }
}
