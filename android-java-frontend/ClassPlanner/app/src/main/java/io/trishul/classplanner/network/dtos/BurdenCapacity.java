package io.trishul.classplanner.network.dtos;

import android.os.Parcel;
import android.os.Parcelable;

public enum BurdenCapacity implements Parcelable {
    LOW, MEDIUM, HIGH;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BurdenCapacity> CREATOR = new Creator<BurdenCapacity>() {
        @Override
        public BurdenCapacity createFromParcel(Parcel in) {
            return BurdenCapacity.valueOf(in.readString());
        }

        @Override
        public BurdenCapacity[] newArray(int size) {
            return new BurdenCapacity[size];
        }
    };
}
