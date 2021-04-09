package com.learn.androidaidl.k.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.learn.androidaidl.k.IMyAidlInterface
import com.learn.androidaidl.k.callbacks.IServerConnectionCallback
import com.learn.androidaidl.k.model.TransportInfo
import com.learn.androidaidl.k.service.server.ServerImpl
import java.net.InetAddress

class MyService : Service() {
    private lateinit var mTransportInfo: TransportInfo

    private val mIMyAidlInterface: IMyAidlInterface.Stub = object : IMyAidlInterface.Stub() {
        override fun buildServer(
            transportInfo: TransportInfo
        ) {
            mTransportInfo = transportInfo
            ServerImpl.buildServer(
                transportInfo.port,
                InetAddress.getByName(transportInfo.ipAddress)
            )
        }

        override fun startServer(iServerConnectionCallback: IServerConnectionCallback?) {
            val connectionStatus = ServerImpl.instance?.startServerAsync()
            connectionStatus?.invokeOnCompletion { cause ->
                if (cause == null) {
                    iServerConnectionCallback?.onConnected(mTransportInfo)
                } else {
                    iServerConnectionCallback?.disconnected(mTransportInfo, cause.localizedMessage)
                }
            }
        }

        override fun stopServer() {
            ServerImpl.instance?.stopServer()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return mIMyAidlInterface
    }

}