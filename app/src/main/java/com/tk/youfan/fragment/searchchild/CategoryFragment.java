package com.tk.youfan.fragment.searchchild;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.tk.youfan.R;
import com.tk.youfan.activity.CatagoryDetailActivity;
import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.domain.EventMessage;
import com.tk.youfan.domain.home.HomeData;
import com.tk.youfan.domain.search.Parent;
import com.tk.youfan.domain.search.Sub;
import com.tk.youfan.utils.Constants;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.SPUtils;
import com.tk.youfan.utils.UrlContants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 作者：tpkeeper on 2016/10/1 11:13
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：品类
 */
public class CategoryFragment extends BaseFragment {
    @Bind(R.id.expandable_listview)
    ExpandableListView expandableListview;
    @Bind(R.id.refreshlayout)
    MaterialRefreshLayout refreshLayout;
    private SPUtils spUtils;
    private String url;
    private static final int REFRESH = 1;
    private static final int NORMAL = 0;
    private int state = NORMAL;
    private List<Parent> parentList;
    private MyExpandableListAdapter adapter;
    private int urlType;

    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_fragment, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        spUtils = new SPUtils(mContext, Constants.SP_NAME);
        getDataFromNet();
        initRefresh();
    }

    public void getDataFromNet() {
        if (spUtils.getInt(Constants.GENDER, Constants.URL_TYPE_NO) != Constants.URL_TYPE_NO) {
            //恢复页面url
            urlType = spUtils.getInt(Constants.GENDER, Constants.URL_TYPE_NO);
            //恢复title
            url = UrlContants.KIND_PINLEI_MAN;
            switch (urlType) {
                case Constants.URL_TYPE_MAN:
                    url = UrlContants.KIND_PINLEI_MAN;
                    break;
                case Constants.URL_TYPE_WOMAN:
                    url = UrlContants.KIND_PINLEI_WOMAN;
                    break;
                case Constants.URL_TYPE_LIFE:
                    url = UrlContants.KIND_PINLEI_LIFE;
                    break;
            }
        } else {
            //默认加载男生
            url = UrlContants.KIND_PINLEI_MAN;
            spUtils.putInt(Constants.GENDER, Constants.URL_TYPE_MAN);
        }

        OkHttpUtils.get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallBack());
    }

    private class MyStringCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            LogUtil.e("okhttputils load data err ！");
        }

        @Override
        public void onResponse(String response, int id) {
            processData(response);
        }
    }

    private void processData(String response) {
        if (TextUtils.isEmpty(response)) {
            LogUtil.e("Search response is empty !!");
            return;
        }
        JSONObject jsonObject = JSON.parseObject(response);
        String data = jsonObject.getString("data");
        parentList = JSON.parseArray(data, Parent.class);

        EventBus.getDefault().post(new EventMessage(EventMessage.MESSAGE_DATA_GETED_SEARCH_CATAGORY_DATA));
    }

    @Subscribe
    public void onEventMessage(EventMessage eventMessage) {
        switch (eventMessage.getMessage()) {
            case EventMessage.MESSAGE_DATA_GETED_SEARCH_CATAGORY_DATA:
                //判断是刷新还是第一次进入
                switch (state) {
                    case NORMAL:
                        initExpandableView();
                        break;
                    case REFRESH:
//                        homeRecyclerViewAdapter.setData(moduleList);
                        expandableListview.setAdapter(adapter);
                        refreshLayout.finishRefresh();
                        //还原状态
                        state = NORMAL;
                        break;
                }
                break;
        }
    }

    private void initRefresh() {
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                //设置状态
                state = REFRESH;
                getDataFromNet();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            }
        });
    }

    private void initExpandableView() {
        adapter = new MyExpandableListAdapter();
        expandableListview.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    private class MyExpandableListAdapter implements ExpandableListAdapter {
        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getGroupCount() {
            return parentList == null ? 0 : parentList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            Parent parent = parentList.get(groupPosition);
            return parent.getSubs() == null ? 0 : parent.getSubs().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return parentList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return parentList.get(groupPosition).getSubs().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ParentHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_category_parent, parent, false);
                holder = new ParentHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ParentHolder) convertView.getTag();
            }
            //装配数据
            holder.setData(parentList.get(groupPosition));
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_category_sub, parent, false);
                holder = new ChildHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

            final Parent parent1 = parentList.get(groupPosition);
            final Sub sub = parent1.getSubs().get(childPosition);
            holder.setData(sub);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CatagoryDetailActivity.class);
                    intent.putExtra("brand_url",sub.getImg());
                    intent.putExtra("brand_code",sub.getId());
                    intent.putExtra("brand_name",sub.getCate_name());
                    mContext.startActivity(intent);
                }
            });
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void onGroupExpanded(int groupPosition) {

        }

        @Override
        public void onGroupCollapsed(int groupPosition) {

        }

        @Override
        public long getCombinedChildId(long groupId, long childId) {
            return 0;
        }

        @Override
        public long getCombinedGroupId(long groupId) {
            return 0;
        }
    }

    class ParentHolder {
        ImageView img_parent;

        public ParentHolder(View itemView) {
            this.img_parent = (ImageView) itemView.findViewById(R.id.img_parent);
        }

        public void setData(Parent parent) {
            Glide.with(mContext)
                    .load(parent.getImg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_parent);
        }
    }

    private class ChildHolder {
        TextView tv_sub;

        public ChildHolder(View itemView) {
            this.tv_sub = (TextView) itemView.findViewById(R.id.tv_sub);
        }

        public void setData(Sub sub) {
            tv_sub.setText(sub.getCate_name());
        }
    }
}
