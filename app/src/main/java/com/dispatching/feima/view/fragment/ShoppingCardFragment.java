package com.dispatching.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerFragmentComponent;
import com.dispatching.feima.dagger.module.FragmentModule;
import com.dispatching.feima.dagger.module.MainActivityModule;
import com.dispatching.feima.dagger.module.ShoppingCardListResponse;
import com.dispatching.feima.utils.ToastUtils;
import com.dispatching.feima.view.PresenterControl.ShoppingCardControl;
import com.dispatching.feima.view.activity.MainActivity;
import com.dispatching.feima.view.adapter.ShoppingCardAdapter;
import com.dispatching.feima.view.adapter.ShoppingCardItemAdapter;
import com.jakewharton.rxbinding2.view.RxView;

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

    private void initData() {
        mPresenter.requestShoppingCardList(companyId, mBuProcessor.getUserId());
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

    private void initView() {
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, (ViewGroup) mActivitiesRecycleView.getParent(), false);
        mEmptyButton = (Button) mEmptyView.findViewById(R.id.empty_go_shopping);
        RxView.clicks(mEmptyButton).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> goForShopping());
        mActivitiesRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ShoppingCardAdapter(null,this, getActivity(), mImageLoaderHelper);
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
                    } else {
                        product.checkFlag = true;
                        for (ShoppingCardListResponse.DataBean.ProductsBean productsBean : product.products) {
                            productsBean.childCheckFlag = true;
                        }
                    }

                    mAdapter.setNewData(mProductList);
                    break;
                case R.id.adapter_shopping_card_edit:
                    ToastUtils.showShortToast("编辑" + position);
                    break;

            }
        });
    }

    @Override
    public void setChildAdapter(Integer position1, ShoppingCardItemAdapter itemAdapter) {
        itemAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.item_shopping_card_check:
                    ToastUtils.showShortToast("check"+position);
                    break;
                case R.id.item_shopping_card_reduce:
                    ToastUtils.showShortToast("减少"+position);
                    break;
                case R.id.item_shopping_card_add:
                    ToastUtils.showShortToast("增加"+position);
                    break;
            }
        });
    }

    private void goForShopping() {
        showToast("去购物");
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

    private void initialize() {
        DaggerFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .fragmentModule(new FragmentModule(this, (MainActivity) getActivity())).build()
                .inject(this);
    }
}
