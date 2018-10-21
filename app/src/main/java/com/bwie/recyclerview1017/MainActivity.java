package com.bwie.recyclerview1017;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<News.DataBean> list;
    private XRecyclerView rvNews;
    private NewsAdapter adapter;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvNews = findViewById(R.id.rv_news);

        initData();

        // 布局管理器LayoutManager


        // 1. 线性布局管理器，默认一个参数的构造方法实现效果和ListView相似, 默认是纵向的
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        // 第一个参数是上下文，第二个参数是控制线性布局管理器的方向，第三个参数是是否反转
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);

        // 2. 网格布局管理器，默认两个参数的构造方法和GridView类似，第二个参数是列数
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        // 第二个参数是列数，第三个是方向，第四个是是否反转
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.HORIZONTAL, true);

        // 3. 瀑布流管理器，只有两个参数的构造方法，第一个是列数，第二个是方向
//        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);

        rvNews.setLayoutManager(layoutManager);

        // 添加默认条目分割线
        rvNews.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // 默认条目动画效果
        rvNews.setItemAnimator(new DefaultItemAnimator());

        // 设置支持上拉加载更多
        rvNews.setLoadingMoreEnabled(true);
        // 设置支持下拉刷新
        rvNews.setPullRefreshEnabled(true);

        // 设置加载的监听，必须设置，否则上拉加载更多不生效
        rvNews.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "onRefresh: ");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvNews.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                Log.i(TAG, "onLoadMore: ");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvNews.loadMoreComplete();
                    }
                }, 2000);

            }
        });

        adapter = new NewsAdapter(this, list);
        // RecyclerView并没有提供默认的条目点击事件
        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                News.DataBean dataBean = list.get(position);
                Toast.makeText(MainActivity.this, dataBean.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnLongItemClickListener(new NewsAdapter.OnLongItemClickListener() {
            @Override
            public void onItemLongClick(View itemView, int position) {
                Log.i(TAG, "onItemLongClick: " + position);
                News.DataBean dataBean = list.get(position);
                list.remove(dataBean);
                // 刷新整个列表
//                adapter.notifyDataSetChanged();
                // 删除时的局部刷新
                adapter.notifyItemRemoved(position);
                // 删除条目的时候没有改变适配器整体的索引，通知下面的列表区域刷新一次
                adapter.notifyItemRangeChanged(position, list.size());
//                adapter.notifyDataSetChanged();

                // 插入时的局部刷新
//                adapter.notifyItemInserted(position);
//                // 改变时的局部刷新
//                adapter.notifyItemChanged(position);
//                // 位置移动的局部刷新
//                adapter.notifyItemMoved(1, 2);
                // 范围的局部刷新,和上面的对应
//                adapter.notifyItemRangeChanged(3,6);
//                adapter.notifyItemRangeInserted(3,5);
            }
        });

        rvNews.setAdapter(adapter);

//        rvNews没有addHeader和addFooter的方法，不能添加头布局

        Type type = new TypeToken<News>() {
        }.getType();
        HttpUtils.getInstance().get("http://www.xieast.com/api/news/news.php", new ICallBack() {
            @Override
            public void success(Object obj) {
                News news = (News) obj;
                if (news != null) {
                    List<News.DataBean> data = news.getData();
                    if (data != null) {
                        list.clear();
                        list.addAll(data);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void failed(Exception e) {

            }
        }, type);

    }


    private void initData() {
        list = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            list.add("第" + i + "条数据");
//        }
    }
}
