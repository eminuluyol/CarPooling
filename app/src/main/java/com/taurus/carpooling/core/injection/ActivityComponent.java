package com.taurus.carpooling.core.injection;

import com.taurus.carpooling.core.NoOpPresenter;
import com.taurus.carpooling.splash.SplashPresenter;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(NoOpPresenter noOpPresenter);

    void inject(SplashPresenter splashPresenter);

}
