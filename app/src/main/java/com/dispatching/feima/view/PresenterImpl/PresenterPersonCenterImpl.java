package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.view.PresenterControl.PersonCenterControl;
import com.dispatching.feima.view.model.PersonCenterModel;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/6/26.
 */

public class PresenterPersonCenterImpl implements PersonCenterControl.PresenterPersonCenter {
    private PersonCenterControl.PersonCenterView mView;
    private Context mContext;
    private PersonCenterModel mModel;

    @Inject
    public PresenterPersonCenterImpl(Context context, PersonCenterControl.PersonCenterView view, PersonCenterModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

   /* @Override
    public void requestAddAddress(AddAddressRequest request) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.addAddressRequest(request).compose(mView.applySchedulers())
                .subscribe(this::addAddressSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }*/

   /* private void addAddressSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            mView.addAddressSuccess();
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
        mView = null;
    }
}
