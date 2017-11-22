package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.BlockDetailResponse;
import com.banshengyuan.feima.entity.BlockHotListResponse;

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

    }

    public interface PresenterBlockDetail extends Presenter<BlockDetailView> {
        void requestBlockDetail(Integer blockId);
        void requestHotList(Integer blockId);
    }
}
