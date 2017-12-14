package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerMainFragmentComponent;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.dagger.module.MainFragmentModule;
import com.banshengyuan.feima.entity.MusicListResponse;
import com.banshengyuan.feima.view.PresenterControl.MagicMusicControl;
import com.banshengyuan.feima.view.activity.FairProductDetailActivity;
import com.banshengyuan.feima.view.activity.MainActivity;
import com.banshengyuan.feima.view.activity.WorkSummaryActivity;
import com.banshengyuan.feima.view.adapter.MagicMusicAdapter;
import com.banshengyuan.feima.view.adapter.MagicMusicHotAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderFragment
 */

public class MagicMusicFragment extends BaseFragment implements MagicMusicControl.MagicMusicView {

    @BindView(R.id.magic_fair_recycle_view)
    RecyclerView mMagicFairRecycleView;
    @BindView(R.id.magic_hot_recycle_view)
    RecyclerView mMagicHotRecycleView;

    public static MagicMusicFragment newInstance() {
        return new MagicMusicFragment();
    }

    @Inject
    MagicMusicControl.PresenterMagicMusic mPresenter;

    private Unbinder unbind;
    private MagicMusicAdapter mMagicMusicAdapter;
    private MagicMusicHotAdapter mMusicHotAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_magic_music, container, false);
        unbind = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void getMusicListSuccess(MusicListResponse musicListResponse) {
        List<MusicListResponse.FairBean> fairList = musicListResponse.fair;
        List<MusicListResponse.HotBean> hotList = musicListResponse.hot;
        if (fairList != null && fairList.size() > 0) {
            mMagicMusicAdapter.setNewData(fairList);
        }
        if (hotList != null && hotList.size() > 0) {
            mMusicHotAdapter.setNewData(hotList);
        }
    }

    @Override
    public void getMusicListFail() {
        mMagicFairRecycleView.setVisibility(View.GONE);
        mMagicHotRecycleView.setVisibility(View.GONE);
    }

    private void initData() {
        mPresenter.requestMusicList();
    }

    private void initView() {
        mMagicFairRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMagicMusicAdapter = new MagicMusicAdapter(null, getActivity(), mImageLoaderHelper);
        mMagicFairRecycleView.setAdapter(mMagicMusicAdapter);

        mMagicHotRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMusicHotAdapter = new MagicMusicHotAdapter(null, getActivity(), mImageLoaderHelper);
        mMagicHotRecycleView.setAdapter(mMusicHotAdapter);

        mMagicMusicAdapter.setOnItemClickListener((adapter, view, position) -> {
            MusicListResponse.FairBean bean = (MusicListResponse.FairBean) adapter.getItem(position);
            if (bean != null) {
                startActivity(WorkSummaryActivity.getSummaryIntent(getActivity(), bean.id));
            }
        });

        mMusicHotAdapter.setOnItemClickListener((adapter, view, position) -> {
            MusicListResponse.HotBean bean = (MusicListResponse.HotBean) adapter.getItem(position);
            if (bean != null) {
                startActivity(FairProductDetailActivity.getIntent(getActivity(), bean.id + ""));
            }
        });
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
        DaggerMainFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .mainFragmentModule(new MainFragmentModule(this, (MainActivity) getActivity()))
                .build()
                .inject(this);
    }
}
