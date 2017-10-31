package com.banshengyuan.feima.view.PresenterControl;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class CollectionControl {
    public interface CollectionView extends LoadDataView {
    }

    public interface PresenterCollection extends Presenter<CollectionView> {
    }
}
