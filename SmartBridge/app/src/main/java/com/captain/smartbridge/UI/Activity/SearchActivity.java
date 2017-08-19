package com.captain.smartbridge.UI.Activity;

import android.content.Intent;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Adapters.SearchListAdapter;
import com.captain.smartbridge.model.SearchCodeReq;
import com.captain.smartbridge.model.SearchCodeRes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fish on 17-4-25.
 */

public class SearchActivity extends AbsActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.search_toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_list)
    ListView searchList;

    int category;
    private List<SearchCodeRes> bridges = null;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_search);
    }


    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.search_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        //searchView.setIconified(false);
        //试试看能不能直接变成搜索栏
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("search", query);
                search(query);
                //                if (!searchView.isIconified()) {
                //                    searchView.setIconified(true);
                //                }
                //                searchItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void search(String key) {
        if (NetUtils.isNetworkConnected(this)) {
            SearchCodeReq searchCodeReq = new SearchCodeReq(key);
            ApiManager.getmService().search(searchCodeReq).enqueue(new Callback<List<SearchCodeRes>>() {
                @Override
                public void onResponse(Call<List<SearchCodeRes>> call, Response<List<SearchCodeRes>> response) {
                    if (response.body() == null) {
                        showToast("账户登录过期，请退出账户后重新登录");
                        return;
                    }

                    bridges = response.body();
                    SearchListAdapter searchListAdapter = new SearchListAdapter(SearchActivity.this, bridges);
                    searchList.setAdapter(searchListAdapter);
                }

                @Override
                public void onFailure(Call<List<SearchCodeRes>> call, Throwable t) {
                    t.printStackTrace();
                    showNetWorkError();
                }
            });
        } else {
            showNetWorkError();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("Code", bridges.get(position).getQldm());
        SearchActivity.this.setResult(2, intent);
        SearchActivity.this.finish();
    }
}
