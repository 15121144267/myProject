package com.banshengyuan.feima.view.PresenterImpl;

import com.banshengyuan.feima.view.PresenterControl.FollowControl;

/**
 * Created by helei on 2017/5/3.
 * PresenterPendingImpl
 */

public class PresenterFollowImpl implements FollowControl.PresenterFollow {
   /* private final MainModel mMainModel;
    private FollowControl.FollowView mView;
    private final Context mContext;

    @Inject
    public PresenterFollowImpl(Context context, MainModel model, FollowControl.FollowView view) {
        mContext = context;
        mMainModel = model;
        mView = view;
    }*/

   /* @Override
    public void requestShopId(String scoreCode, Integer type) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mShopListModel.shopIdRequest(scoreCode, type).compose(mView.applySchedulers())
                .subscribe(this::getShopSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getShopSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(ShopResponse.class);
            ShopResponse response = (ShopResponse) responseData.parsedData;
            mView.getShopSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }
*/
    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
//        mView = null;
    }
}
