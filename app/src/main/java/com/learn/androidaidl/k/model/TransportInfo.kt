package com.learn.androidaidl.k.model

import android.os.Parcel
import android.os.Parcelable

class TransportInfo : Parcelable {
    var ipAddress: String?
    var port: Int

    constructor(parcel: Parcel) {
        ipAddress = parcel.readString()
        port = parcel.readInt()
    }

    constructor(ipAddress: String?, port: Int) {
        this.ipAddress = ipAddress
        this.port = port
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ipAddress)
        parcel.writeInt(port)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "TransportInfo(ipAddress=$ipAddress, port=$port)"
    }


    companion object CREATOR : Parcelable.Creator<TransportInfo> {
        override fun createFromParcel(parcel: Parcel): TransportInfo {
            return TransportInfo(parcel)
        }

        override fun newArray(size: Int): Array<TransportInfo?> {
            return arrayOfNulls(size)
        }
    }
}
