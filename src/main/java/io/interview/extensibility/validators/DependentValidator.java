package io.interview.extensibility.validators;

/**
 * Created by user on 2015-02-01.
 */
public interface DependentValidator extends CommandValidator {
    void notifyResult(boolean validationResult);
}
