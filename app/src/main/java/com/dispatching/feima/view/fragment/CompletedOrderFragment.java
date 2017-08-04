package com.dispatching.feima.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerFragmentComponent;
import com.dispatching.feima.dagger.module.FragmentModule;
import com.dispatching.feima.dagger.module.MainActivityModule;
import com.dispatching.feima.entity.BroConstant;
import com.dispatching.feima.entity.PersonInfoResponse;
import com.dispatching.feima.entity.SpConstant;
import com.dispatching.feima.view.PresenterControl.CompletedOrderControl;
import com.dispatching.feima.view.activity.AddressActivity;
import com.dispatching.feima.view.activity.MainActivity;
import com.dispatching.feima.view.activity.MyOrderActivity;
import com.dispatching.feima.view.activity.PersonCenterActivity;
import com.dispatching.feima.view.customview.MoveScrollView;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by helei on 2017/5/3.
 * CompletedOrderFragment
 */

public class CompletedOrderFragment extends BaseFragment implements CompletedOrderControl.CompletedOrderView {

    @BindView(R.id.person_order)
    TextView mPersonOrder;
    @BindView(R.id.person_address)
    TextView mPersonAddress;
    @BindView(R.id.person_info)
    TextView mPersonInfo;
    @BindView(R.id.person_icon)
    ImageView mPersonIcon;
    @BindView(R.id.person_name)
    TextView mPersonName;
    @BindView(R.id.person_detail)
    TextView mPersonDetail;
    @BindView(R.id.person_tips)
    ImageButton mPersonTips;
    @BindView(R.id.move_image)
    ImageView mMoveImage;
    @BindView(R.id.scroll_layout)
    MoveScrollView mScrollLayout;

    public static CompletedOrderFragment newInstance() {
        return new CompletedOrderFragment();
    }


    @Inject
    CompletedOrderControl.PresenterCompletedOrder mPresenter;

    private Unbinder unbind;
    private PersonInfoResponse mResponse;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complete_order, container, false);
        unbind = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initView() {
        mResponse = mBuProcessor.getPersonInfo();
        update(mResponse);
        mScrollLayout.setMoveImageView(mMoveImage);
        RxView.clicks(mPersonOrder).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestOrder());
        RxView.clicks(mPersonAddress).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestAddress());
        RxView.clicks(mPersonInfo).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestInfo());

    }

    private void initData() {

    }

    private void requestInfo() {
        startActivity(PersonCenterActivity.getPersonIntent(getActivity(), mResponse));
    }

    private void requestAddress() {
        startActivity(AddressActivity.getIntent(getActivity(),"completedOrderFragment"));
    }

    private void requestOrder() {
        startActivity(MyOrderActivity.getIntent(getActivity()));
    }

    @Override
    public void getPersonInfoSuccess(PersonInfoResponse response) {
        mResponse = response;
        update(response);
    }

    @Override
    void addFilter() {
        super.addFilter();
        mFilter.addAction(BroConstant.UPDATE_PERSON_INFO);
    }

    @Override
    void onReceivePro(Context context, Intent intent) {
        if (intent.getAction().equals(BroConstant.UPDATE_PERSON_INFO)) {
            mPresenter.requestPersonInfo(mSharePreferenceUtil.getStringValue(SpConstant.USER_NAME));
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

    private void update(PersonInfoResponse response) {
        if (response == null) return;
        mPersonName.setText(response.nickName == null ? "未知  " : response.nickName + "  ");
        mImageLoaderHelper.displayCircularImage(getActivity(), response.avatarUrl == null ? R.mipmap.person_head_icon : response.avatarUrl, mPersonIcon);
        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.mipmap.person_sex_man);
        Drawable drawable2 = ContextCompat.getDrawable(getActivity(), R.mipmap.person_sex_women);
        drawable.setBounds(0, 0, drawable.getMinimumHeight(), drawable.getMinimumHeight());
        drawable2.setBounds(0, 0, drawable2.getMinimumHeight(), drawable2.getMinimumHeight());
        if (response.sex == 2) {
            mPersonName.setCompoundDrawables(null, null, drawable, null);
        } else {
            mPersonName.setCompoundDrawables(null, null, drawable2, null);
        }

    }

    private void initialize() {
        DaggerFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .fragmentModule(new FragmentModule(this,(MainActivity)getActivity())).build()
                .inject(this);
    }
}
