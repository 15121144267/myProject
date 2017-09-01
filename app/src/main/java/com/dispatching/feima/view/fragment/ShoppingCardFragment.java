package com.dispatching.feima.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerFragmentComponent;
import com.dispatching.feima.dagger.module.FragmentModule;
import com.dispatching.feima.dagger.module.MainActivityModule;
import com.dispatching.feima.dagger.module.ShoppingCardListResponse;
import com.dispatching.feima.entity.BroConstant;
import com.dispatching.feima.entity.OrderConfirmedRequest;
import com.dispatching.feima.entity.PayCreateRequest;
import com.dispatching.feima.utils.SpannableStringUtils;
import com.dispatching.feima.utils.ValueUtil;
import com.dispatching.feima.view.PresenterControl.ShoppingCardControl;
import com.dispatching.feima.view.activity.MainActivity;
import com.dispatching.feima.view.activity.PayActivity;
import com.dispatching.feima.view.adapter.ShoppingCardAdapter;
import com.dispatching.feima.view.adapter.ShoppingCardItemAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderFragment
 */

public class ShoppingCardFragment extends BaseFragment implements ShoppingCardControl.ShoppingCardView {


    @BindView(R.id.fragment_shopping_card_list)
    RecyclerView mActivitiesRecycleView;
    @BindView(R.id.fragment_shopping_card_check)
    CheckBox mFragmentShoppingCardCheck;
    @BindView(R.id.fragment_shopping_card_price)
    TextView mFragmentShoppingCardPrice;
    @BindView(R.id.fragment_shopping_card_balance)
    TextView mFragmentShoppingCardBalance;
    @BindView(R.id.fragment_shopping_card_bottom_view)
    LinearLayout mFragmentShoppingCardBottomView;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static ShoppingCardFragment newInstance() {
        return new ShoppingCardFragment();
    }

    private Unbinder unbind;
    private ShoppingCardAdapter mAdapter;
    private View mEmptyView;
    private List<ShoppingCardListResponse.DataBean> mProductList;
    private ShoppingCardItemAdapter mShoppingCardItemAdapter;
    private Integer mChildPosition;
    private Integer mPartnerPosition;
    private AMapLocation mLocationInfo;
    private ShoppingCardListResponse.DataBean.ProductsBean mChildProduct;
    @Inject
    ShoppingCardControl.PresenterShoppingCard mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_card, container, false);
        unbind = ButterKnife.bind(this, view);
        mMiddleName.setText("我的购物车");
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }


    @Override
    public void shoppingCardListSuccess(ShoppingCardListResponse response) {
        mProductList = response.data;
        if (mProductList != null && mProductList.size() > 0) {
            mFragmentShoppingCardBottomView.setVisibility(View.VISIBLE);
            if (mAdapter != null) {
                mAdapter.setNewData(mProductList);
            }
        } else {
            mFragmentShoppingCardBottomView.setVisibility(View.GONE);
            mAdapter.setNewData(null);
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public void setChildAdapter(Integer parentPosition, ShoppingCardItemAdapter itemAdapter, CheckBox partnerCheckBox) {
        mShoppingCardItemAdapter = itemAdapter;
        mPartnerPosition = parentPosition;
        ShoppingCardListResponse.DataBean mProduct = mProductList.get(parentPosition);
        itemAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mChildPosition = position;
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.item_shopping_card_check);
            mChildProduct = mProduct.products.get(position);
            switch (view.getId()) {
                case R.id.item_shopping_card_check:
                    if (!checkBox.isChecked()) {
                        mChildProduct.childCheckFlag = false;
                        if (mProduct.checkFlag) {
                            partnerCheckBox.setChecked(false);
                            mProduct.checkFlag = false;
                            if (mFragmentShoppingCardCheck.isChecked()) {
                                mFragmentShoppingCardCheck.setChecked(false);
                            }
                        }
                    } else {
                        mChildProduct.childCheckFlag = true;
                    }
                    countPrice2(partnerCheckBox, mProduct);
                    itemAdapter.setData(position, mChildProduct);
                    break;
                case R.id.item_shopping_card_reduce:
                    Integer count = mChildProduct.productNumber;
                    if (count - 1 == 0) {
                        showToast("宝贝不能再减少了哦");
                    } else {
                        mChildProduct.productNumber = count - 1;
                        requestProductNumber(mProduct, mChildProduct);
                    }
                    break;
                case R.id.item_shopping_card_add:
                    Integer count2 = mChildProduct.productNumber;
                    mChildProduct.productNumber = count2 + 1;
                    requestProductNumber(mProduct, mChildProduct);
                    break;
                case R.id.item_shopping_card_delete:
                    requestDeleteProduct(mProduct, mChildProduct);
                    break;
            }

        });
    }

    private void requestDeleteProduct(ShoppingCardListResponse.DataBean product, ShoppingCardListResponse.DataBean.ProductsBean childProduct) {
        mPresenter.requestDeleteProduct(String.valueOf(product.scid), childProduct.pid, String.valueOf(childProduct.productNumber));
    }


    private void requestProductNumber(ShoppingCardListResponse.DataBean product, ShoppingCardListResponse.DataBean.ProductsBean childProduct) {
        mPresenter.requestChangeProductNumber(String.valueOf(product.scid), childProduct.pid, String.valueOf(childProduct.productNumber));
    }

    @Override
    public void deleteProduct(ShoppingCardListResponse.DataBean product, ShoppingCardListResponse.DataBean.ProductsBean childProduct, Integer position) {
        mChildPosition = position;
        requestDeleteProduct(product, childProduct);
    }


    @Override
    public void changeProductNumberSuccess() {
        mShoppingCardItemAdapter.setData(mChildPosition, mChildProduct);
        countPrice();
    }

    @Override
    public void deleteProductSuccess() {
        showToast("刪除购物车成功");
        mShoppingCardItemAdapter.remove(mChildPosition);
        if (mShoppingCardItemAdapter.getData().size() == 0) {
            mAdapter.remove(mPartnerPosition);
        }
        if (mAdapter.getData().size() == 0) {
            mFragmentShoppingCardBottomView.setVisibility(View.GONE);
            mAdapter.setEmptyView(mEmptyView);
        }
        countPrice();
    }

    @Override
    void addFilter() {
        mFilter.addAction(BroConstant.UPDATE_SHOPPING_CARD_INFO);
    }

    @Override
    void onReceivePro(Context context, Intent intent) {
        if (intent.getAction().equals(BroConstant.UPDATE_SHOPPING_CARD_INFO)) {
            initData();
        }
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
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }


    private void initData() {
        mPresenter.requestShoppingCardList(BuildConfig.PARTNER_ID, mBuProcessor.getUserId());
    }

    private void initView() {
        setAllPriceText(0);
        mLocationInfo = ((DaggerApplication) getActivity().getApplicationContext()).getaMapLocation();
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, (ViewGroup) mActivitiesRecycleView.getParent(), false);
        Button mEmptyButton = (Button) mEmptyView.findViewById(R.id.empty_go_shopping);
        RxView.clicks(mEmptyButton).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> goForShopping());
        RxView.clicks(mFragmentShoppingCardBalance).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> goForPayShoppingCard());
        RxView.clicks(mFragmentShoppingCardCheck).subscribe(o -> checkForAll());
        mActivitiesRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ShoppingCardAdapter(null, this, getActivity(), mImageLoaderHelper);
        mActivitiesRecycleView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.adapter_shopping_card_check);
            ShoppingCardListResponse.DataBean product = mProductList.get(position);
            switch (view.getId()) {
                case R.id.adapter_shopping_card_check:
                    if (!checkBox.isChecked()) {
                        product.checkFlag = false;
                        for (ShoppingCardListResponse.DataBean.ProductsBean productsBean : product.products) {
                            productsBean.childCheckFlag = false;
                        }
                        if (mFragmentShoppingCardCheck.isChecked()) {
                            mFragmentShoppingCardCheck.setChecked(false);
                        }
                    } else {
                        product.checkFlag = true;
                        for (ShoppingCardListResponse.DataBean.ProductsBean productsBean : product.products) {
                            productsBean.childCheckFlag = true;
                        }
                    }
                    countPrice();

                    mAdapter.setData(position, product);
                    break;
                case R.id.adapter_shopping_card_edit:
                    TextView editTextView = (TextView) view.findViewById(R.id.adapter_shopping_card_edit);
                    if (editTextView.getText().toString().trim().equals("编辑")) {
                        product.childEditFlag = true;
                        for (ShoppingCardListResponse.DataBean.ProductsBean productsBean : product.products) {
                            productsBean.childEditFlag = true;

                        }
                    } else {
                        product.childEditFlag = false;
                        for (ShoppingCardListResponse.DataBean.ProductsBean productsBean : product.products) {
                            productsBean.childEditFlag = false;
                        }
                    }

                    mAdapter.setData(position, product);
                    break;

            }
        });
    }

    private void setAllPriceText(Integer price) {
        String orderPricePartOne = "合计：";
        String orderPricePartTwo = "￥" + ValueUtil.formatAmount(price);
        SpannableStringBuilder stringBuilder = SpannableStringUtils.getBuilder(orderPricePartTwo)
                .setForegroundColor(ContextCompat.getColor(getActivity(), R.color.order_price_color))
                .setSize(18, true)
                .create();
        SpannableStringBuilder stringBuilder2 = SpannableStringUtils.getBuilder(orderPricePartOne)
                .setForegroundColor(ContextCompat.getColor(getActivity(), R.color.light_grey_dark))
                .append(stringBuilder).create();
        mFragmentShoppingCardPrice.setText(stringBuilder2);
    }

    private void goForPayShoppingCard() {
        PayCreateRequest request = new PayCreateRequest();
        List<OrderConfirmedRequest> payCreate = new ArrayList<>();

        for (ShoppingCardListResponse.DataBean dataBean : mProductList) {
            OrderConfirmedRequest orderCreateRequest = new OrderConfirmedRequest();

            List<OrderConfirmedRequest.ProductsBean> list = new ArrayList<>();
            for (ShoppingCardListResponse.DataBean.ProductsBean product : dataBean.products) {
                if (product.childCheckFlag) {
                    OrderConfirmedRequest.ProductsBean productsBean = new OrderConfirmedRequest.ProductsBean();
                    productsBean.productName = product.name;
                    productsBean.sequence = product.sequence + "";
                    productsBean.number = String.valueOf(product.productNumber);
                    productsBean.specification = product.specification;
                    productsBean.productId = product.pid;
                    productsBean.price = product.finalPrice;
                    productsBean.picture = product.picture;
                    list.add(productsBean);
                }
            }

            if (list.size() > 0) {
                orderCreateRequest.products = list;

                List<OrderConfirmedRequest.AccountsBean> accountList = new ArrayList<>();
                OrderConfirmedRequest.AccountsBean accountsBean = new OrderConfirmedRequest.AccountsBean();
                accountsBean.sequence = 0;
                accountsBean.accountId = "123456";
                accountsBean.number = "1";
                accountsBean.name = "运费";
                accountsBean.type = "1";
                accountsBean.price = "500";
                accountList.add(accountsBean);
                orderCreateRequest.accounts = accountList;

                orderCreateRequest.shopName = dataBean.linkName;
                orderCreateRequest.source = "android";
                orderCreateRequest.customerOrder = "BSY_" + System.currentTimeMillis();
                Integer mAmount = 0;
                for (OrderConfirmedRequest.ProductsBean productsBean : list) {
                    mAmount += productsBean.price * Integer.valueOf(productsBean.number);
                }
                orderCreateRequest.amount = mAmount + 500;
                orderCreateRequest.type = 1;
                orderCreateRequest.payType = 1;
                orderCreateRequest.userId = mBuProcessor.getUserId();
                orderCreateRequest.payChannel = "";
                if (mLocationInfo != null) {
                    orderCreateRequest.longitude = String.valueOf(mLocationInfo.getLongitude());
                    orderCreateRequest.latitude = String.valueOf(mLocationInfo.getLatitude());
                }
                orderCreateRequest.status = 1;
                orderCreateRequest.shopId = dataBean.linkId;
                orderCreateRequest.partition = "";
                orderCreateRequest.remark = "";
                orderCreateRequest.payChannelName = "";
                orderCreateRequest.companyId = BuildConfig.PARTNER_ID;
                payCreate.add(orderCreateRequest);
            }

        }

        if (payCreate.size() > 0) {
            request.orders = payCreate;
            startActivity(PayActivity.getIntent(getActivity(), request));
        } else {
            showToast("您还没有选择宝贝哦");
        }

    }

    private void checkForAll() {
        if (!mFragmentShoppingCardCheck.isChecked()) {
            for (ShoppingCardListResponse.DataBean dataBean : mProductList) {
                dataBean.checkFlag = false;
                for (ShoppingCardListResponse.DataBean.ProductsBean product : dataBean.products) {
                    product.childCheckFlag = false;
                }
            }
        } else {
            for (ShoppingCardListResponse.DataBean dataBean : mProductList) {
                dataBean.checkFlag = true;
                for (ShoppingCardListResponse.DataBean.ProductsBean product : dataBean.products) {
                    product.childCheckFlag = true;
                }
            }
        }
        countPrice();
        mAdapter.setNewData(mProductList);
    }

    private void countPrice2(CheckBox partnerCheckBox, ShoppingCardListResponse.DataBean mProduct) {
        countPrice();
        boolean isAllCheck = true;
        for (ShoppingCardListResponse.DataBean.ProductsBean product : mProduct.products) {
            if (!product.childCheckFlag) {
                isAllCheck = false;
            }
        }
        partnerCheckBox.setChecked(isAllCheck);
    }

    private void countPrice() {
        Integer allPrice = 0;
        List<ShoppingCardListResponse.DataBean> list = mAdapter.getData();
        if (list.size() > 0) {
            for (ShoppingCardListResponse.DataBean dataBean : list) {
                for (ShoppingCardListResponse.DataBean.ProductsBean product : dataBean.products) {
                    if (product.childCheckFlag) {
                        allPrice += product.finalPrice * product.productNumber;
                    }

                }
            }
        }
        setAllPriceText(allPrice);
    }

    private void goForShopping() {
        showToast("去购物");
    }

    private void initialize() {
        DaggerFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .fragmentModule(new FragmentModule(this, (MainActivity) getActivity())).build()
                .inject(this);
    }
}
