package com.learn.androidaidl.k.activity

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.learn.androidaidl.k.IMyAidlInterface
import com.learn.androidaidl.k.R
import com.learn.androidaidl.k.callbacks.IFetchUpdatedListCallback
import com.learn.androidaidl.k.model.DataModel
import com.learn.androidaidl.k.service.MyService
import java.util.*

class MainActivity : AppCompatActivity() {
    private var mIMyAidlInterface: IMyAidlInterface? = null
    private val mIFetchUpdatedListCallback: IFetchUpdatedListCallback =
        object : IFetchUpdatedListCallback.Stub() {
            override fun onListUpdated(dataModel: DataModel) {
                Log.v(TAG, dataModel.toString())
            }

            override fun asBinder(): IBinder {
                return this
            }
        }
    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            componentName: ComponentName,
            iBinder: IBinder
        ) {
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder)
            Toast.makeText(this@MainActivity, "Service connected.", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mIMyAidlInterface = null
            Toast.makeText(this@MainActivity, "Service disconnected.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        bindService(
            Intent(this, MyService::class.java).setPackage(
                "com.learn.androidaidl.k.service"
            ), mServiceConnection, Service.BIND_AUTO_CREATE
        )
    }

    fun onButtonClick(view: View?) {
        try {
            if (mIMyAidlInterface != null) {
                val value = mIMyAidlInterface!!.message
                val listArray: MutableList<String> =
                    ArrayList()
                listArray.add("10")
                listArray.add("11")
                listArray.add("12")
                val dataModel = DataModel("Data", 12, listArray, true)
                mIMyAidlInterface!!.fetchUpdatedList(
                    dataModel,
                    mIFetchUpdatedListCallback
                )
                (findViewById<View>(R.id.result) as TextView).text = value
                Log.v(TAG, value)
            } else {
                Log.v(TAG, "AIDL not initialized.")
            }
        } catch (e: RemoteException) {
            Log.e(TAG, e.localizedMessage, e)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(mServiceConnection)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
