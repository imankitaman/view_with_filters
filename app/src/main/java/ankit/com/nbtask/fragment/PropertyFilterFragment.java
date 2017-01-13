package ankit.com.nbtask.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import ankit.com.nbtask.NBTaskApplication;
import ankit.com.nbtask.PropertiesListActivity;
import ankit.com.nbtask.R;
import ankit.com.nbtask.Utils.PrefConfig;
import ankit.com.nbtask.model.Property;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static ankit.com.nbtask.Utils.UiUtil.setChecked;

/**
 * Created by ankit
 */
public class PropertyFilterFragment extends Fragment {


    @Bind(R.id.cbBhkTwo)
    AppCompatCheckBox cbBhkTwo;
    @Bind(R.id.cbBhkThree)
    AppCompatCheckBox cbBhkThree;
    @Bind(R.id.cbPropertyAp)
    AppCompatCheckBox cbPropertyAp;
    @Bind(R.id.cbPropertyIh)
    AppCompatCheckBox cbPropertyIh;
    @Bind(R.id.cbPropertyIf)
    AppCompatCheckBox cbPropertyIf;
    @Bind(R.id.cbFurnishedfully)
    AppCompatCheckBox cbFurnishedfully;
    @Bind(R.id.cbFurnishedSemi)
    AppCompatCheckBox cbFurnishedSemi;
    @Bind(R.id.cbBhkFour)
    AppCompatCheckBox cbBhkFour;
    PropertiesListActivity propertiesListActivity;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_properoty_filter, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        propertiesListActivity = (PropertiesListActivity) getActivity();
        initToobar();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Filter");
        initApartmentFilter();
        initPropertyType();
        initFurnishedType();
    }

    private void clearAllFilter() {
        NBTaskApplication.androidPreference.put(PrefConfig.ApartmentType.BHK2.name(), "");
        NBTaskApplication.androidPreference.put(PrefConfig.ApartmentType.BHK3.name(), "");
        NBTaskApplication.androidPreference.put(PrefConfig.ApartmentType.BHK4.name(), "");
        NBTaskApplication.androidPreference.put(PrefConfig.BuildingType.AP.name(), "");
        NBTaskApplication.androidPreference.put(PrefConfig.BuildingType.IF.name(), "");
        NBTaskApplication.androidPreference.put(PrefConfig.BuildingType.IH.name(), "");
        NBTaskApplication.androidPreference.put(PrefConfig.Furnishing.FULLY_FURNISHED.name(), "");
        NBTaskApplication.androidPreference.put(PrefConfig.Furnishing.SEMI_FURNISHED.name(), "");
        initApartmentFilter();
        initPropertyType();
        initFurnishedType();
    }

    private void initApartmentFilter() {

        String apartmentBhkTwo = NBTaskApplication.androidPreference.getValue(PrefConfig.ApartmentType.BHK2.name(), "");
        String apartmentBhkThree = NBTaskApplication.androidPreference.getValue(PrefConfig.ApartmentType.BHK3.name(), "");
        String apartmentBhkFour = NBTaskApplication.androidPreference.getValue(PrefConfig.ApartmentType.BHK4.name(), "");

        setChecked.accept(cbBhkTwo, apartmentBhkTwo);
        setChecked.accept(cbBhkThree, apartmentBhkThree);
        setChecked.accept(cbBhkFour, apartmentBhkFour);

        cbBhkTwo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String filteredValue = isChecked ? Property.ApartmentType.BHK2.name() : "";
            NBTaskApplication.androidPreference.put(PrefConfig.ApartmentType.BHK2.name(), filteredValue);
        });


        cbBhkThree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String filteredValue = isChecked ? Property.ApartmentType.BHK3.name() : "";
            NBTaskApplication.androidPreference.put(PrefConfig.ApartmentType.BHK3.name(), filteredValue);

        });

        cbBhkFour.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String filteredValue = isChecked ? Property.ApartmentType.BHK4.name() : "";
            NBTaskApplication.androidPreference.put(PrefConfig.ApartmentType.BHK4.name(), filteredValue);

        });
    }

    private void initPropertyType() {

        String propertyTypeAp = NBTaskApplication.androidPreference.getValue(PrefConfig.BuildingType.AP.name(), "");
        String propertyTypeIf = NBTaskApplication.androidPreference.getValue(PrefConfig.BuildingType.IF.name(), "");
        String propertyTypeIh = NBTaskApplication.androidPreference.getValue(PrefConfig.BuildingType.IH.name(), "");

        setChecked.accept(cbPropertyAp, propertyTypeAp);
        setChecked.accept(cbPropertyIf, propertyTypeIf);
        setChecked.accept(cbPropertyIh, propertyTypeIh);


        cbPropertyAp.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String filterProperty = isChecked ? Property.BuildingType.AP.name() : "";
            NBTaskApplication.androidPreference.put(PrefConfig.BuildingType.AP.name(), filterProperty);
        });

        cbPropertyIf.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String filterProperty = isChecked ? Property.BuildingType.IF.name() : "";
            NBTaskApplication.androidPreference.put(PrefConfig.BuildingType.IF.name(), filterProperty);
        });

        cbPropertyIh.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String filterProperty = isChecked ? Property.BuildingType.IH.name() : "";
            NBTaskApplication.androidPreference.put(PrefConfig.BuildingType.IH.name(), filterProperty);
        });
    }


    private void initFurnishedType() {

        String furnishedTypeFully = NBTaskApplication.androidPreference.getValue(PrefConfig.Furnishing.FULLY_FURNISHED.name(), "");
        String furnishedTypeSemi = NBTaskApplication.androidPreference.getValue(PrefConfig.Furnishing.SEMI_FURNISHED.name(), "");

        setChecked.accept(cbFurnishedfully, furnishedTypeFully);
        setChecked.accept(cbFurnishedSemi, furnishedTypeSemi);

        cbFurnishedfully.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String filterProperty = isChecked ? Property.Furnishing.FULLY_FURNISHED.name() : "";
            NBTaskApplication.androidPreference.put(PrefConfig.Furnishing.FULLY_FURNISHED.name(), filterProperty);
        });

        cbFurnishedSemi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String filterProperty = isChecked ? Property.Furnishing.SEMI_FURNISHED.name() : "";
            NBTaskApplication.androidPreference.put(PrefConfig.Furnishing.SEMI_FURNISHED.name(), filterProperty);
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_filter:
                clearAllFilter();
                break;
            case android.R.id.home:
                propertiesListActivity.toggleFilterFragment();
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    private void initToobar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24px);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btnApplyFilter)
    public void onClick() {
        propertiesListActivity.filterProperties();
        propertiesListActivity.toggleFilterFragment();

    }

}

