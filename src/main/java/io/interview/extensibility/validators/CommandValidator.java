package io.interview.extensibility.validators;

import io.interview.extensibility.commands.DataProcessingCommand;

import java.util.List;

/**
 * Created by user on 2015-02-01.
 */
public interface CommandValidator {
    DataProcessingCommand validateParameter(List<String> parameters) throws ValidationException;
}
