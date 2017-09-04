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

package cz.suky.pb.server.service;

import cz.suky.pb.server.domain.User;
import cz.suky.pb.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private DiegestUtil diegestUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(String username, String plainPassword) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(diegestUtil.sha256hash(plainPassword));
        return userRepository.save(user);
    }

    @Override
    public User getUser(String username, String plainPassword) {
        String password = diegestUtil.sha256hash(plainPassword);
        return userRepository.findByUsernameAndPassword(username, password);
    }

}
