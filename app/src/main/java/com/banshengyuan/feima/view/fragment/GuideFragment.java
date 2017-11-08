package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.SpConstant;


public final class GuideFragment extends BaseFragment {

    public static GuideFragment newInstance(int position) {
        GuideFragment fragment = new GuideFragment();
        fragment.p = position;

        return fragment;
    }

    private int p;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, container, false);
        deal(view);
        return view;
    }

    private void deal(View v) {
        ImageView imageView = (ImageView)v.findViewById(R.id.guide_img_id);
        Button btn = (Button)v.findViewById(R.id.begin_to_use);
        switch (p) {
            case 0:
                imageView.setImageResource(R.mipmap.guide_first);
                btn.setVisibility(View.GONE);
                break;

            case 1:
                imageView.setImageResource(R.mipmap.guide_second);
                btn.setVisibility(View.GONE);
                break;

            case 2:
                imageView.setImageResource(R.mipmap.guide_third);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(v1->{
                    mSharePreferenceUtil.setBooleanValue(SpConstant.FIRST_OPEN_KEY, false);
                    getActivity().setResult(100);
                    getActivity().finish();
                });
                break;
        }
    }
}
