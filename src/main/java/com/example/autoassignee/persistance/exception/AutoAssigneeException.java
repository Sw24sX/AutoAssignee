package com.example.autoassignee.persistance.exception;

import java.io.Serial;

public class AutoAssigneeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4505819924771635158L;

    public AutoAssigneeException(String message, String... args) {
        super(String.format(message, args));
    }
}
