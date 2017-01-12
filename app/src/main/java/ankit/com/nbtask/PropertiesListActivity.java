package ankit.com.nbtask;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ankit.com.nbtask.Utils.MyLog;
import ankit.com.nbtask.Utils.SpaceItemDecoration;
import ankit.com.nbtask.adapter.PropertiesAdapter;
import ankit.com.nbtask.model.Property;
import ankit.com.nbtask.network.ServerAPIRoutes;
import ankit.com.nbtask.network.ServerRequestController;
import ankit.com.nbtask.network.ServerResponseHandler;
import butterknife.Bind;
import butterknife.ButterKnife;

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

    private PropertiesAdapter propertiesAdapter;
    private LinearLayoutManager mLayoutManager;
    private int total_count;
    private int page = 1;
    private int offset = 0;
    private boolean isLoading;

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
        //TODO Just for demo
        if (pageNo > 2) return;

        final String apiUrl = ServerAPIRoutes.getApiUrl(ServerAPIRoutes.GET_PROPERTIES_LIST, pageNo);
        ServerRequestController.get(apiUrl, new ServerResponseHandler(this) {
            @Override
            public void onSuccess(JSONObject response) {
                JSONArray dataArr = response.optJSONArray("data");
                JSONObject otherParamsObj = response.optJSONObject("otherParams");
                final int count = otherParamsObj.optInt("count", 0);
                final int tCount = otherParamsObj.optInt("total_count", 0);
                Type type = new TypeToken<List<Property>>() {}.getType();
                List<Property> propertyList = new Gson().fromJson(String.valueOf(dataArr), type);
                if (propertyList == null || propertyList.size() == 0) {
                    onError("");
                } else {
                    int demoSIze = 0;
                    MyLog.showToast(PropertiesListActivity.this, "Total Count =+" + (demoSIze + propertyList.size()));
                    offset += count;
//                    txtvwErrorMsg.setVisibility(View.GONE);
                    rvProperties.setVisibility(View.VISIBLE);
                    propertiesAdapter.addProperties(propertyList);
                    rvProperties.addOnScrollListener(mRecyclerViewOnScrollListener);
                }
                PropertiesListActivity.this.total_count = tCount;
                isLoading = false;
                MyLog.v("property List", propertyList.toString());
            }

        }, TAG);
    }


    private void onError(String errMsg) {

    }

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

    private void initPropertiesVwAdapter() {
        propertiesAdapter = new PropertiesAdapter();
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvProperties.setLayoutManager(mLayoutManager);
        rvProperties.setAdapter(propertiesAdapter);
        rvProperties.addItemDecoration(new SpaceItemDecoration(8));//int space
        rvProperties.setHasFixedSize(true);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.toolbar_title));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
