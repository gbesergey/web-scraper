package io.interview.extensibility.aspects;

import java.net.URL;
import java.util.EventListener;

/**
 * Created by user on 2015-01-29.
 */
public abstract class PageAspect implements EventListener {
    private URL url;

    public PageAspect(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public abstract void processCharacter(char character);

    public abstract PageAspectResult getResult();
}
