/*
 * Copyright (c) 2017 by Casenet, LLC
 *
 * This file is protected by Federal Copyright Law, with all rights
 * reserved. No part of this file may be reproduced, stored in a
 * retrieval system, translated, transcribed, or transmitted, in any
 * form, or by any means manual, electric, electronic, mechanical,
 * electro-magnetic, chemical, optical, or otherwise, without prior
 * explicit written permission from Casenet, LLC.
 */

package cz.suky.pb.server.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

/**
 *
 */
@Entity
public class MonthlyBalance extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal balance;
    private int year;
    private int month;
    @ManyToOne
    private Account account;

    protected MonthlyBalance() {
    }

    public MonthlyBalance(Account account, int year, int month) {
        this.account = account;
        this.year = year;
        this.month = month;
        this.income = BigDecimal.ZERO;
        this.expense = BigDecimal.ZERO;
        this.balance = BigDecimal.ZERO;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
