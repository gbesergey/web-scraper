package io.interview.extensibility.aspects;

import java.net.URL;

/**
 * Created by user on 2015-01-29.
 */
public abstract class NotifiablePageAspect extends PageAspect {
    public NotifiablePageAspect(URL url) {
        super(url);
    }

    public abstract void notifyAspect(Object event);
}
