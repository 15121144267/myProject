package com.dispatching.feima.dagger;


import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.service.CustomerService;
import com.dispatching.feima.view.activity.BaseActivity;
import com.dispatching.feima.view.fragment.BaseFragment;

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
