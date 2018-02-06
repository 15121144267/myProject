package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerShopListActivityComponent;
import com.banshengyuan.feima.dagger.module.ShopListActivityModule;
import com.banshengyuan.feima.entity.BroConstant;
import com.banshengyuan.feima.entity.GoodsCommentContentRequest;
import com.banshengyuan.feima.entity.MyOrdersResponse;
import com.banshengyuan.feima.view.PresenterControl.ShopListControl;
import com.banshengyuan.feima.view.adapter.OrderCommentAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lei.he on 2017/6/29.
 * CommentActivity
 */

public class CommentActivity extends BaseActivity implements ShopListControl.ShopListView {

    private List<MyOrdersResponse.ListBean.ProductBean> mList;
    private List<GoodsCommentContentRequest> commentList;

    public static Intent getIntent(Context context, ArrayList<MyOrdersResponse.ListBean.ProductBean> mList, String order_sn) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putParcelableArrayListExtra("mList", mList);
        intent.putExtra("order_sn", order_sn);
        return intent;
    }

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_right_text)
    TextView toolbarRightText;
    @BindView(R.id.comment_recyclerview)
    RecyclerView mRecyclerview;
    @Inject
    ShopListControl.PresenterShopList mPresenter;
    private String token;
    private OrderCommentAdapter mAdapter;
    private String mOrderSn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText(R.string.comment);
        initView();
        initData();
    }


    @Override
    public void showLoading(String msg) {
        showDialogLoading(msg);
    }

    @Override
    public void dismissLoading() {
        dismissDialogLoading();
    }

    @Override
    public void showToast(String message) {
        showBaseToast(message);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void initView() {
        toolbarRightText.setVisibility(View.VISIBLE);
        toolbarRightText.setText("提交");

        mOrderSn = getIntent().getStringExtra("order_sn");
        mList = getIntent().getParcelableArrayListExtra("mList");

        mRecyclerview.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
        mAdapter = new OrderCommentAdapter(mList, CommentActivity.this, mImageLoaderHelper);
        mRecyclerview.setAdapter(mAdapter);
    }

    private void initData() {
        token = mBuProcessor.getUserToken();

    }

    private void initializeInjector() {
        DaggerShopListActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shopListActivityModule(new ShopListActivityModule(CommentActivity.this, this))
                .build().inject(this);
    }

    @Override
    public void getCommentSuccess() {
        showToast("评论成功");
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BroConstant.ORDER_TO_ORDERDETAIL));
        finish();
    }

    @OnClick(R.id.toolbar_right_text)
    public void onViewClicked() {
        toComment();
    }

    private void toComment() {
        commentList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            GoodsCommentContentRequest request = new GoodsCommentContentRequest();
            request.setGoods_id(mList.get(i).getGoods_id());
            String contentEt = mList.get(i).getContent();
            if (!TextUtils.isEmpty(contentEt)) {
                request.setContent(contentEt);
            } else {
                request.setContent("");
            }
            commentList.add(request);
        }
        mPresenter.requestPublishComment(commentList, mOrderSn, token);
    }
}
