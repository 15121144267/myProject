package com.dispatching.feima.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dispatching.feima.gen.DaoMaster;

import javax.inject.Inject;

/**
 * Created by helei on 2017/4/25.
 * DbHelper
 */

public class DbHelper extends DaoMaster.OpenHelper {
    private static final String DB_NAME="fm_db";

    @Inject
    public DbHelper(Context context) {
        super(context, DB_NAME, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
