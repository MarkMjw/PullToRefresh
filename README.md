PullToRefresh
===========

*注：本项目使用Android Studio开发*

### 根据Maxwin的XListView改造而来: ###
    1.完善下拉刷新上拉加载更多的功能
    2.并增加对ScrollView的支持
    3.增加自动刷新以及滚动到底部自动加载功能

原XListView参考链接：https://github.com/Maxwin-z/XListView-Android<br>

## **XListView使用示例** ##

设置XListView相关属性<br>
```java
mListView = (XListView) findViewById(R.id.list_view);
mListView.setPullRefreshEnable(true);
mListView.setPullLoadEnable(true);
mListView.setXListViewListener(this);
mListView.setRefreshTime(getTime());

mAdapter = new ArrayAdapter<String>(this, R.layout.vw_list_item, items);
mListView.setAdapter(mAdapter);
```

下拉刷新，下拉加载更多使用示例<br>
```java
@Override
public void onRefresh() {
    mHandler.postDelayed(new Runnable() {
        @Override
        public void run() {
            mIndex = ++mRefreshIndex;
            items.clear();
            geneItems();
            mAdapter = new ArrayAdapter<String>(XListViewActivity.this, R.layout.vw_list_item,
                    items);
            mListView.setAdapter(mAdapter);
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
            onLoad();
        }
    }, 2500);
}
```


## **XScrollView使用示例** ##

设置XScrollView相关属性<br>
```java
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
```

下拉刷新，下拉加载更多使用示例<br>
```java
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
```

##Screenshots
![Screenshot 0](https://raw.github.com/MarkMjw/PullToRefresh/master/Screenshots/0.png)

![Screenshot 1](https://raw.github.com/MarkMjw/PullToRefresh/master/Screenshots/1.png)

![Screenshot 2](https://raw.github.com/MarkMjw/PullToRefresh/master/Screenshots/2.png)

![Screenshot 3](https://raw.github.com/MarkMjw/PullToRefresh/master/Screenshots/3.png)

License
=======

    Copyright (C) 2014 MarkMjw

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
