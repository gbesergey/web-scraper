package io.interview.extensibility.commands;

import java.util.Set;

/**
 * Created by user on 2015-02-01.
 */
public class WordsCountDataCommand implements DataProcessingCommand {
    private Set<String> words;

    public WordsCountDataCommand(Set<String> words) {
        this.words = words;
    }

    public Set<String> getWords() {
        return words;
    }
}
