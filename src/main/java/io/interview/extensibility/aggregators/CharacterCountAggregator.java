package io.interview.extensibility.aggregators;

import io.interview.extensibility.aspects.CharacterCountPageAspect;
import io.interview.extensibility.aspects.PageAspectResult;

import java.util.Set;

/**
 * Created by user on 2015-02-01.
 */
public class CharacterCountAggregator implements PageAspectResultAggregator {

    @Override
    public PageAspectResult aggregate(Set<? extends PageAspectResult> results) {
        long chracterCountSum = 0;
        for (CharacterCountPageAspect.Result characterCountResult: (Set<CharacterCountPageAspect.Result>)results) {
            chracterCountSum += characterCountResult.getCharacterCount();
        }
        return new CharacterCountPageAspect.Result(AGGREGATED_URL, chracterCountSum);
    }
}
