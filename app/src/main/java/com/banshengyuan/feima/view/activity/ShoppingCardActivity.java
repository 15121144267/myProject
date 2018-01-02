package com.banshengyuan.feima.view.activity;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerShoppingCardActivityComponent;
import com.banshengyuan.feima.dagger.module.ShoppingCardActivityModule;
import com.banshengyuan.feima.entity.ShoppingCardListResponse;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.ShoppingCardControl;
import com.banshengyuan.feima.view.adapter.ShoppingCardAdapter;
import com.banshengyuan.feima.view.adapter.ShoppingCardItemAdapter;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
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
    @BindView(R.id.toolbar_right_text)
    TextView mToolbarRightText;
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
        return new Intent(context, ShoppingCardActivity.class);
    }

    @Inject
    ShoppingCardControl.PresenterShoppingCard mPresenter;

    private ShoppingCardAdapter mAdapter;
    private View mEmptyView;
    private ShoppingCardItemAdapter mShoppingCardItemAdapter;
    private Integer mChildPosition;
    private Integer mPartnerPosition;
    private List<ShoppingCardListResponse.ListBeanX> mBeanXList;
    private ShoppingCardListResponse.ListBeanX.ListBean mChildProduct;
    private Integer originalNumber;

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

    @Override
    public void setChildAdapter(Integer parentPosition, ShoppingCardItemAdapter itemAdapter, CheckBox partnerCheckBox) {
        mPartnerPosition = parentPosition;
        ShoppingCardListResponse.ListBeanX mProduct = mAdapter.getItem(parentPosition);
        itemAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mChildPosition = position;
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.item_shopping_card_check);
            mChildProduct = itemAdapter.getItem(position);
            switch (view.getId()) {
                case R.id.item_shopping_card_check:
                    if (!checkBox.isChecked()) {
                        mChildProduct.childCheckFlag = false;
                        if (mProduct.checkFlag) {
                            partnerCheckBox.setChecked(false);
                            mProduct.checkFlag = false;
                            if (mActivityShoppingCardCheck.isChecked()) {
                                mActivityShoppingCardCheck.setChecked(false);
                            }
                        }
                    } else {
                        mChildProduct.childCheckFlag = true;
                    }

                    countPrice2(partnerCheckBox, mProduct);
                    itemAdapter.setData(position, mChildProduct);
                    break;
                case R.id.item_shopping_card_reduce:
                    Integer count = mChildProduct.number;
                    if (count - 1 == 0) {
                        showToast("宝贝不能再减少了哦");
                    } else {
                        mChildProduct.number = count - 1;
                        requestProductNumber(mChildProduct, count, itemAdapter, position);
                    }
                    break;
                case R.id.item_shopping_card_add:
                    Integer count2 = mChildProduct.number;
                    mChildProduct.number = count2 + 1;
                    requestProductNumber(mChildProduct, count2, itemAdapter, position);
                    break;
                case R.id.item_shopping_card_delete:
                case R.id.item_shopping_card__slip_delete:
                    requestDeleteProduct(mChildProduct, itemAdapter, position);
                    break;
                case R.id.item_shopping_card_price:
                case R.id.item_shopping_card_des:
                case R.id.item_shopping_card_icon:
                    startActivity(GoodDetailActivity.getIntent(this, mChildProduct.goods_id));
                    break;
            }

        });
    }

    private void requestDeleteProduct(ShoppingCardListResponse.ListBeanX.ListBean childProduct, BaseQuickAdapter adapter, Integer childPosition) {
        mShoppingCardItemAdapter = (ShoppingCardItemAdapter) adapter;
        mChildPosition = childPosition;
        mPresenter.requestDeleteProduct(childProduct.goods_id);
    }

    private void requestProductNumber(ShoppingCardListResponse.ListBeanX.ListBean childProduct, Integer count, BaseQuickAdapter adapter, Integer childPosition) {
        mShoppingCardItemAdapter = (ShoppingCardItemAdapter) adapter;
        mChildPosition = childPosition;
        originalNumber = count;
        mPresenter.requestChangeProductNumber(childProduct.goods_id, childProduct.goods_sku, childProduct.number);
    }

    @Override
    public void changeProductNumberSuccess() {
        mShoppingCardItemAdapter.setData(mChildPosition, mChildProduct);
        countPrice();
    }

    @Override
    public void changeProductNumberFail(String des) {
        showToast(des);
        mChildProduct.number = originalNumber;
    }

    @Override
    public void deleteProductSuccess() {
        showToast("刪除购物车成功");
        mShoppingCardItemAdapter.remove(mChildPosition);
        if (mShoppingCardItemAdapter.getData().size() == 0) {
            mAdapter.remove(mPartnerPosition);
        }
        if (mAdapter.getData().size() == 0) {
            mActivityShoppingCardBottomView.setVisibility(View.GONE);
            mAdapter.setEmptyView(mEmptyView);
            mToolbarRightText.setVisibility(View.GONE);
        }
    }

    @Override
    public void shoppingCardListSuccess(ShoppingCardListResponse response) {
        if (response.list != null && response.list.size() > 0) {
            mToolbarRightText.setVisibility(View.VISIBLE);
            mBeanXList = response.list;
            mActivityShoppingCardBottomView.setVisibility(View.VISIBLE);
            mAdapter.setNewData(response.list);
        } else {
            mActivityShoppingCardBottomView.setVisibility(View.GONE);
            mAdapter.setEmptyView(mEmptyView);
            mToolbarRightText.setVisibility(View.GONE);
        }

    }

    @Override
    public void shoppingCardListFail(String des) {
        mActivityShoppingCardBottomView.setVisibility(View.GONE);
        mAdapter.setEmptyView(mEmptyView);
    }

    private void initData() {
        mPresenter.requestShoppingCardList();
    }

    private void initView() {
        mToolbarRightText.setText("编辑");
        setEmptyView();
        mActivityShoppingCardPrice.setText(ValueUtil.setAllPriceText(0, this));
        RxView.clicks(mActivityShoppingCardBalance).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> goForPayShoppingCard());
        mActivityShoppingCardList.setLayoutManager(new LinearLayoutManager(this));
        RxView.clicks(mActivityShoppingCardCheck).subscribe(o -> checkForAll());
        RxView.clicks(mToolbarRightText).subscribe(o -> editContent());
        mAdapter = new ShoppingCardAdapter(null, this, ShoppingCardActivity.this, mImageLoaderHelper);
        mActivityShoppingCardList.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ShoppingCardListResponse.ListBeanX product = mAdapter.getItem(position);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.adapter_shopping_card_check);
            switch (view.getId()) {
                case R.id.adapter_shopping_card_check:
                    if (!checkBox.isChecked()) {
                        product.checkFlag = false;
                        for (ShoppingCardListResponse.ListBeanX.ListBean productsBean : product.list) {
                            productsBean.childCheckFlag = false;
                        }
                        if (mActivityShoppingCardCheck.isChecked()) {
                            mActivityShoppingCardCheck.setChecked(false);
                        }
                    } else {
                        product.checkFlag = true;
                        for (ShoppingCardListResponse.ListBeanX.ListBean productsBean : product.list) {
                            productsBean.childCheckFlag = true;
                        }
                    }
                    countPrice();
                    mAdapter.setData(position, product);
                    break;
            }
        });

    }

    private void setEmptyView() {
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_view, (ViewGroup) mActivityShoppingCardList.getParent(), false);
        ImageView imageView = (ImageView) mEmptyView.findViewById(R.id.empty_icon);
        imageView.setImageResource(R.mipmap.empty_shopping_card);
        Button emptyButton = (Button) mEmptyView.findViewById(R.id.empty_text);
        emptyButton.setText("去逛逛");
        RxView.clicks(emptyButton).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> showToast("去逛逛"));
    }

    private void goForPayShoppingCard() {

        List<ShoppingCardListResponse.ListBeanX> orderConfirm = new ArrayList<>();
        for (ShoppingCardListResponse.ListBeanX dataBean : mAdapter.getData()) {
            if (dataBean.checkFlag) {
                orderConfirm.add(dataBean);
            } else {
                List<ShoppingCardListResponse.ListBeanX.ListBean> childProduct = new ArrayList<>();
                for (ShoppingCardListResponse.ListBeanX.ListBean listBean : dataBean.list) {
                    if (listBean.childCheckFlag) {
                        childProduct.add(listBean);
                    }
                }
                if (childProduct.size() > 0) {
                    ShoppingCardListResponse.ListBeanX listBeanX = new ShoppingCardListResponse.ListBeanX();
                    listBeanX.checkFlag = dataBean.checkFlag;
                    listBeanX.couponDes = dataBean.couponDes;
                    listBeanX.couponId = dataBean.couponId;
                    listBeanX.freightWay = dataBean.freightWay;
                    listBeanX.list = childProduct;
                    listBeanX.reduceValue = dataBean.reduceValue;
                    listBeanX.remark = dataBean.remark;
                    listBeanX.shop_freight_config = dataBean.shop_freight_config;
                    listBeanX.stoer_name = dataBean.stoer_name;
                    listBeanX.store_id = dataBean.store_id;
                    listBeanX.user_ticket = dataBean.user_ticket;
                    listBeanX.reduceWay = dataBean.reduceWay;
                    orderConfirm.add(listBeanX);
                }
            }
        }


        if (orderConfirm.size() > 0) {
            ShoppingCardListResponse response = new ShoppingCardListResponse();
            response.list = orderConfirm;
            startActivity(PayActivity.getIntent(this, response, 2));
        } else {
            showToast("您还没有选择宝贝哦");
        }
    }

    private void countPrice2(CheckBox partnerCheckBox, ShoppingCardListResponse.ListBeanX mProduct) {
        countPrice();
        boolean isAllCheck = true;
        for (ShoppingCardListResponse.ListBeanX.ListBean product : mProduct.list) {
            if (!product.childCheckFlag) {
                isAllCheck = false;
            }
        }
        partnerCheckBox.setChecked(isAllCheck);
    }

    private void countPrice() {
        double allPrice = 0;
        List<ShoppingCardListResponse.ListBeanX> list = mAdapter.getData();
        if (list.size() > 0) {
            for (ShoppingCardListResponse.ListBeanX dataBean : list) {
                for (ShoppingCardListResponse.ListBeanX.ListBean product : dataBean.list) {
                    if (product.childCheckFlag) {
                        allPrice += product.goods_price * product.number;
                    }

                }
            }
        }
        mActivityShoppingCardPrice.setText(ValueUtil.setAllPriceText(allPrice, this));
    }


    private void checkForAll() {
        if (!mActivityShoppingCardCheck.isChecked()) {
            for (ShoppingCardListResponse.ListBeanX dataBean : mAdapter.getData()) {
                dataBean.checkFlag = false;
                for (ShoppingCardListResponse.ListBeanX.ListBean product : dataBean.list) {
                    product.childCheckFlag = false;
                }
            }
        } else {
            for (ShoppingCardListResponse.ListBeanX dataBean : mAdapter.getData()) {
                dataBean.checkFlag = true;
                for (ShoppingCardListResponse.ListBeanX.ListBean product : dataBean.list) {
                    product.childCheckFlag = true;
                }
            }
        }
        countPrice();
        mAdapter.notifyDataSetChanged();
    }

    private void editContent() {
        if (mAdapter.getData().size() > 0) {
            if (mToolbarRightText.getText().toString().trim().equals("编辑")) {
                mToolbarRightText.setText("保存");
                for (ShoppingCardListResponse.ListBeanX listBeanX : mAdapter.getData()) {
                    for (ShoppingCardListResponse.ListBeanX.ListBean listBean : listBeanX.list) {
                        listBean.childEditFlag = true;
                    }
                }
            } else {
                for (ShoppingCardListResponse.ListBeanX listBeanX : mAdapter.getData()) {
                    mToolbarRightText.setText("编辑");
                    for (ShoppingCardListResponse.ListBeanX.ListBean listBean : listBeanX.list) {
                        listBean.childEditFlag = false;
                    }
                }
            }
            mAdapter.notifyDataSetChanged();
        } else {
            mToolbarRightText.setVisibility(View.GONE);
        }

    }

    private void initializeInjector() {
        DaggerShoppingCardActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shoppingCardActivityModule(new ShoppingCardActivityModule(ShoppingCardActivity.this, this))
                .build().inject(this);
    }
}
