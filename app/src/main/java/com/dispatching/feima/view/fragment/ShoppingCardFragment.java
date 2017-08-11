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

import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerFragmentComponent;
import com.dispatching.feima.dagger.module.FragmentModule;
import com.dispatching.feima.dagger.module.MainActivityModule;
import com.dispatching.feima.dagger.module.ShoppingCardListResponse;
import com.dispatching.feima.entity.BroConstant;
import com.dispatching.feima.entity.SpecificationResponse;
import com.dispatching.feima.utils.SpannableStringUtils;
import com.dispatching.feima.utils.ToastUtils;
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
    private Button mEmptyButton;
    private final String companyId = "53c69e54-c788-495c-bed3-2dbfc6fd5c61";
    private List<ShoppingCardListResponse.DataBean> mProductList;
    private Integer allPrice;
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
            mAdapter.setNewData(mProductList);
        } else {
            mFragmentShoppingCardBottomView.setVisibility(View.GONE);
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public void setChildAdapter(Integer parentPosition, ShoppingCardItemAdapter itemAdapter, CheckBox partnerCheckBox) {
        ShoppingCardListResponse.DataBean mProduct = mProductList.get(parentPosition);
        itemAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.item_shopping_card_check);
            switch (view.getId()) {
                case R.id.item_shopping_card_check:
                    ShoppingCardListResponse.DataBean.ProductsBean childProduct = mProduct.products.get(position);
                    if (!checkBox.isChecked()) {
                        childProduct.childCheckFlag = false;
                        if (mProduct.checkFlag) {
                            partnerCheckBox.setChecked(false);
                            mProduct.checkFlag = false;
                            if (mFragmentShoppingCardCheck.isChecked()) {
                                mFragmentShoppingCardCheck.setChecked(false);
                            }
                        }
                    } else {
                        childProduct.childCheckFlag = true;
                    }
                    countPrice2(partnerCheckBox, mProduct);
                    itemAdapter.setData(position, childProduct);
                    break;
                case R.id.item_shopping_card_reduce:
                    ToastUtils.showShortToast("减少" + position);
                    break;
                case R.id.item_shopping_card_add:
                    ToastUtils.showShortToast("增加" + position);
                    break;
            }
        });
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
        mPresenter.requestShoppingCardList(companyId, mBuProcessor.getUserId());
    }

    private void initView() {
        setAllPriceText(0);
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, (ViewGroup) mActivitiesRecycleView.getParent(), false);
        mEmptyButton = (Button) mEmptyView.findViewById(R.id.empty_go_shopping);
        RxView.clicks(mEmptyButton).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> goForShopping());
        RxView.clicks(mFragmentShoppingCardBalance).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> goForPayShoppingCard());
        RxView.clicks(mFragmentShoppingCardCheck).subscribe(o -> checkForAll());
        mActivitiesRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ShoppingCardAdapter(null, this, getActivity(), mImageLoaderHelper);
        mActivitiesRecycleView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.adapter_shopping_card_check);
            switch (view.getId()) {
                case R.id.adapter_shopping_card_check:
                    ShoppingCardListResponse.DataBean product = mProductList.get(position);
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
                    ToastUtils.showShortToast("编辑" + position);
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
        SpecificationResponse response = new SpecificationResponse();
        List<SpecificationResponse.ProductsBean> list = new ArrayList<>();
        for (ShoppingCardListResponse.DataBean dataBean : mProductList) {
            for (ShoppingCardListResponse.DataBean.ProductsBean product : dataBean.products) {
                if (product.childCheckFlag) {
                    SpecificationResponse.ProductsBean productsBean = new SpecificationResponse.ProductsBean();
                    productsBean.barcode = product.barcode;
                    productsBean.category = product.category;
                    productsBean.categoryName = product.categoryName;
                    productsBean.companyId = product.companyId;
                    productsBean.customerCode = product.customerCode;
                    productsBean.finalPrice = product.finalPrice;
                    productsBean.name = product.name;
                    productsBean.originalPrice = product.originalPrice;
                    productsBean.picture = product.picture;
                    productsBean.pid = product.pid;
                    productsBean.remark = product.remark;
                    productsBean.saleCount = product.productNumber;
                    productsBean.status = Integer.valueOf(product.status);
                    productsBean.sellTimeName = product.sellTimeName;
                    productsBean.specification = product.specification;
                    productsBean.type = product.type;
                    productsBean.sequence = product.sequence;
                    productsBean.unit = product.unit;
                    productsBean.labelNames = product.labelNames;
                    list.add(productsBean);
                }
            }
        }
        if(list.size()>0){
            response.products = list;
            startActivity(PayActivity.getIntent(getActivity(), response));
        }else {
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
        allPrice = 0;
        for (ShoppingCardListResponse.DataBean dataBean : mProductList) {
            for (ShoppingCardListResponse.DataBean.ProductsBean product : dataBean.products) {
                if (product.childCheckFlag) {
                    allPrice += product.finalPrice * product.productNumber;
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
