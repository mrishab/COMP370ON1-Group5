package io.trishul.classplanner.network.dtos;

import java.time.LocalDateTime;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ClassPlanDTO {
    @Data
    public static class Get {
        private Long id;
        private GradPlanDTO.Get gradPlan;
        private String description;
        private List<CourseDTO> classes;
        private AvailabilityDTO availability;
        private ClassDistribution classDistribution;
        private BurdenCapacity burdenCapacity;
        private String createdAt;
        private String updatedAt;
    }

    @Data
    @NoArgsConstructor
    public static class Post implements Parcelable {
        private Long gradPlanId;
        private String description;
        private AvailabilityDTO availability;
        private ClassDistribution classDistribution;
        private BurdenCapacity burdenCapacity;

        public Post(Parcel in) {
            gradPlanId = in.readLong();
            description = in.readString();
            availability = in.readParcelable(AvailabilityDTO.class.getClassLoader());
            classDistribution = in.readParcelable(ClassDistribution.class.getClassLoader());
            burdenCapacity = in.readParcelable(BurdenCapacity.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(gradPlanId);
            dest.writeString(description);
            dest.writeParcelable(availability, flags);
            dest.writeParcelable(classDistribution, flags);
            dest.writeParcelable(burdenCapacity, flags);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Post> CREATOR = new Creator<Post>() {
            @Override
            public Post createFromParcel(Parcel in) {
                return new Post(in);
            }

            @Override
            public Post[] newArray(int size) {
                return new Post[size];
            }
        };
    }
}
