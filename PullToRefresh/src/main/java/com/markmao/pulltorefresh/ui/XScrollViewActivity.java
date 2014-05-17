package com.markmao.pulltorefresh.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.markmao.pulltorefresh.R;
import com.markmao.pulltorefresh.widget.XScrollView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * XScrollView demo
 *
 * @author markmjw
 * @date 2013-10-08
 */
public class XScrollViewActivity extends Activity implements XScrollView.IXScrollViewListener {
    private XScrollView mScrollView;

    private ListView mListView;

    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> mItems = new ArrayList<String>();
    private Handler mHandler;
    private int mIndex = 0;
    private static int mRefreshIndex = 0;

    public static void launch(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, XScrollViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_scroll_view);

        geneItems();
        initView();
    }

    protected void initView() {
        mHandler = new Handler();

        mScrollView = (XScrollView) findViewById(R.id.scroll_view);
        mScrollView.setPullRefreshEnable(true);
        mScrollView.setPullLoadEnable(true);
        mScrollView.setIXScrollViewListener(this);
        mScrollView.setRefreshTime(getTime());

        View content = LayoutInflater.from(this).inflate(R.layout.vw_scroll_view_content, null);

        if (null != content) {
            mListView = (ListView) content.findViewById(R.id.content_list);
            mListView.setFocusable(false);
            mListView.setFocusableInTouchMode(false);

            mAdapter = new ArrayAdapter<String>(this, R.layout.vw_list_item, mItems);
            mListView.setAdapter(mAdapter);
            measureHeight();
        }

        mScrollView.setView(content);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mIndex = ++mRefreshIndex;
                mItems.clear();
                geneItems();
                mAdapter = new ArrayAdapter<String>(XScrollViewActivity.this,
                        R.layout.vw_list_item, mItems);
                mListView.setAdapter(mAdapter);
                measureHeight();
                onLoad();
            }
        }, 2500);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                geneItems();
                mAdapter.notifyDataSetChanged();
                measureHeight();
                onLoad();
            }
        }, 2500);
    }

    private void geneItems() {
        for (int i = 0; i != 20; ++i) {
            mItems.add("Test XScrollView item " + (++mIndex));
        }
    }

    private void onLoad() {
        mScrollView.stopRefresh();
        mScrollView.stopLoadMore();
        mScrollView.setRefreshTime(getTime());
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    private int measureHeight() {
        // get ListView adapter
        ListAdapter adapter = mListView.getAdapter();
        if (null == adapter) {
            return 0;
        }

        int totalHeight = 0;

        for (int i = 0, len = adapter.getCount(); i < len; i++) {
            View item = adapter.getView(i, null, mListView);
            if (null == item) continue;
            // measure each item width and height
            item.measure(0, 0);
            // calculate all height
            totalHeight += item.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = mListView.getLayoutParams();

        if (null == params) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        // calculate ListView height
        params.height = totalHeight + (mListView.getDividerHeight() * (adapter.getCount() - 1));

        mListView.setLayoutParams(params);

        return params.height;
    }
}
