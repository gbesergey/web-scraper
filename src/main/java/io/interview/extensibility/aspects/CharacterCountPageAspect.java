package io.interview.extensibility.aspects;

import java.net.URL;

/**
 * Created by user on 2015-01-29.
 */
public class CharacterCountPageAspect extends PageAspect {
    private long characterCount;

    public CharacterCountPageAspect(URL url) {
        super(url);
        characterCount = 0;
    }

    @Override
    public void processCharacter(char character) {
        characterCount++;
    }

    @Override
    public Result getResult() {
        return new Result(this.getUrl().toString(), characterCount);
    }

    public static final class Result extends PageAspectResult {
        private long characterCount;

        public Result(String url, long characterCount) {
            super(url);
            this.characterCount = characterCount;
        }

        public long getCharacterCount() {
            return characterCount;
        }

        public void setCharacterCount(long characterCount) {
            this.characterCount = characterCount;
        }
    }
}
