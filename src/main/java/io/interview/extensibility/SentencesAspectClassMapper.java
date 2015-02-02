package io.interview.extensibility;

import io.interview.extensibility.aggregators.SentencesAggregator;
import io.interview.extensibility.aspects.SentencesPageAspect;
import io.interview.extensibility.commands.DataProcessingCommand;
import io.interview.extensibility.aggregators.PageAspectResultAggregator;
import io.interview.extensibility.aspects.PageAspect;
import io.interview.extensibility.aspects.PageAspectResult;
import io.interview.extensibility.commands.SentencesDataCommand;
import io.interview.extensibility.printers.PageAspectResultPrinter;
import io.interview.extensibility.printers.SentencesPrinter;

import java.net.URL;

/**
 * Created by user on 2015-02-01.
 */
public class SentencesAspectClassMapper implements AspectClassMapper {
    public SentencesAspectClassMapper() {
    }

    @Override
    public Class<? extends DataProcessingCommand> getCommandClass() {
        return SentencesDataCommand.class;
    }

    @Override
    public Class<? extends PageAspectResult> getResultClass() {
        return SentencesPageAspect.Result.class;
    }

    @Override
    public PageAspect getPageAspectByCommand(DataProcessingCommand dataProcessingCommand, URL url) {
        return new SentencesPageAspect(url);
    }

    @Override
    public PageAspectResultAggregator getAggregatorByResult() {
        return new SentencesAggregator();
    }

    @Override
    public PageAspectResultPrinter getPrinterByResult() {
        return new SentencesPrinter();
    }
}
