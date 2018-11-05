package com.nikita.bulygin.weatherapp.di.components;

import android.app.Activity;

import com.nikita.bulygin.weatherapp.di.ActivityScope;
import com.nikita.bulygin.weatherapp.di.modules.ActivityModule;

import dagger.BindsInstance;
import dagger.Component;


@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
@ActivityScope
public interface ActivityComponent {

    @Component.Builder
    interface Builder {
        ActivityComponent build();

        @BindsInstance
        ActivityComponent.Builder activity(Activity activity);

        ActivityComponent.Builder appComponent(AppComponent component);
    }
}
