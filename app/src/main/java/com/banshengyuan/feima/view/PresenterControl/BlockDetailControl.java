package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.BlockDetailResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddressControl
 */

public class BlockDetailControl {
    public interface BlockDetailView extends LoadDataView {
        void getBlockDetailSuccess(BlockDetailResponse response);
        void getBlockDetailFail();

    }

    public interface PresenterBlockDetail extends Presenter<BlockDetailView> {
        void requestBlockDetail(Integer blockId);
    }
}
