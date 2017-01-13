package ankit.com.nbtask;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ankit.com.nbtask.Utils.FilterUtility;
import ankit.com.nbtask.Utils.MyLog;
import ankit.com.nbtask.Utils.SpaceItemDecoration;
import ankit.com.nbtask.adapter.PropertiesAdapter;
import ankit.com.nbtask.fragment.PropertyFilterFragment;
import ankit.com.nbtask.model.Property;
import ankit.com.nbtask.network.ServerAPIRoutes;
import ankit.com.nbtask.network.ServerRequestController;
import ankit.com.nbtask.network.ServerResponseHandler;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ankit
 */
public class PropertiesListActivity extends AppCompatActivity {

    private static final String TAG = PropertiesListActivity.class.getCanonicalName();
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.rvProperties)
    RecyclerView rvProperties;
    @Bind(R.id.xfabActionButton)
    FloatingActionButton xfabActionButton;
    @Bind(R.id.activity_home)
    CoordinatorLayout activityHome;
    @Bind(R.id.fragmentContainer)
    LinearLayout fragmentContainer;
    @Bind(R.id.animationView)
    View animationView;
    @Bind(R.id.txtError)
    TextView txtError;

    private PropertiesAdapter propertiesAdapter;
    private LinearLayoutManager mLayoutManager;
    private int total_count;
    private int page = 1;
    private int offset = 0;
    private boolean isLoading;
    private static final String FILTER_FRAGMENT_TAG = "FILTER_FRAGMENT";
    private List<Property> originalPropertyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initPropertiesVwAdapter();
        getPropertiesList(page);
        initToolbar();

    }

    private void getPropertiesList(int pageNo) {
        final String apiUrl = ServerAPIRoutes.getApiUrl(ServerAPIRoutes.GET_PROPERTIES_LIST, pageNo);
        ServerRequestController.get(apiUrl, new ServerResponseHandler(this) {
            @Override
            public void onSuccess(JSONObject response) {
                JSONArray dataArr = response.optJSONArray("data");
                JSONObject otherParamsObj = response.optJSONObject("otherParams");
                final int count = otherParamsObj.optInt("count", 0);
                final int tCount = otherParamsObj.optInt("total_count", 0);
                Type type = new TypeToken<List<Property>>() {}.getType();
                List<Property> properties = new Gson().fromJson(String.valueOf(dataArr), type);
                if (properties == null || properties.size() == 0) {
                    onError("No properties fount");
                } else {
                    originalPropertyList.addAll(properties);
                    final List<Property> filteredProperties = FilterUtility.getFilteredProperties(properties);
                    int demoSIze = 0;
                    offset += count;
                    txtError.setVisibility(View.GONE);
                    rvProperties.setVisibility(View.VISIBLE);
                    propertiesAdapter.addProperties(filteredProperties);
                    rvProperties.addOnScrollListener(mRecyclerViewOnScrollListener);
                }
                PropertiesListActivity.this.total_count = tCount;
                isLoading = false;
                MyLog.v("property List", originalPropertyList.toString());
            }

        }, TAG);
    }


    public void toggleFilterFragment() {
        Fragment f = getSupportFragmentManager()
                .findFragmentByTag(FILTER_FRAGMENT_TAG);
        if (f != null) {
            invalidateOptionsMenu();
            getSupportFragmentManager().beginTransaction().remove(f).commit();
            getSupportFragmentManager().popBackStack();
            invalidateOptionsMenu();
            fragmentContainer.setVisibility(View.GONE);
            xfabActionButton.setVisibility(View.VISIBLE);
        } else {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_up,
                            R.anim.slide_down,
                            R.anim.slide_up,
                            R.anim.slide_down)
                    .add(R.id.fragmentContainer, Fragment.instantiate(this, PropertyFilterFragment.class.getName()),
                            FILTER_FRAGMENT_TAG
                    ).addToBackStack(null).commit();
            fragmentContainer.setVisibility(View.VISIBLE);
            xfabActionButton.setVisibility(View.GONE);
            invalidateOptionsMenu();
        }
    }


    public void filterProperties() {
        //Updating Properties list using filter
        List<Property> desiredCenters = FilterUtility.getFilteredProperties(originalPropertyList);
        if (desiredCenters.isEmpty()) {
            onError("We could not find results for applied filters");
        } else {
            propertiesAdapter.clearAllProperties();
            propertiesAdapter.addProperties(desiredCenters);
        }
    }

    /**
     * @param errMsg will remove recycler view and show the error message
     */
    private void onError(String errMsg) {
        if (!TextUtils.isEmpty(errMsg)) {
            rvProperties.setVisibility(View.GONE);
            txtError.setVisibility(View.VISIBLE);
            txtError.setError(errMsg);
        } else {
            rvProperties.setVisibility(View.VISIBLE);
            txtError.setVisibility(View.GONE);
        }
    }

    /**
     * {@link android.support.v7.widget.RecyclerView.OnScrollListener} with help to find the itemCount for pagination.
     */
    public RecyclerView.OnScrollListener
            mRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) {
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    if (!isLoading && totalItemCount < total_count) {
                        isLoading = true;
                        getPropertiesList(page++);
                        rvProperties.removeOnScrollListener(this);
                    }
                }
            }
        }
    };

    /**
     * initializing recycler view with all its properties
     * margin difference of 4 dp {@link SpaceItemDecoration} class will handle.
     */
    private void initPropertiesVwAdapter() {
        propertiesAdapter = new PropertiesAdapter();
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvProperties.setLayoutManager(mLayoutManager);
        rvProperties.setAdapter(propertiesAdapter);
        rvProperties.addItemDecoration(new SpaceItemDecoration(4));//int space
        rvProperties.setHasFixedSize(true);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.toolbar_title));// with dummy toolbar text
    }


    @OnClick(R.id.xfabActionButton)
    public void onClick() {
        toggleFilterFragment();// open filter fragment
    }

    @Override
    public void onBackPressed() {
        Fragment filter = getSupportFragmentManager()
                .findFragmentByTag(FILTER_FRAGMENT_TAG);
        if (filter != null) {
            toggleFilterFragment();
            return;
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        //Will cancel the api request
        ServerRequestController.cancelApiCallsByTag(TAG);
    }
}
