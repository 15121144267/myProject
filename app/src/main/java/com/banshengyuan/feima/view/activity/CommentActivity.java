package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerShopListActivityComponent;
import com.banshengyuan.feima.dagger.module.ShopListActivityModule;
import com.banshengyuan.feima.entity.MyOrdersResponse;
import com.banshengyuan.feima.view.PresenterControl.ShopListControl;
import com.banshengyuan.feima.view.adapter.MyOrdersAdapter;
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

    private List<MyOrdersResponse.ListBean.ProductBean> mList ;

    public static Intent getIntent(Context context, ArrayList<MyOrdersResponse.ListBean.ProductBean> mList) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putParcelableArrayListExtra("mList",mList);
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
        finish();
    }

    @OnClick(R.id.toolbar_right_text)
    public void onViewClicked() {
//        String content = commentontent.getText().toString();
//        if (TextUtils.isEmpty(content)) {
//            showToast("评论不能为空");
//            return;
//        }
//        mPresenter.requestPublishComment(gId, content, token);
    }
}
