package io.interview.extensibility.aspects;

import java.net.URL;
import java.util.*;

/**
 * Created by user on 2015-01-29.
 */
public class SentencesPageAspect extends NotifiablePageAspect {
    private static final Set<Character> PUNCTIATIONS = new HashSet<Character>() {{
        add('.');
//        add(':');
        add('!');
        add('?');
        add('<');
    }};
    private List<SentenceWithWords> sentences;
    private StringBuilder currentSentence;
    private boolean alreadyRefreshed;
    private Set<String> words;

    public SentencesPageAspect(URL url) {
        super(url);
        this.sentences = new LinkedList<>();
        this.currentSentence = new StringBuilder();
        this.words = new HashSet<>();
        refreshSentence();
    }

    private void refreshSentence() {
            currentSentence.setLength(0);
            words.clear();
            alreadyRefreshed = true;
    }

    @Override
    public void notifyAspect(Object event) {
        if (!words.contains(event)) {
            words.add((String)event);
        }
    }

    @Override
    public void processCharacter(char character) {
        if (!PUNCTIATIONS.contains(character)) {
            currentSentence.append(character);
            alreadyRefreshed = false;
        } else if (!alreadyRefreshed) {
            if (!words.isEmpty()) {
                sentences.add(new SentenceWithWords(currentSentence.toString(), new HashSet<String>(words)));
            }
            refreshSentence();
        }
    }

    @Override
    public PageAspectResult getResult() {
        return new Result(this.getUrl().toString(), sentences);
    }

    public static final class SentenceWithWords {
        private String sentence;
        private Set<String> words;

        public SentenceWithWords(String sentence, Set<String> words) {
            this.sentence = sentence;
            this.words = words;
        }

        public String getSentence() {
            return sentence;
        }

        public Set<String> getWords() {
            return words;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SentenceWithWords that = (SentenceWithWords) o;

            if (!sentence.equals(that.sentence)) return false;
            if (!words.equals(that.words)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = sentence.hashCode();
            result = 31 * result + words.hashCode();
            return result;
        }
    }

    public static final class Result extends PageAspectResult {
        private List<SentenceWithWords> sentences;

        public Result(String url, List<SentenceWithWords> sentences) {
            super(url);
            this.sentences = sentences;
        }

        public List<SentenceWithWords> getSentences() {
            return sentences;
        }
    }
}
