package vn.edu.vlsc.lab03a_vunguyenductri_2500115657.model

import android.os.Parcel
import android.os.Parcelable

// Time complexity khởi tạo: O(1)
// Triển khai chuẩn Parcelable trực tiếp theo Android SDK IPC Binder (tương thích 100% AGP 9.4 & Built-in Kotlin)
data class Student(
    val id: Int,
    val name: String,
    val mssv: String,
    val avatarRes: Int
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(mssv)
        parcel.writeInt(avatarRes)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }
}
