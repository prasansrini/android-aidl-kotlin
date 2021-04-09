// IServerConnectionCallback.aidl
package com.learn.androidaidl.k.callbacks;

import com.learn.androidaidl.k.model.TransportInfo;

interface IServerConnectionCallback {
    void onConnected(in TransportInfo transportInfo);
    void disconnected(in TransportInfo transportInfo, in String cause);
}
