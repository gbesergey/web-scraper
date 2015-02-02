package io.interview;

import io.interview.extensibility.ClassMappingRegistry;
import io.interview.extensibility.aspects.*;
import io.interview.extensibility.commands.DataProcessingCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by user on 2015-01-29.
 */
public class UrlProcessor extends Thread implements Callable<Set<PageAspectResult>> {
    private URL url;
    Set<DataProcessingCommand> commands;

    public UrlProcessor(URL url, Set<DataProcessingCommand> commands) {
        this.url = url;
        this.commands = commands;
    }

    @Override
    public Set<PageAspectResult> call() throws Exception {
        Set<PageAspectResult> result = new HashSet<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            PageFilter tagFilter = new PageFilter("<", new String[]{">"});
            PageFilter scriptFilter = new PageFilter("<script", new String[]{"/>", "</script"});
            PageFilter styleFilter = new PageFilter("<style", new String[]{"/>", "</"});

            // todo dependency management
            List<PageAspect> pageAspects = new LinkedList<>();
            List<NotifyingPageAspect> notifyingPageAspects = new LinkedList<>();
            List<NotifiablePageAspect> notifiablePageAspects = new LinkedList<>();
            for (DataProcessingCommand command: commands) {
                PageAspect pageAspect = ClassMappingRegistry.getInstance().getPageAspectByCommand(command, url);
                if (pageAspect instanceof NotifyingPageAspect) {
                    notifyingPageAspects.add((NotifyingPageAspect) pageAspect);
                } else
                if (pageAspect instanceof NotifiablePageAspect) {
                    notifiablePageAspects.add((NotifiablePageAspect) pageAspect);
                } else {
                    pageAspects.add(pageAspect);
                }
            }
            for (NotifyingPageAspect notifyingPageAspect: notifyingPageAspects) {
                for (NotifiablePageAspect notifiablePageAspect: notifiablePageAspects) {
                    notifyingPageAspect.register(notifiablePageAspect);
                }
            }
//            for (NotifyingPageAspect notifyingPageAspect: notifyingPageAspects) {
                pageAspects.addAll(notifyingPageAspects);
//            }
//            for (NotifiablePageAspect notifiablePageAspect: notifiablePageAspects) {
                pageAspects.addAll(notifiablePageAspects);
//            }

//            PageAspect characterCountAspect = new CharacterCountPageAspect(url);
//            NotifyingPageAspect wordCountPageAspect = new WordCountPageAspect(url, words);
//            NotifiablePageAspect sentencesPageAspect = new SentencesPageAspect(url);
//            wordCountPageAspect.register(sentencesPageAspect);
//            pageAspects.add(characterCountAspect);
            // sentences after word
//            pageAspects.add(wordCountPageAspect);
//            pageAspects.add(sentencesPageAspect);

            char character;
            while ( (character = (char) reader.read()) != '\uFFFF') {
                boolean a = scriptFilter.filter(character);
                if ((tagFilter.filter(character) & a & styleFilter.filter(character)) /*&& character != ' ' && character != '\r' && character != '\n'*/) {
                    for (PageAspect pageAspect : pageAspects) {
                        pageAspect.processCharacter(character);
                    }
                }
            }
            for (PageAspect pageAspect: pageAspects) {
                result.add(pageAspect.getResult());
            }
//            pageResult = new PageResult((long)characterCountAspect.getResult(), (Map<String, Long>)wordCountPageAspect.getResult(), (List<String>)sentencesPageAspect.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
