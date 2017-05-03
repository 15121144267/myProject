/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.dispatching.feima.view.PresenterControl;

/**
 * Interface representing a Presenter in a model view presenter (MVP) pattern.
 */
public interface Presenter<T extends LoadDataView> {

    void setView(T t);

    void resume();

    void pause();


    void onCreate();

    void onDestroy();
}
