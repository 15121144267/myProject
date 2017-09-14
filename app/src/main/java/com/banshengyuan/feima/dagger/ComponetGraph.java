package com.banshengyuan.feima.dagger;


import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.service.CustomerService;
import com.banshengyuan.feima.view.activity.BaseActivity;
import com.banshengyuan.feima.view.fragment.BaseFragment;

/**
 * Created by wxl on 16/3/30.
 *
 */
public interface ComponetGraph {

    void inject(DaggerApplication application);

    void inject(BaseActivity baseActivity);
    void inject(BaseFragment baseFragment);

    void inject(CustomerService service);

}
