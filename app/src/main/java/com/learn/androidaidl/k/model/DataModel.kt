package com.learn.androidaidl.k.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class DataModel : Parcelable {
    var name: String?
    var age: Int
    var list: MutableList<String?>?
    var isAlive: Boolean

    constructor(
        name: String?,
        age: Int,
        list: List<String?>?,
        isAlive: Boolean
    ) {
        this.name = name
        this.age = age
        this.list = ArrayList(list!!)
        this.isAlive = isAlive
    }

    private constructor(parcel: Parcel) {
        name = parcel.readString()
        age = parcel.readInt()
        list = parcel.createStringArrayList()
        isAlive = parcel.readByte().toInt() != 0
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeList(list as List<*>?)
        if (isAlive) {
            parcel.writeByte(1.toByte())
        } else {
            parcel.writeByte(0.toByte())
        }
    }

    override fun toString(): String {
        return "DataModel{" +
                "mName='" + name + '\'' +
                ", mAge=" + age +
                ", mList=" + list +
                ", mIsAlive=" + isAlive +
                '}'
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DataModel?> = object : Parcelable.Creator<DataModel?> {
            override fun createFromParcel(parcel: Parcel): DataModel? {
                return DataModel(parcel)
            }

            override fun newArray(size: Int): Array<DataModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}
