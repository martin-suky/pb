package cz.suky.pb.server.controller;

import cz.suky.pb.server.domain.Bank;
import cz.suky.pb.server.domain.BankFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    @RequestMapping("/{bankName}")
    public List<BankFormat> getBankFormats(@PathVariable String bankName) {
        Bank bank = Bank.valueOf(bankName.toUpperCase());
        return bank.getFormats();
    }
}
