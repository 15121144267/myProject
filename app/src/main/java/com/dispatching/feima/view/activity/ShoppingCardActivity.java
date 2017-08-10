package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerShoppingCardActivityComponent;
import com.dispatching.feima.dagger.module.ShoppingCardActivityModule;
import com.dispatching.feima.dagger.module.ShoppingCardListResponse;
import com.dispatching.feima.utils.ToastUtils;
import com.dispatching.feima.view.PresenterControl.ShoppingCardControl;
import com.dispatching.feima.view.adapter.ShoppingCardAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/28.
 * AddressActivity
 */

public class ShoppingCardActivity extends BaseActivity implements ShoppingCardControl.ShoppingCardView {

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_shopping_card_list)
    RecyclerView mActivityShoppingCardList;
    @BindView(R.id.activity_shopping_card_check)
    CheckBox mActivityShoppingCardCheck;
    @BindView(R.id.activity_shopping_card_price)
    TextView mActivityShoppingCardPrice;
    @BindView(R.id.activity_shopping_card_balance)
    TextView mActivityShoppingCardBalance;
    @BindView(R.id.activity_shopping_card_bottom_view)
    LinearLayout mActivityShoppingCardBottomView;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, ShoppingCardActivity.class);
        return intent;
    }

    @Inject
    ShoppingCardControl.PresenterShoppingCard mPresenter;

    private ShoppingCardAdapter mAdapter;
    private View mEmptyView;
    private Button mEmptyButton;
    private final String companyId = "53c69e54-c788-495c-bed3-2dbfc6fd5c61";
    private  List<ShoppingCardListResponse.DataBean> mProductList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_card);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText("我的购物车");
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }


    private void initView() {
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_view, (ViewGroup) mActivityShoppingCardList.getParent(), false);
        mEmptyButton = (Button) mEmptyView.findViewById(R.id.empty_go_shopping);
        RxView.clicks(mEmptyButton).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> goForShopping());
        mActivityShoppingCardList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ShoppingCardAdapter(null, this, mImageLoaderHelper);
        mActivityShoppingCardList.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CheckBox mCheckBox = (CheckBox) view.findViewById(R.id.address_default);
            switch (view.getId()) {
                case R.id.adapter_shopping_card_check:
                    if (mCheckBox.isChecked()) {
                        mCheckBox.setChecked(false);
                        //孩子们也false
                    }else {
                        mCheckBox.setChecked(true);
                    }
                    break;
                case R.id.adapter_shopping_card_edit:
                    ToastUtils.showShortToast("编辑" + position);
                    break;

            }
        });
    }

    private void initData() {
        mPresenter.requestShoppingCardList(companyId, mBuProcessor.getUserId());
    }

    @Override
    public void shoppingCardListSuccess(ShoppingCardListResponse response) {
         mProductList = response.data;
        if (mProductList != null && mProductList.size() > 0) {
            mActivityShoppingCardBottomView.setVisibility(View.VISIBLE);
            mAdapter.setNewData(mProductList);
        } else {
            mActivityShoppingCardBottomView.setVisibility(View.GONE);
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    private void goForShopping() {
        showToast("去购物");
    }

    private void initializeInjector() {
        DaggerShoppingCardActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shoppingCardActivityModule(new ShoppingCardActivityModule(ShoppingCardActivity.this, this))
                .build().inject(this);
    }
}
