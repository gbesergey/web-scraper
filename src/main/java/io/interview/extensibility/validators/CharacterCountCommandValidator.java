package io.interview.extensibility.validators;

import io.interview.extensibility.commands.CharacterCountDataCommand;

import java.util.Iterator;
import java.util.List;

/**
 * Created by user on 2015-02-01.
 */
public class CharacterCountCommandValidator implements CommandValidator {
    private static final String COMMAND_STRING = "-c";

    @Override
    public CharacterCountDataCommand validateParameter(List<String> parameters) {
        boolean isPresent = false;
        Iterator iterator = parameters.iterator();
        while (iterator.hasNext()) {
            if (COMMAND_STRING.equals(iterator.next())) {
                isPresent = true;
                iterator.remove();
                break;
            }
        }
        return isPresent ? new CharacterCountDataCommand() : null;
    }
}
