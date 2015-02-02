package io.interview.extensibility.aggregators;

import io.interview.extensibility.aspects.PageAspectResult;
import io.interview.extensibility.aspects.WordCountPageAspect;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by user on 2015-02-01.
 */
public class WordCountAggregator implements PageAspectResultAggregator {
    @Override
    public PageAspectResult aggregate(Set<? extends PageAspectResult> results) {
        Map<String, Long> aggregatedWordsCount = new HashMap<>();
        for (WordCountPageAspect.Result wordCountResult: (Set<WordCountPageAspect.Result>)results) {
            for (Map.Entry<String, Long> wordCount: wordCountResult.getWordsCount().entrySet()) {
                if (aggregatedWordsCount.containsKey(wordCount.getKey())) {
                    aggregatedWordsCount.put(wordCount.getKey(), aggregatedWordsCount.get(wordCount.getKey()) + wordCount.getValue());
                } else {
                    aggregatedWordsCount.put(wordCount.getKey(), wordCount.getValue());
                }
            }
        }
        return new WordCountPageAspect.Result(AGGREGATED_URL, aggregatedWordsCount);
    }
}
