package com.timwe.init;

import androidx.annotation.Keep;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

@Keep
public class ModelData<T> {
    public enum NetworkStatus {
        SUCCESS,
        LOADING,
        FAILED,
        NONE,
    }
    private final MutableLiveData<NetworkStatus> networkDataStatus = new MutableLiveData<>(NetworkStatus.NONE);
    private T data = null;
    private Throwable throwable = null;
    private int responseCode;
    private boolean fromCache;
    private boolean handled;

    private void setNetworkDataStatus(NetworkStatus networkStatus) {
        this.networkDataStatus.setValue(networkStatus);
    }

    public void setLoadingInProgressStatus(boolean fromCache) {
        handled = false;
        this.fromCache = fromCache;
        this.networkDataStatus.setValue(NetworkStatus.LOADING);
    }

    public void setLoadingInProgressStatus() {
        handled = false;
        setLoadingInProgressStatus(false);
    }
    public void setHandled() {
       handled = true;
    }


    public void setFailedStatus(Throwable throwable, boolean fromCache) {
        handled = false;
        this.throwable = throwable;
        this.fromCache = fromCache;
        this.networkDataStatus.setValue(NetworkStatus.FAILED);
    }

    public void setFailedStatus(Throwable throwable) {
        handled = false;
        setFailedStatus(throwable, false);
    }

    public void setSuccessStatus(T data, int responseCode, boolean fromCache) {
        handled = false;
        this.responseCode = responseCode;
        this.fromCache = fromCache;
        this.data = data;
        this.networkDataStatus.setValue(NetworkStatus.SUCCESS);
    }

    public void setSuccessStatus(T data, int responseCode) {
        handled = false;
        setSuccessStatus(data, responseCode, false);
    }

    public LiveData<NetworkStatus> getNetworkDataStatus() {
        return networkDataStatus;
    }

    public T getData() {
        return data;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public boolean isFromCache() {
        return fromCache;
    }

    public boolean isHandled() {
        return handled;
    }
}
