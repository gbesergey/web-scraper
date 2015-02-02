package io.interview.extensibility.printers;

import io.interview.extensibility.aspects.PageAspectResult;
import io.interview.extensibility.aspects.SentencesPageAspect;

/**
 * Created by user on 2015-02-01.
 */
public class SentencesPrinter implements PageAspectResultPrinter {
    private static final String SENTENCES_STRING = "sentences containing words:\n";
    private static final String SENTENCE_STRING = "\t%s - ";
    private static final String WORDS_SEPARATOR = ", ";

    public SentencesPrinter() {

    }

    @Override
    public String print(PageAspectResult pageAspectResult) {
        StringBuilder result = new StringBuilder(SENTENCES_STRING);
        for (SentencesPageAspect.SentenceWithWords sentence: ((SentencesPageAspect.Result)pageAspectResult).getSentences()) {
            result.append(String.format(SENTENCE_STRING, sentence.getSentence()));
            for (String word: sentence.getWords()) {
                result.append(word).append(WORDS_SEPARATOR);
            }
            result.setLength(result.length() - 1);
            result.append("\n");
        }
        return result.toString();
    }
}
