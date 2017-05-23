/**
 * 
 */
package com.dispatching.feima.view.fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dispatching.feima.utils.ToastUtils;

/**
 * @author helei
 */
public  class BaseDialogFragment extends DialogFragment implements View.OnClickListener{
	public static final String TAG = BaseDialogFragment.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);
	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if(getDialog() == null){
            setShowsDialog(false);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return dialog;
    }

	@Override
	public void onDestroy() {

		super.onDestroy();
	}

    @Override
    public void onClick(View v) {

    }

    public void showMessage(String msg) {
        ToastUtils.showShortToast(msg);
    }

    public void showMessage(int msgId) {
        ToastUtils.showShortToast(msgId);
    }
}
