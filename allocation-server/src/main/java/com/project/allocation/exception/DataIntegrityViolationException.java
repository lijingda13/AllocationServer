package com.project.allocation.exception;

/**
 * Exception class that represents a violation of data integrity constraints.
 * This exception should be thrown when an operation would lead to a violation of the integrity rules in the database,
 * such as a duplicate key, referential integrity violation, or any other scenarios where data could not be saved
 * or updated due to constraints defined in the database schema or business logic.
 */
public class DataIntegrityViolationException extends RuntimeException {
    /**
     * Constructs a new {@code DataIntegrityViolationException} with the specified detail message.
     * The detail message is saved for later retrieval by the {@link #getMessage()} method.
     *
     * @param message the detail message which provides more information on the nature of the data integrity violation.
     */
    public DataIntegrityViolationException(String message) {
        super(message);
    }
}
