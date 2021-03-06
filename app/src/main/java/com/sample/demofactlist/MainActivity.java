package com.sample.demofactlist;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sample.demofactlist.Adapter.FactAdapter;
import com.sample.demofactlist.Model.FactList;
import com.sample.demofactlist.ViewModel.FactsViewModel;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    private FactAdapter factAdapter;
    private ActionBar actionBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private TextView errorTextView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initiate progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage(getResources().getString(R.string.loading_dialog));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        recyclerView = findViewById(R.id.recycler_list);
        errorTextView = findViewById(R.id.text_error);

        // so that title can be updated at run time
        actionBar = getSupportActionBar();

        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initList();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        initList();
    }

    // method is made public for testing
    public void initList() {

        if (swipeRefreshLayout != null && !swipeRefreshLayout.isRefreshing()) {
            showDialog();
        }

        EspressoIdlingResouce.increment();

        if (!isNetworkAvailable()) {
            Log.e(TAG, "is Network:" + isNetworkAvailable());


            swipeRefreshLayout.setRefreshing(false);
            dismissDialog();
            recyclerView.setVisibility(View.GONE);
            errorTextView.setVisibility(View.VISIBLE);
            errorTextView.setText(getResources().getText(R.string.network_error));

            EspressoIdlingResouce.decrement();
            return;
        }

        FactsViewModel model = ViewModelProviders.of(this).get(FactsViewModel.class);
        model.observeUsers(this,
                new Observer<FactList>() {
                    @Override
                    public void onChanged(@Nullable FactList facts) {
                        EspressoIdlingResouce.decrement();
                        swipeRefreshLayout.setRefreshing(false);
                        dismissDialog();

                        if(facts == null){
                            recyclerView.setVisibility(View.GONE);
                            errorTextView.setText(getResources().getText(R.string.no_data_found));
                            errorTextView.setVisibility(View.VISIBLE);
                            return;
                        }

                        if (facts.getException() !=  null) {
                            recyclerView.setVisibility(View.GONE);
                            errorTextView.setText(getResources().getText(R.string.list_not_loaded));
                            errorTextView.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            errorTextView.setVisibility(View.GONE);

                            updateRecyclerView(facts);
                        }
                    }
                }
        );
    }

    private void updateRecyclerView(final FactList factList) {

        actionBar.setTitle(factList.getTitle());

        factAdapter = new FactAdapter(MainActivity.this, factList.getRows());

        recyclerView.setAdapter(factAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void showDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
