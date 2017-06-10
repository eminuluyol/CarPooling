package com.taurus.carpooling.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.taurus.carpooling.R;
import com.taurus.carpooling.core.BaseFragment;

import butterknife.BindView;

public class SplashFragment extends BaseFragment<SplashView, SplashPresenter> implements SplashView {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_splash;
    }

    @NonNull
    @Override
    public SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getPresenter().onProgressBarShow();

    }

    @Override
    public void showLoading() {

        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {

        progressBar.setVisibility(View.GONE);

    }
}
