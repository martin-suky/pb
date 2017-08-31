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
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 *
 */
public interface MonthlyBalanceRepository extends AbstractEntityRepository<MonthlyBalance> {
    MonthlyBalance findByAccountAndYearAndMonth(Account account, int year, int month);

    List<MonthlyBalance> findByAccountOrderByYearAscMonthAsc(Account accountByOwnerAndId);
}
