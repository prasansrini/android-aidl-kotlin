package com.learn.androidaidl.k.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.learn.androidaidl.k.IMyAidlInterface
import com.learn.androidaidl.k.callbacks.IFetchUpdatedListCallback
import com.learn.androidaidl.k.model.DataModel

class MyService : Service() {
    private val mIMyAidlInterface: IMyAidlInterface.Stub = object : IMyAidlInterface.Stub() {
        override fun getMessage(): String {
            return "Hello"
        }

        override fun setDataModel(dataModel: DataModel) {
            Log.e(TAG, dataModel.toString())
        }

        override fun fetchUpdatedList(
            dataModel: DataModel,
            iFetchUpdatedListCallback: IFetchUpdatedListCallback
        ) {
            Handler().postDelayed({
                try {
                    for (i in dataModel.list!!.indices) {
                        dataModel.list!!.set(i, "" + i)
                    }
                    iFetchUpdatedListCallback.onListUpdated(dataModel)
                } catch (e: RemoteException) {
                    Log.e(TAG, e.localizedMessage, e)
                }
            }, TIME_OUT.toLong())
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return mIMyAidlInterface
    }

    companion object {
        private const val TIME_OUT = 1000
        private const val TAG = "MyService"
    }
}