package com.example.templatemodule.analystic.trackers;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.templatemodule.analystic.AnalyticsManager;
import com.example.templatemodule.analystic.Event;
import com.example.templatemodule.analystic.Tracker;
import com.example.templatemodule.analystic.UserPropertyFacebook;
import com.example.templatemodule.analystic.UserPropertyFireBase;
import com.google.firebase.analytics.FirebaseAnalytics;


import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class FirebaseTracker extends Tracker {

    Context mContext;
    AnalyticsManager analytics;
    private DisposableObserver<Event> eventsDisposable;
    private DisposableObserver<UserPropertyFireBase> userPropertyDisposableObserver;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Inject
    public FirebaseTracker(AnalyticsManager analyticsManager, Context context) {
        mContext = context;
        analytics = analyticsManager;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        if (isInterestedInEvents()) {
            subscribeToEvents();
            subscribeToUserPropertys();
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

    private void subscribeToUserPropertys() {
        userPropertyDisposableObserver = analytics.userpropertysStreamFireBase()
                .filter(this::acceptUserProperty)
                .map(this::transformUserProperty)
                .subscribeWith(new DisposableObserver<UserPropertyFireBase>() {
                    @Override
                    public void onNext(UserPropertyFireBase userPropertyFireBase) {
                        postUserPropety(userPropertyFireBase);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    // Whether this tracker is interested in this specific event
    public boolean acceptEvent(@NonNull Event event) {
        return true;
    }

    public boolean acceptUserProperty(@NonNull UserPropertyFireBase userPropertyFireBase) {
        return true;
    }


    protected Event transformEvent(Event event) {
        return event;
    }


    protected UserPropertyFireBase transformUserProperty(@NonNull UserPropertyFireBase userPropertyFireBase) {
        return userPropertyFireBase;
    }

    @Override
    protected void postEvent(Event event) {
        mFirebaseAnalytics.logEvent(event.name, event.params);
        Log.e("Firebase Analyst","postEvent:" + event.toString());
    }

    protected void postUserPropety(UserPropertyFireBase userPropertyFireBase) {
        mFirebaseAnalytics.setUserProperty(userPropertyFireBase.key, userPropertyFireBase.value);
        Log.e("Firebase Analyst","postUserPropetys:" + userPropertyFireBase.toString());
    }

    protected void logEvent(@NonNull Event event) {

    }
}
