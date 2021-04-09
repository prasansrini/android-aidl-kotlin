package com.learn.androidaidl.k.service.server

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException

class ServerImpl private constructor(port: Int, ipAddress: InetAddress) {
    private var mServerSocket: ServerSocket?
    private var mPort: Int = port
    private var mIpAddress: InetAddress = ipAddress
    private var mServerStatus: CompletableDeferred<Boolean> = CompletableDeferred()
    private var mSocket: Socket? = null

    fun startServerAsync(): Deferred<Boolean> {
        GlobalScope.launch {
            try {
                mServerStatus.complete(true)
                mSocket = mServerSocket?.accept()
            } catch (e: Exception) {
                mServerStatus.completeExceptionally(e)
                e.printStackTrace()
            }
        }

        return mServerStatus
    }

    fun stopServer() {
        if (mServerSocket != null) {
            mServerSocket!!.close()
        }
        if (mSocket != null) {
            mSocket!!.close()
        }

        mServerSocket = null
    }

    companion object {
        var instance: ServerImpl? = null

        fun buildServer(port: Int, ipAddress: InetAddress): ServerImpl? {
            if (instance == null) {
                instance = ServerImpl(port, ipAddress)
            }

            return instance
        }
    }

    init {
        try {
            mServerSocket = ServerSocket(mPort, 50, mIpAddress)
        } catch (e: SocketException) {
            mServerSocket = null
            e.printStackTrace()
        }
    }
}
