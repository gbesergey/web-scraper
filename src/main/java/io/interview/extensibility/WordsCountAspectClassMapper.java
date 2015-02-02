package io.interview.extensibility;

import io.interview.extensibility.aggregators.WordCountAggregator;
import io.interview.extensibility.aspects.WordCountPageAspect;
import io.interview.extensibility.commands.DataProcessingCommand;
import io.interview.extensibility.aggregators.PageAspectResultAggregator;
import io.interview.extensibility.aspects.PageAspect;
import io.interview.extensibility.aspects.PageAspectResult;
import io.interview.extensibility.commands.WordsCountDataCommand;
import io.interview.extensibility.printers.PageAspectResultPrinter;
import io.interview.extensibility.printers.WordCountPrinter;

import java.net.URL;

/**
 * Created by user on 2015-02-01.
 */
public class WordsCountAspectClassMapper implements AspectClassMapper {
    public WordsCountAspectClassMapper() {
    }

    @Override
    public Class<? extends DataProcessingCommand> getCommandClass() {
        return WordsCountDataCommand.class;
    }

    @Override
    public Class<? extends PageAspectResult> getResultClass() {
        return WordCountPageAspect.Result.class;
    }

    @Override
    public PageAspect getPageAspectByCommand(DataProcessingCommand dataProcessingCommand, URL url) {
        return new WordCountPageAspect(url, ((WordsCountDataCommand)dataProcessingCommand).getWords());
    }

    @Override
    public PageAspectResultAggregator getAggregatorByResult() {
        return new WordCountAggregator();
    }

    @Override
    public PageAspectResultPrinter getPrinterByResult() {
        return new WordCountPrinter();
    }
}
