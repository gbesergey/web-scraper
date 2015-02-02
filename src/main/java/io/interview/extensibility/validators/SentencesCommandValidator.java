package io.interview.extensibility.validators;

import io.interview.extensibility.commands.SentencesDataCommand;

import java.util.Iterator;
import java.util.List;

/**
 * Created by user on 2015-02-01.
 */
public class SentencesCommandValidator implements DependentValidator {
    private static final String COMMAND_STRING = "-e";
    private boolean dependsOnValidatorsResult;

    public SentencesCommandValidator() {
        dependsOnValidatorsResult = true;
    }

    @Override
    public SentencesDataCommand validateParameter(List<String> parameters) {
        SentencesDataCommand result = null;
        if (dependsOnValidatorsResult) {
            boolean isPresent = false;
            Iterator iterator = parameters.iterator();
            while (iterator.hasNext()) {
                if (COMMAND_STRING.equals(iterator.next())) {
                    isPresent = true;
                    iterator.remove();
                    break;
                }
            }
            if (isPresent) {
                result = new SentencesDataCommand();
            }
        }
        return result;
    }


    @Override
    public void notifyResult(boolean validationResult) {
        dependsOnValidatorsResult &= validationResult;
    }
}
