package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aries.ui.view.radius.RadiusTextView;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerPersonCenterActivityComponent;
import com.banshengyuan.feima.dagger.module.PersonCenterActivityModule;
import com.banshengyuan.feima.entity.IntentConstant;
import com.banshengyuan.feima.entity.PersonInfoResponse;
import com.banshengyuan.feima.help.DialogFactory;
import com.banshengyuan.feima.help.photohelp.CompressConfig;
import com.banshengyuan.feima.help.photohelp.CropOptions;
import com.banshengyuan.feima.help.photohelp.InvokeListener;
import com.banshengyuan.feima.help.photohelp.InvokeParam;
import com.banshengyuan.feima.help.photohelp.PermissionManager;
import com.banshengyuan.feima.help.photohelp.TContextWrap;
import com.banshengyuan.feima.help.photohelp.TResult;
import com.banshengyuan.feima.help.photohelp.TakePhoto;
import com.banshengyuan.feima.help.photohelp.TakePhotoImpl;
import com.banshengyuan.feima.help.photohelp.TakePhotoInvocationHandler;
import com.banshengyuan.feima.help.photohelp.TakePhotoOptions;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.PersonCenterControl;
import com.banshengyuan.feima.view.customview.timepickview.DatePicker;
import com.banshengyuan.feima.view.customview.timepickview.util.ConvertUtils;
import com.banshengyuan.feima.view.fragment.PhotoChoiceDialog;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/27.
 * PersonCenterActivity
 */

public class PersonCenterActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener, PersonCenterControl.PersonCenterView, PhotoChoiceDialog.photoChoiceDialogListener {

    public static Intent getPersonIntent(Context context, PersonInfoResponse response) {
        Intent intent = new Intent(context, PersonCenterActivity.class);
        intent.putExtra("PersonInfoResponse", response);
        return intent;
    }

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.person_center_head)
    LinearLayout mPersonCenterHead;
    @BindView(R.id.person_center_name)
    LinearLayout mPersonCenterName;
    @BindView(R.id.person_center_sex)
    LinearLayout mPersonCenterSex;
    @BindView(R.id.person_center_birthday)
    LinearLayout mPersonCenterBirthday;
    @BindView(R.id.update_person_info)
    RadiusTextView mUpdatePersonInfo;
    @BindView(R.id.person_name)
    TextView mPersonName;
    @BindView(R.id.person_sex)
    TextView mPersonSex;
    @BindView(R.id.person_birthday_date)
    TextView mPersonBirthdayDate;
    @BindView(R.id.person_icon)
    ImageView mPersonIcon;
    @BindView(R.id.person_center_signature)
    EditText personCenterSignature;
    @BindView(R.id.person_page_bg)
    LinearLayout personPageBg;
    @Inject
    PersonCenterControl.PresenterPersonCenter mPresenter;
    private Uri imageUri;

    private PersonInfoResponse mPersonInfoResponse;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private String token;
    private boolean avatalFlag = false;//记录是否更新头像

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText(R.string.user_person_center);
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

    private void initData() {
        token = mBuProcessor.getUserToken();
    }

    private void initView() {
        mPersonInfoResponse = getIntent().getParcelableExtra("PersonInfoResponse");
        //初始化头像存储文件
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        imageUri = Uri.fromFile(file);
        if (mPersonInfoResponse != null) {
            PersonInfoResponse.InfoBean infoBean = mPersonInfoResponse.getInfo();

            if (infoBean.getSex() == 0) {
                mPersonSex.setText(getString(R.string.app_choice));
            } else {
                mPersonSex.setText(infoBean.getSex() == 1 ? "男" : "女");
            }

            if (TextUtils.isEmpty(infoBean.getBirthday())) {
                mPersonBirthdayDate.setText(getString(R.string.app_choice));
            } else {
                mPersonBirthdayDate.setText(infoBean.getBirthday());
            }
            if (TextUtils.isEmpty(infoBean.getName())) {
                mPersonName.setText(getString(R.string.app_setting_less));
            } else {
                mPersonName.setText(infoBean.getName());
                mPersonName.setTextColor(Color.parseColor("#333333"));
            }

            if (TextUtils.isEmpty(infoBean.getHead_img())) {
                mImageLoaderHelper.displayCircularImage(this, R.mipmap.person_fake_icon, mPersonIcon);
            } else {
                mImageLoaderHelper.displayCircularImage(this, infoBean.getHead_img(), mPersonIcon);
            }

            if (!TextUtils.isEmpty(infoBean.getSalt())) {
                personCenterSignature.setText(infoBean.getSalt());
            }

        }
        RxView.clicks(mPersonCenterHead).subscribe(v -> requestChoicePic());
        RxView.clicks(mPersonCenterName).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestPersonName());
        RxView.clicks(mPersonCenterSex).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestPersonSex());
        RxView.clicks(mPersonCenterBirthday).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestPersonBirthday());
        RxView.clicks(mUpdatePersonInfo).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestUpdatePersonInfo());
        RxView.clicks(personPageBg).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> changeBg());//更换个人主页背景

    }

    private void changeBg() {
        showToast("更换个人主页背景");
    }

    private void requestChoicePic() {
        PhotoChoiceDialog mDialog = PhotoChoiceDialog.newInstance();
        mDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), mDialog, PhotoChoiceDialog.TAG);
    }

    @Override
    public void photoTakeListener() {
        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
    }

    @Override
    public void PhotoDirectListener() {
        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
    }

    @Override
    public void takeSuccess(TResult result) {
        Bitmap bitmap = BitmapFactory.decodeFile(result.getImage().getCompressPath());
        String img_url = ValueUtil.convertIconToString(bitmap);
        mPersonInfoResponse.getInfo().setHead_img(img_url);
        avatalFlag = true;
        mImageLoaderHelper.displayCircularImage(this, result.getImage().getCompressPath(), mPersonIcon);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        mImageLoaderHelper.displayCircularImage(this, R.mipmap.person_fake_icon, mPersonIcon);
    }

    @Override
    public void takeCancel() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    private void requestPersonName() {
        String name = ValueUtil.replaceBlank(mPersonName.getText().toString());
        if (("未设置").equals(name)) {
            name = "";
        }
        startActivityForResult(PersonNameActivity.getIntent(this, name), IntentConstant.ORDER_POSITION_TWO);
    }


    private void requestPersonSex() {
        String sex = mPersonSex.getText().toString();
        startActivityForResult(SexChoiceActivity.getIntent(this, sex.trim()), IntentConstant.ORDER_POSITION_ONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        if (requestCode == IntentConstant.ORDER_POSITION_ONE && resultCode == RESULT_OK) {
            String sex = data.getStringExtra("sex");
            if ("男".equals(sex) && mPersonInfoResponse != null) {
                mPersonInfoResponse.getInfo().setSex(1);
            } else if (mPersonInfoResponse != null) {
                mPersonInfoResponse.getInfo().setSex(2);
            }

            mPersonSex.setText(sex);

        } else if (requestCode == IntentConstant.ORDER_POSITION_TWO && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            mPersonInfoResponse.getInfo().setName(name);
            mPersonName.setTextColor(Color.parseColor("#333333"));
            mPersonName.setText(name );
        }

    }

    @Override
    public void updatePersonInfoSuccess() {
        setResult(RESULT_OK);
        finish();
//        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BroConstant.UPDATE_PERSON_INFO));
    }

    private void requestUpdatePersonInfo() {
        String signature = personCenterSignature.getText().toString();
        mPersonInfoResponse.getInfo().setSalt(signature);
        mPresenter.requestUpdatePersonInfo(mPersonInfoResponse, token, avatalFlag);
    }

    private void requestPersonBirthday() {
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        picker.setRangeEnd(2117, 1, 1);
        picker.setRangeStart(1917, 1, 1);
        picker.setSelectedItem(2017, 1, 1);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener((DatePicker.OnYearMonthDayPickListener) (year, month, day)
                -> {
            String date = year + "-" + month + "-" + day;
            mPersonInfoResponse.getInfo().setBirthday(date);
            mPersonBirthdayDate.setText(date);
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }


    private void configCompress(TakePhoto takePhoto) {
        int maxSize = 1024;
        int width = 160;
        int height = 160;
        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(width)
                .enableReserveRaw(false).create();
        takePhoto.onEnableCompress(config, false);
    }

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());

    }

    private CropOptions getCropOptions() {
        int height = 80;
        int width = 80;
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);
        builder.setWithOwnCrop(true);
        return builder.create();
    }

    private void initializeInjector() {
        DaggerPersonCenterActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .personCenterActivityModule(new PersonCenterActivityModule(PersonCenterActivity.this, this))
                .build().inject(this);
    }
}
