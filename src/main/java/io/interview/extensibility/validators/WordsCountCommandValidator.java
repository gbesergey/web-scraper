package io.interview.extensibility.validators;

import io.interview.extensibility.commands.WordsCountDataCommand;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2015-02-01.
 */
public class WordsCountCommandValidator implements NotifyingValidator {
    private static final String COMMAND_STRING = "-w";
    private List<DependentValidator> dependentValidators;

    public WordsCountCommandValidator() {
        this.dependentValidators = new LinkedList<>();
    }

    @Override
    public WordsCountDataCommand validateParameter(List<String> parameters) throws ValidationException {
        boolean isPresent = false;
        Iterator iterator = parameters.iterator();
        while (iterator.hasNext()) {
            if (COMMAND_STRING.equals(iterator.next())) {
                isPresent = true;
                iterator.remove();
                break;
            }
        }
        Set<String> words = null;
        if (isPresent) {
            String parameter = null;
            Pattern pattern = Pattern.compile("([a-zA-Z0-9]+,)+[a-zA-Z0-9]+");
            Iterator<String> anotherIterator = parameters.iterator();
            while (anotherIterator.hasNext()) {
                parameter = anotherIterator.next();
                Matcher matcher = pattern.matcher(parameter);
                if (matcher.matches()) {
                    anotherIterator.remove();
                    break;
                }
            }
            if (parameter != null) {
                words = new HashSet<>(Arrays.asList(parameter.split(",")));
            } else {
                throw new ValidationException("No words specified for '-w' data processing command.");
            }
            for (DependentValidator dependentValidator : dependentValidators) {
                dependentValidator.notifyResult(isPresent);
            }
        }
        return isPresent ? new WordsCountDataCommand(words) : null;
    }

    @Override
    public void register(DependentValidator dependentValidator) {
        dependentValidators.add(dependentValidator);
    }
}
