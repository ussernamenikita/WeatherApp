package com.nikita.bulygin.weatherapp.utils;


import android.content.Context;
import android.support.annotation.StringRes;

import com.nikita.bulygin.weatherapp.di.DIConstants;

import javax.inject.Inject;
import javax.inject.Named;

public class ContextUtils {

    private Context context;

    @Inject
    public ContextUtils(@Named(DIConstants.APP) Context context) {
        this.context = context;
    }

    public String getString(@StringRes int id){
        return context.getString(id);
    }

    public String getString(@StringRes int resId,Object ... vararg){
        return context.getString(resId,vararg);
    }


}
