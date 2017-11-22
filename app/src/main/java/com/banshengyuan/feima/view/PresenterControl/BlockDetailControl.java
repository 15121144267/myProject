package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.BlockDetailResponse;
import com.banshengyuan.feima.entity.BlockFairListResponse;
import com.banshengyuan.feima.entity.BlockHotListResponse;
import com.banshengyuan.feima.entity.BlockStoreListResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddressControl
 */

public class BlockDetailControl {
    public interface BlockDetailView extends LoadDataView {
        void getBlockDetailSuccess(BlockDetailResponse response);

        void getBlockDetailFail();

        void getHotListSuccess(BlockHotListResponse response);

        void getHotListFail();

        void getFairListSuccess(BlockFairListResponse response);

        void getFairListFail();

        void getStoreListSuccess(BlockStoreListResponse response);

        void getStoreListFail();

    }

    public interface PresenterBlockDetail extends Presenter<BlockDetailView> {
        void requestBlockDetail(Integer blockId);

        void requestHotList(Integer blockId);

        void requestFairList(Integer blockId);

        void requestStoreList(Integer blockId);
    }
}
