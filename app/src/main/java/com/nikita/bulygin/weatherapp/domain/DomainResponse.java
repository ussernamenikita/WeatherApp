package com.nikita.bulygin.weatherapp.domain;


import android.support.annotation.Nullable;

public class DomainResponse <T> {

    public enum RESULT_STATUS{SUCCESS,ERROR}
    @Nullable
    private T data = null;
    @Nullable
    private int errorCode = 0;
    @Nullable
    private RESULT_STATUS status = null;

    public DomainResponse(@Nullable T data, int errorCode, @Nullable RESULT_STATUS status) {
        this.data = data;
        this.errorCode = errorCode;
        this.status = status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }

    @Nullable
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(@Nullable int errorCode) {
        this.errorCode = errorCode;
    }

    @Nullable
    public RESULT_STATUS getStatus() {
        return status;
    }

    public void setStatus(@Nullable RESULT_STATUS status) {
        this.status = status;
    }
}
