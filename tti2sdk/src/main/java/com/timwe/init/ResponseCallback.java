package com.timwe.init;

import androidx.annotation.Keep;

@Keep
public interface ResponseCallback<T>{
        void onResponse(T response);
}