package io.interview.extensibility.printers;

import io.interview.extensibility.aspects.PageAspectResult;
import io.interview.extensibility.aspects.CharacterCountPageAspect;

/**
 * Created by user on 2015-02-01.
 */
public class CharacterCountPrinter implements PageAspectResultPrinter {
    private static final String CHARACTER_COUNT_STRING = "character count - %d\n";

    public CharacterCountPrinter() {

    }

    @Override
    public String print(PageAspectResult pageAspectResult) {
        return String.format(CHARACTER_COUNT_STRING, ((CharacterCountPageAspect.Result) pageAspectResult).getCharacterCount());
    }
}
