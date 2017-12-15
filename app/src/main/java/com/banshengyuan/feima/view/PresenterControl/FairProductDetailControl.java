package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.HotFairDetailResponse;
import com.banshengyuan.feima.entity.HotFairStateResponse;
import com.banshengyuan.feima.entity.HotFariJoinActionRequest;
import com.banshengyuan.feima.entity.HotFariJoinActionResponse;
import com.banshengyuan.feima.entity.HotFariStateRequest;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class FairProductDetailControl {
    public interface FairProductDetailView extends LoadDataView {
        void getHotFairDetailSuccess(HotFairDetailResponse response);

        void getHotFairStateSuccess(HotFairStateResponse response);

        void getHotFairJoinActionSuccess(HotFariJoinActionResponse response);
    }

    public interface PresenterFairProductDetail extends Presenter<FairProductDetailView> {
        void requestHotFairDetail(String id,String token);//请求热闹详情

        void requestHotFairState(String id, String order_sn,String token);//热闹-报名订单状态查询

        void requestHotFairJoinAction(String id, String phone,String token);//热闹-报名

    }
}
