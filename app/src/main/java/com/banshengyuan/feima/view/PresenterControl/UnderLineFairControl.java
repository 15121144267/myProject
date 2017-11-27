package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.BlockDetailFairListResponse;
import com.banshengyuan.feima.entity.BlockDetailResponse;
import com.banshengyuan.feima.entity.BlockStoreListResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class UnderLineFairControl {
    public interface UnderLineFairView extends LoadDataView {
        void getBlockDetailSuccess(BlockDetailResponse response);
        void getBlockDetailFail(String des);
        void getBlockFairListSuccess(BlockDetailFairListResponse response);
        void getBlockFairListFail(String des);
        void getStoreListSuccess(BlockStoreListResponse response);
        void getStoreListFail();
    }

    public interface PresenterUnderLineFair extends Presenter<UnderLineFairView> {
        void requestBlockDetail(Integer blockId);
        void requestBlockFairList(Integer blockId);
        void requestBlockStoreList(Integer blockId);
    }
}
