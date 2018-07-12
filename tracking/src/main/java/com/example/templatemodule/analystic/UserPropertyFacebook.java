package com.example.templatemodule.analystic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;

public class UserPropertyFacebook {

    @Nullable
    public final String key;
    @Nullable
    public Bundle params;
    public UserPropertyFacebook(@NonNull String key, @Nullable Bundle params) {
        this.key = key;
        this.params = params;
    }

    @Override
    public String toString() {
        return "UserPropertyFireBase{" +
                "key='" + key + '\'' +
                ", params=" + params +
                '}';
    }
}
