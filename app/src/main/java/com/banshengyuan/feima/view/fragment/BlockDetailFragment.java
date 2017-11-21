package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banshengyuan.feima.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderFragment
 */

public class BlockDetailFragment extends BaseFragment  {


    public static BlockDetailFragment newInstance() {
        return new BlockDetailFragment();
    }

    private Unbinder unbinder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_block_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initAdapter();
        return view;
    }

    private void initView() {

    }


    private void initData() {



    }

    private void initAdapter() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

   /* private void initialize() {
        DaggerBlockDetailFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .blockDetailActivityModule(new BlockDetailActivityModule((AppCompatActivity) getActivity(), this))
                .blockDetailFragmentModule(new BlockDetailFragmentModule((BlockDetailActivity) getActivity())).build()
                .inject(this);
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
