package io.interview.extensibility.aggregators;

import io.interview.extensibility.aspects.PageAspectResult;

import java.util.Set;

/**
 * Created by user on 2015-02-01.
 */
public interface PageAspectResultAggregator {
    static final String AGGREGATED_URL = "all";

    PageAspectResult aggregate(Set<? extends PageAspectResult> results);
}
