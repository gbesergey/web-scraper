package io.interview;

import io.interview.extensibility.CharacterCountAspectClassMapper;
import io.interview.extensibility.ClassMappingRegistry;
import io.interview.extensibility.SentencesAspectClassMapper;
import io.interview.extensibility.WordsCountAspectClassMapper;
import io.interview.extensibility.aggregators.PageAspectResultAggregator;
import io.interview.extensibility.aspects.PageAspectResult;
import io.interview.extensibility.commands.DataProcessingCommand;
import io.interview.extensibility.validators.*;

import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by user on 2015-01-29.
 */
public class WebScraper {
    private static final int URL_PARAMETER_INDEX = 0;

    public static void main(String[] args) {
        ClassMappingRegistry.getInstance().registerMapper(new CharacterCountAspectClassMapper());
        ClassMappingRegistry.getInstance().registerMapper(new WordsCountAspectClassMapper());
        ClassMappingRegistry.getInstance().registerMapper(new SentencesAspectClassMapper());
        List<String> parameters = new LinkedList<>(Arrays.asList(args));
        List<CommandValidator> commandValidators = new LinkedList<>();
        // todo dependency management
        commandValidators.add(new CharacterCountCommandValidator());
        NotifyingValidator wordsCountValidator = new WordsCountCommandValidator();
        DependentValidator sentencesValidator = new SentencesCommandValidator();
        wordsCountValidator.register(sentencesValidator);
        commandValidators.add(new CharacterCountCommandValidator());
        // sentences after words
        commandValidators.add(wordsCountValidator);
        commandValidators.add(sentencesValidator);
        try {
            Set<URL> urls = UrlValidator.validateParameter(parameters.get(URL_PARAMETER_INDEX));
            parameters.remove(URL_PARAMETER_INDEX);
            Set<DataProcessingCommand> commands = new HashSet<>();
            for (CommandValidator commandValidator : commandValidators) {
                DataProcessingCommand command = commandValidator.validateParameter(parameters);
                if (command != null) {
                    commands.add(command);
                }
            }
            ExecutorService executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(), 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
            List<Callable<Set<PageAspectResult>>> urlProcessors = new LinkedList<>();
            for (URL url : urls) {
                urlProcessors.add(new UrlProcessor(url, commands));
            }
            try {
                List<Future<Set<PageAspectResult>>> futures = executorService.invokeAll(urlProcessors);
                Map<String, Set<PageAspectResult>> resultsByUrl = new LinkedHashMap<>();
                for (Future<Set<PageAspectResult>> future : futures) {
                    for (PageAspectResult pageAspectResult : future.get()) {
                        if (!resultsByUrl.containsKey(pageAspectResult.getUrl())) {
                            resultsByUrl.put(pageAspectResult.getUrl(), new HashSet<PageAspectResult>());
                        }
                        resultsByUrl.get(pageAspectResult.getUrl()).add(pageAspectResult);
                    }
                }
                executorService.shutdown();
                Map<Class<? extends PageAspectResult>, Set<PageAspectResult>> resultsByAspect = new HashMap<>();
                for (Set<PageAspectResult> pageAspectResults : resultsByUrl.values()) {
                    for (PageAspectResult pageAspectResult : pageAspectResults) {
                        if (!resultsByAspect.containsKey(pageAspectResult.getClass())) {
                            resultsByAspect.put(pageAspectResult.getClass(), new HashSet<PageAspectResult>());
                        }
                        resultsByAspect.get(pageAspectResult.getClass()).add(pageAspectResult);
                    }
                }
                resultsByUrl.put(PageAspectResultAggregator.AGGREGATED_URL, new HashSet<PageAspectResult>());
                for (Map.Entry<Class<? extends PageAspectResult>, Set<PageAspectResult>> aspectResults : resultsByAspect.entrySet()) {
                    resultsByUrl.get(PageAspectResultAggregator.AGGREGATED_URL).add(ClassMappingRegistry.getInstance().getAggregatorByResult(aspectResults.getKey()).aggregate(aspectResults.getValue()));
                }
                for (Map.Entry<String, Set<PageAspectResult>> urlResults : resultsByUrl.entrySet()) {
                    System.out.println(urlResults.getKey() + ":");
                    for (PageAspectResult pageAspectResult : urlResults.getValue()) {
                        System.out.println(ClassMappingRegistry.getInstance().getPrinterByResult(pageAspectResult).print(pageAspectResult));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}
