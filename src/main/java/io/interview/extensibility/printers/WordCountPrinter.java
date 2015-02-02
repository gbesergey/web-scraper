package io.interview.extensibility.printers;

import io.interview.extensibility.aspects.PageAspectResult;
import io.interview.extensibility.aspects.WordCountPageAspect;

import java.util.Map;

/**
 * Created by user on 2015-02-01.
 */
public class WordCountPrinter implements PageAspectResultPrinter {
    private static final String WORDS_COUNT_STRING = "word count:\n";
    private static final String WORD_COUNT_STRING = "\t%s - %d\n";

    public WordCountPrinter() {}

    @Override
    public String print(PageAspectResult pageAspectResult) {
        StringBuilder result = new StringBuilder(WORDS_COUNT_STRING);
        for (Map.Entry<String, Long> wordCount: ((WordCountPageAspect.Result)pageAspectResult).getWordsCount().entrySet()) {
            result.append(String.format(WORD_COUNT_STRING, wordCount.getKey(), wordCount.getValue()));
        }
        return result.toString();
    }
}
