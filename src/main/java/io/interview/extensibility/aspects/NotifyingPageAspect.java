package io.interview.extensibility.aspects;

import java.net.URL;

/**
 * Created by user on 2015-01-29.
 */
public abstract class NotifyingPageAspect extends PageAspect {
    public NotifyingPageAspect(URL url) {
        super(url);
    }

    public abstract void register(NotifiablePageAspect pageAspect);
}
