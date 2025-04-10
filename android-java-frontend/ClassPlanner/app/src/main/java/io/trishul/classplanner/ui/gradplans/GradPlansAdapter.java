package io.trishul.classplanner.ui.gradplans;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import io.trishul.classplanner.R;
import io.trishul.classplanner.network.dtos.GradPlanDTO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GradPlansAdapter extends RecyclerView.Adapter<GradPlansAdapter.ViewHolder> {

    protected final List<GradPlanDTO.Get> gradPlans;

    public GradPlansAdapter(List<GradPlanDTO.Get> gradPlans) {
        this.gradPlans = gradPlans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grad_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GradPlanDTO.Get gradPlan = gradPlans.get(position);

        holder.programName.setText(gradPlan.getProgramName());
        holder.majorName.setText(gradPlan.getMajorName());
        holder.credits.setText(String.format("Credits: %d/%d", 
            gradPlan.getCreditsCompleted(), gradPlan.getCreditsRequired()));

        holder.cgpa.setText(String.format("%.2f", gradPlan.getCgpa()));
        setCgpaBackground(holder.cgpa, gradPlan.getCgpa());

        holder.programLevel.setText(gradPlan.getProgramLevel());

        // Format date nicely
        LocalDateTime createdAt = LocalDateTime.parse(gradPlan.getCreatedAt());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        holder.createdAt.setText("Created: " + createdAt.format(formatter));
    }

    private void setCgpaBackground(TextView cgpaView, double cgpa) {
        int colorResId;
        if (cgpa >= 3.7) colorResId = R.color.cgpa_excellent;
        else if (cgpa >= 3.0) colorResId = R.color.cgpa_good;
        else if (cgpa >= 2.0) colorResId = R.color.cgpa_fair;
        else colorResId = R.color.cgpa_poor;

        cgpaView.getBackground().setTint(
            ContextCompat.getColor(cgpaView.getContext(), colorResId));
    }

    @Override
    public int getItemCount() {
        return gradPlans.size();
    }

    protected List<GradPlanDTO.Get> getGradPlans() {
        return gradPlans;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView programName, majorName, credits, cgpa, createdAt;
        Chip programLevel;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            programName = itemView.findViewById(R.id.tv_program_name);
            majorName = itemView.findViewById(R.id.tv_major_name);
            credits = itemView.findViewById(R.id.tv_credits);
            cgpa = itemView.findViewById(R.id.tv_cgpa);
            createdAt = itemView.findViewById(R.id.tv_created_at);
            programLevel = itemView.findViewById(R.id.chip_program_level);
        }
    }
}
