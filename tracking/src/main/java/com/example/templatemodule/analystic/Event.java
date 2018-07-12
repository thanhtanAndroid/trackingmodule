package com.example.templatemodule.analystic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;


public class Event {

    @NonNull
    public final String name;
    @Nullable
    public final Bundle params;

    public Event(@NonNull String name, @Nullable Bundle params) {
        this.name = name;
        this.params = params;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", params=" + params.toString() +
                '}';
    }
}
