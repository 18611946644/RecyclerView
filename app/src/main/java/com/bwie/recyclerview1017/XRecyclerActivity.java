package com.bwie.recyclerview1017;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class XRecyclerActivity extends AppCompatActivity {
    private static final String TAG = "XRecyclerActivity";
    private XRecyclerView xrvData;

    private List<String> list;
    private DataAdapter adapter;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xrecycler);
        xrvData = findViewById(R.id.xrv_data);
        initData();
        adapter = new DataAdapter(this, list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        xrvData.setLayoutManager(manager);

        xrvData.setLoadingMoreEnabled(true);



        xrvData.setAdapter(adapter);

        xrvData.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "onRefresh: ");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xrvData.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                Log.i(TAG, "onLoadMore: ");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xrvData.loadMoreComplete();
                    }
                }, 2000);
            }
        });

    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("第" + i + "条数据");
        }
    }
}
