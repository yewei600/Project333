package com.ericwei.project333.clothes_tab;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

/**
 * Created by ericwei on 2017-03-28.
 */

public class DBHelper implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;

    public DBHelper(Context context) {
        this.context = context;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
