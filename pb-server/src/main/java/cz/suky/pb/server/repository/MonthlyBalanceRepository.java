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

package cz.suky.pb.server.repository;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.MonthlyBalance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface MonthlyBalanceRepository extends AbstractEntityRepository<MonthlyBalance> {
    Optional<MonthlyBalance> findByAccountAndYearAndMonth(Account account, int year, int month);

    List<MonthlyBalance> findByAccountOrderByYearAscMonthAsc(Account accountByOwnerAndId);

    @Query("SELECT b FROM MonthlyBalance b WHERE b.account = :#{#lowest.account} AND (b.year > :#{#lowest.year} OR (b.year = :#{#lowest.year} AND b.month >= :#{#lowest.month})) ORDER BY b.year, b.month")
    List<MonthlyBalance> findThisAndNewerBalances(@Param("lowest") MonthlyBalance lowestBalance);
}
