package com.nikita.bulygin.weatherapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.nikita.bulygin.weatherapp.databinding.FragmentWeatherByCityBinding;
import com.nikita.bulygin.weatherapp.di.InjectableActivity;
import com.nikita.bulygin.weatherapp.di.components.ActivityComponent;
import com.nikita.bulygin.weatherapp.di.modules.WeatherByCityModule;
import com.nikita.bulygin.weatherapp.domain.entities.City;
import com.nikita.bulygin.weatherapp.vm.IWeatherViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;


public class WeatherFragment extends Fragment implements IWeatherView, AdapterView.OnItemClickListener {

    @Inject
    IWeatherViewModel weatherViewModel;

    @Inject
    FragmentWeatherByCityBinding weatherByCityBinding;

    @Inject
    CitesArrayAdapter arrayAdapter;

    @Inject
    WeatherRecyclerViewAdapter weatherRecyclerViewAdapter;

    @Override
    public void updateState(@NotNull WeatherByCityViewState state) {
        if (state.getCitiesList() != null) {
            arrayAdapter.setData(state.getCitiesList());
        } else {
            arrayAdapter.setData(new ArrayList<>());
        }
        weatherRecyclerViewAdapter.setList(state.getWeatherList() == null ? new ArrayList<>() : state.getWeatherList());
        weatherRecyclerViewAdapter.notifyDataSetChanged();
        weatherByCityBinding.progressBar.setVisibility(state.getLogingInProgress() ? View.VISIBLE : View.GONE);
        if (state.getCurrentError() != null && !"".equals(state.getCurrentError())) {
            this.showError(state.getCurrentError());
        }
    }

    private void showError(String currentError) {
        Toast.makeText(getContext(), currentError, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initiateInjection();
        super.onCreate(savedInstanceState);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), manager.getOrientation());
        weatherByCityBinding.weatherRv.addItemDecoration(itemDecoration);
        weatherByCityBinding.weatherRv.setLayoutManager(manager);
        weatherByCityBinding.weatherRv.setAdapter(weatherRecyclerViewAdapter);
        weatherByCityBinding.searchCityAtv.setAdapter(arrayAdapter);
        weatherByCityBinding.searchCityAtv.setOnItemClickListener(this);
        weatherByCityBinding.searchCityAtv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                weatherViewModel.onCityNameChanged(s.toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        weatherViewModel.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        weatherViewModel.unbindView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return weatherByCityBinding.weatherByCityContainer;
    }

    private void initiateInjection() {
        Activity activity = getActivity();
        if (activity != null && activity instanceof InjectableActivity) {
            ActivityComponent activityComponent = ((InjectableActivity) activity).getActivityComponent();
            activityComponent.getWeatherByCityComponent(new WeatherByCityModule(this)).inject(this);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        City city = arrayAdapter.getCity(position);
        if (city != null) {
            weatherViewModel.onCitySelected(city);
        }
    }
}
