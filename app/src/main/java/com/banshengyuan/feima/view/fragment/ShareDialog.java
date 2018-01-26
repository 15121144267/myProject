package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ShareInfo;
import com.banshengyuan.feima.entity.ShareStatus;
import com.banshengyuan.feima.help.AniCreator;
import com.banshengyuan.feima.help.ShareHelp.ShareHelper;
import com.banshengyuan.feima.view.adapter.ShareAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lei.he on 2018/1/26.
 * ShareDialog
 */

public class ShareDialog extends BaseDialogFragment {

    public static final String TAG = ShareDialog.class.getSimpleName();

    public static ShareDialog getInstance() {
        return new ShareDialog();
    }

    @BindView(R.id.share_dialog_layout)
    RelativeLayout mShareDialogLayout;
    @BindView(R.id.dialog_share_list)
    RecyclerView mDialogShareList;
    private ShareInfo mShareInfo;
    private Unbinder unbind;
    private ShareAdapter mAdapter;
    private String[] mStrings = {"微信分享", "微信朋友圈", "QQ分享", "微博分享"};
    private int[] mDrawables = {
            R.drawable.share_weixin,
            R.drawable.share_friends,
            R.drawable.share_qq,
            R.drawable.share_weibo
    };

    public void setContent(ShareInfo shareInfo) {
        mShareInfo = shareInfo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_dialog, container, true);
        unbind = ButterKnife.bind(this, view);
        mShareDialogLayout.setOnClickListener(this);
        mDialogShareList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mAdapter = new ShareAdapter(null, getActivity());
        mDialogShareList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            if (mShareInfo == null) {
                showToast("暂不支持分享，请稍后重试");
                return;
            }
            switch (position) {
                case 0:
                    ShareHelper.getInstance().shareInfo(getActivity(), mShareInfo, 1);
                    break;
                case 1:
                    ShareHelper.getInstance().shareInfo(getActivity(), mShareInfo, 2);
                    break;
                default:
                    showToast("该功能未开放");
                    break;

            }
        });
        AniCreator.getInstance().apply_animation_translate(mShareDialogLayout, AniCreator.ANIMATION_MODE_POPUP, View.VISIBLE, false, null);
        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<ShareStatus> shareStatuses = new ArrayList<>();
        for (int i = 0; i < mStrings.length; i++) {
            ShareStatus share = new ShareStatus();
            share.name = mStrings[i];
            share.res = mDrawables[i];
            shareStatuses.add(share);
        }
        mAdapter.setNewData(shareStatuses);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_dialog_layout:
                closeDialog(TAG);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }
}
