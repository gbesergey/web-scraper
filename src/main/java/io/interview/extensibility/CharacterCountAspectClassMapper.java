package io.interview.extensibility;

import io.interview.extensibility.aggregators.CharacterCountAggregator;
import io.interview.extensibility.aspects.CharacterCountPageAspect;
import io.interview.extensibility.commands.CharacterCountDataCommand;
import io.interview.extensibility.commands.DataProcessingCommand;
import io.interview.extensibility.aggregators.PageAspectResultAggregator;
import io.interview.extensibility.aspects.PageAspect;
import io.interview.extensibility.aspects.PageAspectResult;
import io.interview.extensibility.printers.CharacterCountPrinter;
import io.interview.extensibility.printers.PageAspectResultPrinter;

import java.net.URL;

/**
 * Created by user on 2015-02-01.
 */
public class CharacterCountAspectClassMapper implements AspectClassMapper {
    public CharacterCountAspectClassMapper() {
    }

    @Override
    public Class<? extends DataProcessingCommand> getCommandClass() {
        return CharacterCountDataCommand.class;
    }

    @Override
    public Class<? extends PageAspectResult> getResultClass() {
        return CharacterCountPageAspect.Result.class;
    }

    @Override
    public PageAspect getPageAspectByCommand(DataProcessingCommand dataProcessingCommand, URL url) {
        return new CharacterCountPageAspect(url);
    }

    @Override
    public PageAspectResultAggregator getAggregatorByResult() {
        return new CharacterCountAggregator();
    }

    @Override
    public PageAspectResultPrinter getPrinterByResult() {
        return new CharacterCountPrinter();
    }
}
