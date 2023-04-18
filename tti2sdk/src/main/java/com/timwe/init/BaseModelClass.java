package com.timwe.init;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;


@Keep
 public abstract class BaseModelClass {
    @NonNull
    @Override
    public String toString() {
        return Utils.instaceToFormatedJsonString(this);
    }
}