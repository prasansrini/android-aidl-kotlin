// IMyAidlInterface.aidl
package com.learn.androidaidl.k;

import com.learn.androidaidl.k.model.DataModel;
import com.learn.androidaidl.k.callbacks.IFetchUpdatedListCallback;

interface IMyAidlInterface {
    String getMessage();
    void setDataModel(in DataModel dataModel);
    void fetchUpdatedList(in DataModel dataModel, in IFetchUpdatedListCallback iFetchUpdatedListCallback);
}
