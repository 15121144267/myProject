package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aries.ui.view.radius.RadiusTextView;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.HotFairDetailResponse;
import com.banshengyuan.feima.help.AniCreator;
import com.banshengyuan.feima.help.DialogFactory;
import com.banshengyuan.feima.utils.TimeUtil;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.FairProductDetailControl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class JoinActionDialog extends BaseDialogFragment {
    public static final String TAG = JoinActionDialog.class.getSimpleName();
    @BindView(R.id.horn)
    ImageView horn;
    @BindView(R.id.join_action_title)
    TextView joinActionTitle;
    @BindView(R.id.join_action_date)
    TextView joinActionDate;
    @BindView(R.id.join_action_place)
    TextView joinActionPlace;
    @BindView(R.id.join_action_money)
    TextView joinActionMoney;
    @BindView(R.id.join_action_phone)
    TextView joinActionPhone;
    @BindView(R.id.cancal_btn)
    RadiusTextView cancalBtn;
    @BindView(R.id.sure_btn)
    RadiusTextView sureBtn;
    @BindView(R.id.join_action_lv)
    LinearLayout joinActionLv;
    Unbinder unbinder;
    private PayMethodClickListener mListener;

    private FairProductDetailControl.PresenterFairProductDetail mPresenter;
    private String id;
    private String mToken;
    private HotFairDetailResponse mHotFairDetailResponse = null;//热闹详情
    private String mPhone;

    public static JoinActionDialog newInstance() {
        return new JoinActionDialog();
    }

    public void setListener(PayMethodClickListener listener) {
        this.mListener = listener;
    }

    public void setData(FairProductDetailControl.PresenterFairProductDetail presenter, String fId, String token, HotFairDetailResponse hotFairDetailResponse, String phone) {
        mPresenter = presenter;
        id = fId;
        mToken = token;
        mHotFairDetailResponse = hotFairDetailResponse;
        mPhone = phone;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_join_action, container, true);
        unbinder = ButterKnife.bind(this, view);
        AniCreator.getInstance().apply_animation_translate(joinActionLv, AniCreator.ANIMATION_MODE_POPUP, View.VISIBLE, false, null);
        initView();
        return view;
    }

    private void initView() {
        if (mHotFairDetailResponse != null) {
            HotFairDetailResponse.InfoBean infoBean = mHotFairDetailResponse.getInfo();
            joinActionTitle.setText(infoBean.getName());
            String date = "时间: " + TimeUtil.transferLongToDate(TimeUtil.TIME_YYMMDD, (long) infoBean.getStart_time());
            String place = TextUtils.isEmpty(infoBean.getStreet_name()) ? "未知街区  " : infoBean.getStreet_name();
            mPhone = TextUtils.isEmpty(mPhone) ? "未知  " : mPhone;
            joinActionPhone.setText(mPhone);
            if (infoBean.getSales_price() > 0) {
                String money = "￥" + ValueUtil.formatAmount2(infoBean.getSales_price());
                joinActionMoney.setText(money);
            }
            joinActionDate.setText(date);
            joinActionPlace.setText("地点: " + place);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.cancal_btn, R.id.sure_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancal_btn:
                closeThisDialog();
                break;
            case R.id.sure_btn:
                String phone = joinActionPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getActivity(), "手机号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ValueUtil.isMobilePhone(phone)) {
                    Toast.makeText(getActivity(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                mPresenter.requestHotFairJoinAction(id, phone, mToken);
                closeThisDialog();
                break;
        }
    }

    public interface PayMethodClickListener {
        void clickRechargeBtn(String payType);
    }


    private void closeThisDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
