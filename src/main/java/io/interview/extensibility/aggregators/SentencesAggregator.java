package io.interview.extensibility.aggregators;

import io.interview.extensibility.aspects.PageAspectResult;
import io.interview.extensibility.aspects.SentencesPageAspect;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 2015-02-01.
 */
public class SentencesAggregator implements PageAspectResultAggregator {
    @Override
    public PageAspectResult aggregate(Set<? extends PageAspectResult> results) {
        List<SentencesPageAspect.SentenceWithWords> aggregatedSentences = new LinkedList<>();
        for (SentencesPageAspect.Result sentenceResult: (Set<SentencesPageAspect.Result>)results) {
            aggregatedSentences.addAll(sentenceResult.getSentences());
        }
        return new SentencesPageAspect.Result(AGGREGATED_URL, aggregatedSentences);
    }
}
