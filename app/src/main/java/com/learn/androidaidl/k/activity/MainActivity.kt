package com.learn.androidaidl.k.activity

import android.app.Activity
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.view.View
import android.widget.Toast
import com.learn.androidaidl.k.IMyAidlInterface
import com.learn.androidaidl.k.R
import com.learn.androidaidl.k.callbacks.IServerConnectionCallback
import com.learn.androidaidl.k.model.TransportInfo
import com.learn.androidaidl.k.service.MyService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    private var mServerImplService: IMyAidlInterface? = null

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            componentName: ComponentName,
            iBinder: IBinder
        ) {
            mServerImplService = IMyAidlInterface.Stub.asInterface(iBinder)
            Toast.makeText(this@MainActivity, "Service connected.", Toast.LENGTH_SHORT).show()
            val transportInfo = TransportInfo("127.0.0.1", 9130)
            mServerImplService!!.buildServer(transportInfo)
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mServerImplService = null
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
            if (mServerImplService != null) {
                mServerImplService?.startServer(object : IServerConnectionCallback.Stub() {
                    override fun disconnected(transportInfo: TransportInfo?, cause: String) {
                        result.text = "Disconnected due to $cause!"
                    }

                    override fun onConnected(transportInfo: TransportInfo?) {
                        result.text =
                            "Connected to ${transportInfo?.ipAddress}:${transportInfo?.port}"
                    }
                })
            } else {
                Log.v(TAG, "Service not initialized.")
            }
        } catch (e: RemoteException) {
            Log.e(TAG, e.localizedMessage, e)
        }
    }

    override fun onStop() {
        super.onStop()
        mServerImplService?.stopServer()
        mServerImplService = null
        unbindService(mServiceConnection)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
