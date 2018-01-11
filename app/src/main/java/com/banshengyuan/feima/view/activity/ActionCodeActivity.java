package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.HotFairDetailResponse;
import com.banshengyuan.feima.utils.TimeUtil;
import com.banshengyuan.feima.utils.ToolUtils;
import com.google.zxing.WriterException;

import java.sql.Time;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActionCodeActivity extends BaseActivity {

    @BindView(R.id.action_code_title)
    TextView actionCodeTitle;
    @BindView(R.id.action_code_number)
    TextView actionCodeNumber;
    @BindView(R.id.action_code_date)
    TextView actionCodeDate;
    @BindView(R.id.action_code_place)
    TextView actionCodePlace;
    @BindView(R.id.action_code_qrcode)
    ImageView actionCodeQrcode;
    @BindView(R.id.action_code_phone)
    TextView actionCodePhone;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private HotFairDetailResponse mHotFairDetailResponse = null;//热闹详情
    private String qrCode = null;
    private HotFairDetailResponse.InfoBean infoBean = null;

    public static Intent getIntent(Context context, HotFairDetailResponse hotFairDetailResponse, String qrCode) {
        Intent intent = new Intent(context, ActionCodeActivity.class);
        intent.putExtra("hotFairDetailResponse", hotFairDetailResponse);
        intent.putExtra("qrCode", qrCode);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_code);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mMiddleName.setText("活动凭证");
        initData();

    }

    private void initData() {
        if (getIntent() != null) {
            mHotFairDetailResponse = getIntent().getParcelableExtra("hotFairDetailResponse");
            qrCode = getIntent().getStringExtra("qrCode");

            infoBean = mHotFairDetailResponse.getInfo();
            if (infoBean != null) {
                String codeNum;
                if (!TextUtils.isEmpty(infoBean.getOrder_sn())) {
                    codeNum = infoBean.getOrder_sn();
                    setQRCodeBitmap(infoBean.getOrder_sn());
                } else {
                    codeNum = "未知";
                }
                actionCodeNumber.setText("券码：" + codeNum);

                if (infoBean.getStart_time() > 0) {
                    actionCodeDate.setText(TimeUtil.transferLongToDate(TimeUtil.TIME_YYMMDD, (long) infoBean.getStart_time()));
                }
                if (!TextUtils.isEmpty(infoBean.getStreet_name())) {
                    actionCodePlace.setText(infoBean.getStreet_name());
                }

                if (!TextUtils.isEmpty(infoBean.getName())) {
                    actionCodeTitle.setText(infoBean.getName());
                }

                //手机
                String phone = TextUtils.isEmpty(mBuProcessor.getUserPhone()) ? "未知：" : mBuProcessor.getUserPhone();
                actionCodePhone.setText(phone);
            }

        }
    }

    private void setQRCodeBitmap(String code) {
        Bitmap bmp = null;
        try {
            bmp = ToolUtils.generateBarcode(code);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        if (bmp != null) {
            actionCodeQrcode.setImageBitmap(bmp);
        }

        Bitmap qrBitmap = ToolUtils.generateBitmap(code, 600, 600);
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.freemud_logo);
        Bitmap bitmap = ToolUtils.addLogo(qrBitmap, logoBitmap);
        actionCodeQrcode.setImageBitmap(bitmap);
    }

}
