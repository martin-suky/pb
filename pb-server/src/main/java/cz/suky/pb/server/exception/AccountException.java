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

package cz.suky.pb.server.exception;

import org.springframework.http.HttpStatus;

/**
 *
 */
public class AccountException extends AbstractServiceException {
    public AccountException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public AccountException(HttpStatus httpStatus, String message, Throwable cause) {
        super(httpStatus, message, cause);
    }

    public static AccountException notFound() {
        return new AccountException(HttpStatus.NOT_FOUND, "Account not found.");
    }
    public static AccountException badRequest(String message) {
        return new AccountException(HttpStatus.NOT_FOUND, message);
    }
}
