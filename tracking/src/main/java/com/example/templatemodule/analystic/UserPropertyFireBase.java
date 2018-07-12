package com.example.templatemodule.analystic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class UserPropertyFireBase {
    @Nullable
    public final String key;
    @Nullable
    public String value;
    public UserPropertyFireBase(@NonNull String key, @Nullable String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "UserPropertyFacebook{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
