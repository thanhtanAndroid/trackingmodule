package com.example.templatemodule.analystic.trackers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.templatemodule.analystic.UserPropertyFacebook;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.example.templatemodule.analystic.AnalyticsManager;
import com.example.templatemodule.analystic.Event;
import com.example.templatemodule.analystic.Tracker;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;


public class FacebookTracker extends Tracker {
    Context mContext;
    AnalyticsManager analytics;
    private AppEventsLogger loggerEvent;
    private DisposableObserver<Event> eventsDisposable;
    private DisposableObserver<UserPropertyFacebook> userPropertyDisposableObserver;


    @Inject
    public FacebookTracker(AnalyticsManager analyticsManager,Context context) {
        mContext=context;
        analytics = analyticsManager;
        loggerEvent = AppEventsLogger.newLogger(mContext);
        if (isInterestedInEvents()) {
            subscribeToEvents();
            subscribeToUserPropety();
        }
    }


    // Custom tracker logic about whether this tracker
    // is interested in the Events stream
    private boolean isInterestedInEvents() {
        return true;
    }


    private void subscribeToEvents() {
        eventsDisposable = analytics.eventsStream()
                .filter(this::acceptEvent)
                .map(this::transformEvent)
                .subscribeWith(new DisposableObserver<Event>() {
                    @Override
                    public void onNext(Event event) {
                        postEvent(event);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void subscribeToUserPropety() {
        userPropertyDisposableObserver = analytics.userpropertysStreamFB()
                .filter(this::acceptUserProperty)
                .map(this::transformUserProperty)
                .subscribeWith(new DisposableObserver<UserPropertyFacebook>() {
                    @Override
                    public void onNext(UserPropertyFacebook userPropertyFacebook) {
                        postUserPropety(userPropertyFacebook);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    public boolean acceptUserProperty(@NonNull UserPropertyFacebook userPropertyFacebook) {
        return true;
    }


    // Whether this tracker is interested in this specific event
    public boolean acceptEvent(@NonNull Event event) {
        return true;
    }

    // Whether this tracker is interested in this specific UserPropertyFacebook

    protected Event transformEvent(Event event) {
        return event;
    }


    protected UserPropertyFacebook transformUserProperty(@NonNull UserPropertyFacebook userPropertyFacebook) {
        return userPropertyFacebook;
    }


    @Override
    protected void postEvent(Event event) {
        loggerEvent.logEvent(event.name, event.params);
        Log.e("tbb","postEvent:" + event.toString());
    }

    protected void postUserPropety(UserPropertyFacebook userPropertyFacebook) {
        loggerEvent.setUserID(userPropertyFacebook.key);
        loggerEvent.updateUserProperties(userPropertyFacebook.params, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                Log.e("TBao",response.toString()+"");
            }
        });
        Log.e("tbb","postUserpropetys:" + userPropertyFacebook.toString());
    }

    protected void logEvent(@NonNull Event event) {
    }

}
