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
public class TransactionException extends AbstractServiceException {
    public TransactionException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public TransactionException(HttpStatus httpStatus, String message, Throwable cause) {
        super(httpStatus, message, cause);
    }

    public static TransactionException notFound() {
        return new TransactionException(HttpStatus.NOT_FOUND, "Transaction not found.");
    }
    public static TransactionException badRequest(String message) {
        return new TransactionException(HttpStatus.NOT_FOUND, message);
    }
}
