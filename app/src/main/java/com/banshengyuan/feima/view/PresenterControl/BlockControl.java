package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.BlockFairListResponse;
import com.banshengyuan.feima.entity.BlockHotListResponse;
import com.banshengyuan.feima.entity.BlockStoreListResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class BlockControl {
    public interface BlockView extends LoadDataView {
        void getHotListSuccess(BlockHotListResponse response);
        void getHotListFail();
        void getFairListSuccess(BlockFairListResponse response);
        void getFairListFail();
        void getStoreListSuccess(BlockStoreListResponse response);

        void getStoreListFail();
    }

    public interface PresenterBlock extends Presenter<BlockView> {
        void requestHotList(Integer blockId);
        void requestFairList(Integer blockId);
        void requestStoreList(Integer blockId);
    }
}
