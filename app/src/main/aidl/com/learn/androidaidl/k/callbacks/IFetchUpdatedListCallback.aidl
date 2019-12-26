// IFetchUpdatedListCallback.aidl
package com.learn.androidaidl.k.callbacks;

import com.learn.androidaidl.k.model.DataModel;

interface IFetchUpdatedListCallback {
    void onListUpdated(in DataModel dataModel);
}
