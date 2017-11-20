package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.MusicListResponse;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderControl
 */

public class MagicMusicControl {
    public interface MagicMusicView extends LoadDataView{
        void getMusicListSuccess(MusicListResponse musicListResponse);
        void getMusicListFail( );
    }

    public interface PresenterMagicMusic extends Presenter<MagicMusicView> {
        void requestMusicList();
    }
}
