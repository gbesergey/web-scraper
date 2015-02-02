package io.interview;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2015-01-29.
 */
public class PageFilter {
    private static final char END_OF_CONTENT = '<';
    private String beginning;
    private int currentCharacterPosition;
    private Map<String, Integer> endings;
    private boolean started;


    public PageFilter(String beginning, String[] endings) {
        this.beginning = beginning;
        this.endings = new HashMap<>();
        for (String ending : endings) {
            this.endings.put(ending, 0);
        }
        this.currentCharacterPosition = 0;
        this.started = false;
    }

    public boolean filter(char character) {
        boolean result;
        if (!started) {
            if (beginning.charAt(currentCharacterPosition) == character) {
                currentCharacterPosition++;
//                result = !started;
                if (currentCharacterPosition == beginning.length()) {
                    started = true;
                    // pass
//                    result = true;
                    currentCharacterPosition = 0;
                }
            } else {
                currentCharacterPosition = 0;
//                result = !started;
            }
            if (character == END_OF_CONTENT) {

            }
            result = character == END_OF_CONTENT || !started;
//            result = !started;
        } else {
            // even if ends on this character, this character must not be processed
            result = false;
            for (Map.Entry<String, Integer> ending : endings.entrySet()) {
                if (ending.getKey().charAt(ending.getValue()) == character) {
                    ending.setValue(ending.getValue() + 1);
                    if (ending.getValue() == ending.getKey().length()) {
                        started = false;
//                        for (Map.Entry<String, Integer> end: endings.entrySet()) {
//                            end.setValue(0);
//                        }
//                        ending.setValue(0);
                    }
                } else {
                    ending.setValue(0);
                }
            }
            if (!started) {
                for (Map.Entry<String, Integer> end : endings.entrySet()) {
                    end.setValue(0);
                }
            }
        }
        return result;
    }
}