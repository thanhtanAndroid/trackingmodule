package com.example.templatemodule.analystic;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.templatemodule.analystic.trackers.FacebookTracker;
import com.example.templatemodule.analystic.trackers.FirebaseTracker;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.PublishSubject;


@Singleton
public class AnalyticsManager {
    public static Context context;
    public boolean isFacebookenabled, isFirebaseEnable = false;
    public static AnalyticsManager sIntance;
    private PublishSubject<Event> eventPubSub;
    private PublishSubject<UserPropertyFacebook> eventUserpropertyFacebook;
    private PublishSubject<UserPropertyFireBase> eventUserpropertyFirebase;
    private String user_id;
    private final String key_user_id = "user_id";
    @Inject
    public AnalyticsManager() {

        eventPubSub = PublishSubject.create();
        eventUserpropertyFacebook = PublishSubject.create();
        eventUserpropertyFirebase = PublishSubject.create();
    }

    public static AnalyticsManager getInstance() {
        return sIntance;
    }

    public void init(Context mcontext) {
        context = mcontext;
    }

    public void trackerUserproperty(boolean isFirebaseEnable) {
        long timestamp = Calendar.getInstance().getTimeInMillis() / 1000;
        if (DataLocal.getInstance(context).getString("user_id").equals("")) {
            user_id = "user_time_" + timestamp;
            DataLocal.getInstance(context).setString("user_id", user_id);
        }else {
            user_id = DataLocal.getInstance(context).getString("user_id");
        }
        Log.e("firebase ",isFirebaseEnable+"");
        if(isFirebaseEnable){
            trackUserPropertyFirebase(new UserPropertyFireBase(key_user_id, user_id));
        }else{
            Bundle params = new Bundle();
            params.putString("user_information",user_id);
            trackUserPropertyFacebook(new UserPropertyFacebook(key_user_id, params));
        }
    }

//    public void trackerFcm(String FcmtoKen) {
//        eventUserproperty.onNext(new UserPropertyFacebook("FCMToken", FcmtoKen));
//    }

//    public Boolean isFirebaseEnable() {
//        return isFirebaseEnable;
//    }
    public void setFacebookenabled(boolean isFacebookenabled) {
        this.isFacebookenabled = isFacebookenabled;
        loadEnabledTracker();
    }

    public void setFirebaseEnabled(boolean isFirebaseEnable) {
        this.isFirebaseEnable = isFirebaseEnable;
        loadEnabledTracker();
    }

    private void loadEnabledTracker() {
        if (isFacebookenabled) {
            new FacebookTracker(this, context);
        }

        if (isFirebaseEnable) {
            new FirebaseTracker(this, context);
        }
        trackerUserproperty(isFirebaseEnable);
    }

    public Observable<Event> eventsStream() {
        return eventPubSub;
    }

    public Observable<UserPropertyFacebook> userpropertysStreamFB() {
        return eventUserpropertyFacebook;
    }

    public Observable<UserPropertyFireBase> userpropertysStreamFireBase() {
        return eventUserpropertyFirebase;
    }

    public void trackEvent(@NonNull Event event) {
        eventPubSub.onNext(event);
    }

    public void trackUserPropertyFacebook(UserPropertyFacebook userPropertyFacebook) {
        eventUserpropertyFacebook.onNext(userPropertyFacebook);
    }

    public void trackUserPropertyFirebase(UserPropertyFireBase userPropertyFireBase) {
        eventUserpropertyFirebase.onNext(userPropertyFireBase);
    }
}
