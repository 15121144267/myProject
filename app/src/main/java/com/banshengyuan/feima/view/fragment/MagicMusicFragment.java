package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerMainFragmentComponent;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.dagger.module.MainFragmentModule;
import com.banshengyuan.feima.entity.MagicMusicResponse;
import com.banshengyuan.feima.view.PresenterControl.MagicMusicControl;
import com.banshengyuan.feima.view.activity.MainActivity;
import com.banshengyuan.feima.view.adapter.MagicMusicAdapter;

import java.util.ArrayList;
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
    @BindView(R.id.magic_music_recycle_view)
    RecyclerView mMagicMusicRecycleView;
    @BindView(R.id.recommend_search_layout)
    LinearLayout mRecommendSearchLayout;
    public static MagicMusicFragment newInstance() {
        return new MagicMusicFragment();
    }

    @Inject
    MagicMusicControl.PresenterMagicMusic mPresenter;

    private Unbinder unbind;
    private MagicMusicAdapter mMagicMusicAdapter;

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

    private void initData() {
        List<MagicMusicResponse> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MagicMusicResponse response = new MagicMusicResponse();
            response.name = "魔兽世界" + i;
            list.add(response);
        }
        mMagicMusicAdapter.setNewData(list);
    }

    private void initView() {
        mMagicMusicRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMagicMusicAdapter = new MagicMusicAdapter(null, getActivity());
        mMagicMusicRecycleView.setAdapter(mMagicMusicAdapter);
    }

    public void showSearchLayout(boolean flag) {
        if (!flag) {
            mRecommendSearchLayout.setVisibility(View.VISIBLE);
        } else {
            mRecommendSearchLayout.setVisibility(View.GONE);
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

    private void initialize() {
        DaggerMainFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .mainFragmentModule(new MainFragmentModule(this, (MainActivity) getActivity()))
                .build()
                .inject(this);
    }
}
