package io.interview.extensibility.aspects;

import java.net.URL;
import java.util.*;

/**
 * Created by user on 2015-01-29.
 */
public class WordCountPageAspect extends NotifyingPageAspect {
    private static final Set<Character> PUNCTIATIONS = new HashSet<Character>() {{
        add('.');
        add(',');
        add(':');
        add(';');
        add('?');
        add('!');
        add('&');
        add('\r');
        add('\n');
        add('<');
    }};
    private Map<String, Long> wordsCount;
    private Set<NotifiablePageAspect> listenerAspects;
    private Set<String> possibleCurrentWords;
    private int currentCharacterPosition;
    private boolean alreadyRefreshed;

    public WordCountPageAspect(URL url, Set<String> words) {
        super(url);
        this.wordsCount = new HashMap<>();
        for (String word : words) {
            this.wordsCount.put(word, 0L);
        }
        this.listenerAspects = new HashSet<>();
        this.possibleCurrentWords = new HashSet<>();
        refreshPossibleWords();
    }

    private void refreshPossibleWords() {
        possibleCurrentWords.clear();
        for (String word : wordsCount.keySet()) {
            possibleCurrentWords.add(word);
        }
        currentCharacterPosition = 0;
        alreadyRefreshed = true;
    }

    @Override
    public void register(NotifiablePageAspect pageAspect) {
        listenerAspects.add(pageAspect);
    }

    @Override
    public void processCharacter(char character) {
        if (!Character.isWhitespace(character) && !PUNCTIATIONS.contains(character)) {
            alreadyRefreshed = false;
            Iterator<String> possibleWords = possibleCurrentWords.iterator();
            while (possibleWords.hasNext()) {
                String word = possibleWords.next();
                if (word.length() == currentCharacterPosition || word.charAt(currentCharacterPosition) != character) {
                    possibleWords.remove();
                }
            }
            currentCharacterPosition++;
        } else {
            if (!alreadyRefreshed) {
                // zero or one element (cause possibleCurrentWords is Set)
                for (String word : possibleCurrentWords) {
                    if (word.length() == currentCharacterPosition) {
                    wordsCount.put(word, wordsCount.get(word) + 1);
                    for (NotifiablePageAspect aspect : listenerAspects) {
                        aspect.notifyAspect(word);
                    }}
                }
                refreshPossibleWords();
            }
        }
    }

    @Override
    public Result getResult() {
        return new Result(this.getUrl().toString(), wordsCount);
    }

    public static final class Result extends PageAspectResult {
        private Map<String, Long> wordsCount;

        public Result(String url, Map<String, Long> wordsCount) {
            super(url);
            this.wordsCount = wordsCount;
        }

        public Map<String, Long> getWordsCount() {
            return wordsCount;
        }
    }
}
