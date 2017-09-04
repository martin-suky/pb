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

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 */
@Service
public class DiegestUtilImpl implements DiegestUtil {
    @Override
    public String sha256hash(String plainText) {

        byte[] plainBytes = plainText.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.reset();
            md.update(plainBytes);
            byte[] hashedBytes = md.digest();
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("There is some issue with JVM", e);
        }
    }
}
