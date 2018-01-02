package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.view.PresenterControl.CollectionControl;
import com.banshengyuan.feima.view.model.CollectionModel;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterCollectionImpl implements CollectionControl.PresenterCollection {
    private CollectionControl.CollectionView mView;
    private Context mContext;
    private CollectionModel mModel;

    @Inject
    public PresenterCollectionImpl(Context context, CollectionControl.CollectionView view, CollectionModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void onCreate() {

    }


    @Override
    public void onDestroy() {
        mView = null;
    }
}
