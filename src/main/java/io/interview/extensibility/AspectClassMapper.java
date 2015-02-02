package io.interview.extensibility;

import io.interview.extensibility.commands.DataProcessingCommand;
import io.interview.extensibility.aggregators.PageAspectResultAggregator;
import io.interview.extensibility.aspects.PageAspect;
import io.interview.extensibility.aspects.PageAspectResult;
import io.interview.extensibility.printers.PageAspectResultPrinter;

import java.net.URL;

/**
 * Created by user on 2015-02-01.
 */
public interface AspectClassMapper {
    Class<? extends DataProcessingCommand> getCommandClass();
    Class<? extends PageAspectResult> getResultClass();
    PageAspect getPageAspectByCommand(DataProcessingCommand dataProcessingCommand, URL url);
    PageAspectResultAggregator getAggregatorByResult();
    PageAspectResultPrinter getPrinterByResult();
}
