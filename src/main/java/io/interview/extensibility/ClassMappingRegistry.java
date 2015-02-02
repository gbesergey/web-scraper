package io.interview.extensibility;

import io.interview.extensibility.commands.DataProcessingCommand;
import io.interview.extensibility.aggregators.PageAspectResultAggregator;
import io.interview.extensibility.aspects.PageAspect;
import io.interview.extensibility.aspects.PageAspectResult;
import io.interview.extensibility.printers.PageAspectResultPrinter;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2015-02-01.
 */
public class ClassMappingRegistry {
    private static ClassMappingRegistry instance = new ClassMappingRegistry();
    private Map<Class<? extends DataProcessingCommand>, AspectClassMapper> mappersByCommand;
    private Map<Class<? extends PageAspectResult>, AspectClassMapper> mappersByResult;

    private ClassMappingRegistry() {
        this.mappersByCommand = new HashMap<>();
        this.mappersByResult = new HashMap<>();
    }

    public static ClassMappingRegistry getInstance() {
        return instance;
    }

    public void registerMapper(AspectClassMapper mapper) {
        mappersByCommand.put(mapper.getCommandClass(), mapper);
        mappersByResult.put(mapper.getResultClass(), mapper);
    }

    public PageAspect getPageAspectByCommand(DataProcessingCommand dataProcessingCommand, URL url) {
        return mappersByCommand.get(dataProcessingCommand.getClass()).getPageAspectByCommand(dataProcessingCommand, url);
    }

    public PageAspectResultAggregator getAggregatorByResult(Class<? extends PageAspectResult> pageAspectResult) {
        return mappersByResult.get(pageAspectResult).getAggregatorByResult();
    }

    public PageAspectResultPrinter getPrinterByResult(PageAspectResult pageAspectResult) {
        return mappersByResult.get(pageAspectResult.getClass()).getPrinterByResult();
    }
}
