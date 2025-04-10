package io.trishul.classplanner.network.dtos;

import android.os.Parcel;
import android.os.Parcelable;

public enum ClassDistribution implements Parcelable {
    CONCENTRATED, SPARSE;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClassDistribution> CREATOR = new Creator<ClassDistribution>() {
        @Override
        public ClassDistribution createFromParcel(Parcel in) {
            return ClassDistribution.valueOf(in.readString());
        }

        @Override
        public ClassDistribution[] newArray(int size) {
            return new ClassDistribution[size];
        }
    };
}
