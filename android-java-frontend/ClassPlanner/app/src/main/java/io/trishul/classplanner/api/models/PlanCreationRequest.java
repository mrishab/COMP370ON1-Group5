package io.trishul.classplanner.api.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Map;

public class PlanCreationRequest implements Parcelable {
    @SerializedName("gradPlanId")
    private Long gradPlanId;
    @SerializedName("desiredNumberOfClasses")
    private int desiredNumberOfClasses;
    @SerializedName("burdenCapacity")
    private String burdenCapacity;
    @SerializedName("classDistribution")
    private String classDistribution;
    @SerializedName("availability")
    private Map<String, boolean[]> availability;

    public PlanCreationRequest() {
        // Default constructor
    }

    public Long getGradPlanId() {
        return gradPlanId;
    }

    public void setGradPlanId(Long gradPlanId) {
        this.gradPlanId = gradPlanId;
    }

    public int getDesiredNumberOfClasses() {
        return desiredNumberOfClasses;
    }

    public void setDesiredNumberOfClasses(int desiredNumberOfClasses) {
        this.desiredNumberOfClasses = desiredNumberOfClasses;
    }

    public String getBurdenCapacity() {
        return burdenCapacity;
    }

    public void setBurdenCapacity(String burdenCapacity) {
        this.burdenCapacity = burdenCapacity;
    }

    public String getClassDistribution() {
        return classDistribution;
    }

    public void setClassDistribution(String classDistribution) {
        this.classDistribution = classDistribution;
    }

    public Map<String, boolean[]> getAvailability() {
        return availability;
    }

    public void setAvailability(Map<String, boolean[]> availability) {
        this.availability = availability;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static PlanCreationRequest fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, PlanCreationRequest.class);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(this.toJson());
    }

    public static final Creator<PlanCreationRequest> CREATOR = new Creator<PlanCreationRequest>() {
        @Override
        public PlanCreationRequest createFromParcel(Parcel in) {
            String json = in.readString();
            return PlanCreationRequest.fromJson(json);
        }

        @Override
        public PlanCreationRequest[] newArray(int size) {
            return new PlanCreationRequest[size];
        }
    };
}
