package io.trishul.classplanner.network.dtos;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvailabilityDTO implements Parcelable {
    private List<AvailabilityDayDTO> days;

    public AvailabilityDTO(Parcel in) {
        days = in.createTypedArrayList(AvailabilityDayDTO.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(days);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AvailabilityDTO> CREATOR = new Creator<AvailabilityDTO>() {
        @Override
        public AvailabilityDTO createFromParcel(Parcel in) {
            return new AvailabilityDTO(in);
        }

        @Override
        public AvailabilityDTO[] newArray(int size) {
            return new AvailabilityDTO[size];
        }
    };

    @Data
    @NoArgsConstructor
    public static class AvailabilityDayDTO implements Parcelable {
        private String day;
        private List<AvailabilityHourDTO> hours;

        public AvailabilityDayDTO(Parcel in) {
            day = in.readString();
            hours = in.createTypedArrayList(AvailabilityHourDTO.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(day);
            dest.writeTypedList(hours);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<AvailabilityDayDTO> CREATOR = new Creator<AvailabilityDayDTO>() {
            @Override
            public AvailabilityDayDTO createFromParcel(Parcel in) {
                return new AvailabilityDayDTO(in);
            }

            @Override
            public AvailabilityDayDTO[] newArray(int size) {
                return new AvailabilityDayDTO[size];
            }
        };
    }

    @Data
    @NoArgsConstructor
    public static class AvailabilityHourDTO implements Parcelable {
        private Integer hourOfTheDay;
        private Boolean isAvailable;

        protected AvailabilityHourDTO(Parcel in) {
            hourOfTheDay = in.readInt();
            byte tmpIsAvailable = in.readByte();
            isAvailable = tmpIsAvailable == 0 ? null : tmpIsAvailable == 1;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(hourOfTheDay);
            if (isAvailable == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) (isAvailable ? 1 : 2));
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<AvailabilityHourDTO> CREATOR = new Creator<AvailabilityHourDTO>() {
            @Override
            public AvailabilityHourDTO createFromParcel(Parcel in) {
                return new AvailabilityHourDTO(in);
            }

            @Override
            public AvailabilityHourDTO[] newArray(int size) {
                return new AvailabilityHourDTO[size];
            }
        };
    }
}
