package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.HotFariJoinActionRequest;
import com.banshengyuan.feima.help.AniCreator;
import com.banshengyuan.feima.help.DialogFactory;
import com.banshengyuan.feima.view.PresenterControl.FairProductDetailControl;

import javax.inject.Inject;

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
    EditText joinActionPhone;
    @BindView(R.id.cancal_btn)
    TextView cancalBtn;
    @BindView(R.id.sure_btn)
    Button sureBtn;
    @BindView(R.id.join_action_lv)
    LinearLayout joinActionLv;
    Unbinder unbinder;
    private PayMethodClickListener mListener;

    private FairProductDetailControl.PresenterFairProductDetail mPresenter;
    private String id;
    private HotFariJoinActionRequest mHotFariJoinActionRequest;

    public static JoinActionDialog newInstance() {
        return new JoinActionDialog();
    }

    /*public void setPayFlag(String payFlag) {
        this.payFlag = payFlag;
    }*/

    public void setListener(PayMethodClickListener listener) {
        this.mListener = listener;
    }

    public void setData(FairProductDetailControl.PresenterFairProductDetail presenter, String fId, HotFariJoinActionRequest hotFariJoinActionRequest) {
        mPresenter = presenter;
        id = fId;
        mHotFariJoinActionRequest = hotFariJoinActionRequest;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_join_action, container, true);
        unbinder = ButterKnife.bind(this, view);
        AniCreator.getInstance().apply_animation_translate(joinActionLv, AniCreator.ANIMATION_MODE_POPUP, View.VISIBLE, false, null);
        return view;
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
                if (TextUtils.isEmpty(joinActionPhone.getText().toString())) {
                    Toast.makeText(getActivity(), "手机号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                mHotFariJoinActionRequest.setPhone(joinActionPhone.getText().toString());
                mPresenter.requestHotFairJoinAction(id, mHotFariJoinActionRequest);
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
