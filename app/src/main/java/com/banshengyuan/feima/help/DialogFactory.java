package com.banshengyuan.feima.help;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.view.activity.BaseActivity;

import java.util.Calendar;


public class DialogFactory {

    public static AlertDialog newAlertDialog(Context context, String title,
                                             String msg, String[] btnNames,
                                             DialogInterface.OnClickListener... listeners) {

        DialogInterface.OnClickListener tempListener = null;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (title != null) {
            builder.setTitle(title);
        }
        if (msg != null) {
            builder.setMessage(msg);
        }

        if (btnNames.length > 0) {
            if (listeners == null || listeners.length < 1) {
                builder.setPositiveButton(btnNames[0], null);
            } else {
                builder.setPositiveButton(btnNames[0], listeners[0]);
            }
        }
        if (btnNames.length > 1) {
            if (listeners == null || listeners.length < 2) {
                builder.setPositiveButton(btnNames[0], null);
            } else {
                builder.setNeutralButton(btnNames[1], listeners[1]);
            }
        }
        if (btnNames.length > 2) {
            if (listeners == null || listeners.length < 3) {
                builder.setPositiveButton(btnNames[0], null);
            } else {
                builder.setNegativeButton(btnNames[2], listeners[2]);
            }
        }

        return builder.create();
    }

    public static AlertDialog newMsgDialog(Context context, String msg,
                                           String title, String confirm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_menu_more);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton(confirm, null);
        return builder.create();
    }

    public static DatePickerDialog newDatePickerDialog(Context context,
                                                       OnDateSetListener callBack) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(context,
                callBack, year, monthOfYear, dayOfMonth);
    }

    public static AlertDialog newCustomViewDialog(Context context,
                                                  String title, String[] btnNames, View customView,
                                                  DialogInterface.OnClickListener... btnListeners) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null) {
            builder.setTitle(title);
        }

        builder.setView(customView);

        if (btnNames.length > 0) {
            if (btnListeners == null || btnListeners.length < 1) {
                builder.setPositiveButton(btnNames[0], null);
            } else {
                builder.setPositiveButton(btnNames[0], btnListeners[0]);
            }
        }
        if (btnNames.length > 1) {
            if (btnListeners == null || btnListeners.length < 2) {
                builder.setPositiveButton(btnNames[0], null);
            } else {
                builder.setNeutralButton(btnNames[1], btnListeners[1]);
            }
        }
        if (btnNames.length > 2) {
            if (btnListeners == null || btnListeners.length < 3) {
                builder.setPositiveButton(btnNames[0], null);
            } else {
                builder.setNegativeButton(btnNames[2], btnListeners[2]);
            }
        }
        return builder.create();
    }

    public static void showDialogFragment(FragmentManager fragManager, DialogFragment dialogFragment, String tag) {
        try {
            FragmentTransaction ft = fragManager.beginTransaction();
            Fragment prev = fragManager.findFragmentByTag(tag);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.add(dialogFragment, tag);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissDialogFragment(FragmentManager fragManager, String tag) {

        FragmentTransaction ft = fragManager.beginTransaction();
        Fragment fragment = fragManager.findFragmentByTag(tag);
        if (fragment != null) {
            ft.remove(fragment);
            ft.commitAllowingStateLoss();
        }
    }

    public static ProgressDialog showProgressDialog(Context context, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static Dialog showLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.view_loading, (ViewGroup) ((BaseActivity)context).getWindow().getDecorView(),false);// 得到加载view
        final Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(v);
        return loadingDialog;

    }
}
