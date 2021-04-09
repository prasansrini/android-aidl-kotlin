// IMyAidlInterface.aidl
package com.learn.androidaidl.k;

import com.learn.androidaidl.k.model.TransportInfo;
import com.learn.androidaidl.k.model.DataModel;
import com.learn.androidaidl.k.callbacks.IServerConnectionCallback;

interface IMyAidlInterface {
    void buildServer(in TransportInfo transportInfo);
    void startServer(in IServerConnectionCallback iServerConnectionCallback);
    void stopServer();
}
