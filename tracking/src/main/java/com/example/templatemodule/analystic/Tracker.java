package com.example.templatemodule.analystic;

import android.support.annotation.NonNull;


public abstract class Tracker<E> {

    public void trackEvent(Event event) {
        if (acceptEvent(event)) {
           // E transformedEvent = transformEvent(event);
            postEvent(event);
        }
    }

    /**
     *
     * @param event
     * @return
     */
    protected abstract boolean acceptEvent(Event event);

    /**
     * Transform the generic event to the platform-specific event.
     * @param event
     * @return
     */
    @NonNull
    protected abstract E transformEvent(Event event);

    /**
     * Send the event to the analytics platform
     * @param event
     */
    protected abstract void postEvent(Event event);

    protected abstract void logEvent(Event event);


}
