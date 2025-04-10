package io.trishul.classplanner.ui.gradplans;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import io.trishul.classplanner.R;
import io.trishul.classplanner.network.dtos.GradPlanDTO;

public class GradPlansAdapter extends RecyclerView.Adapter<GradPlansAdapter.ViewHolder> {
    private final List<GradPlanDTO.Get> gradPlans;

    public GradPlansAdapter(List<GradPlanDTO.Get> gradPlans) {
        this.gradPlans = gradPlans;
    }

    protected List<GradPlanDTO.Get> getGradPlans() {
        return gradPlans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grad_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GradPlanDTO.Get gradPlan = gradPlans.get(position);
        
        // Set program name, handle nulls
        holder.programName.setText(gradPlan.getProgramName() != null ? 
            gradPlan.getProgramName() : "Unknown Program");

        // Format credits as completed/required
        String credits = String.format("%d/%d", 
            gradPlan.getCreditsCompleted() != null ? gradPlan.getCreditsCompleted() : 0,
            gradPlan.getCreditsRequired() != null ? gradPlan.getCreditsRequired() : 0);
        holder.credits.setText(credits);

        // Format CGPA with 2 decimal places
        String gpa = String.format("%.2f", 
            gradPlan.getCgpa() != null ? gradPlan.getCgpa() : 0.0);
        holder.gpa.setText(gpa);

        // Format created date
        if (gradPlan.getCreatedAt() != null) {
            holder.createdAt.setText(gradPlan.getCreatedAt().toString());
        } else {
            holder.createdAt.setText("Unknown Date");
        }
    }

    @Override
    public int getItemCount() {
        return gradPlans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView programName, credits, gpa, createdAt;

        ViewHolder(View itemView) {
            super(itemView);
            programName = itemView.findViewById(R.id.program_name);
            credits = itemView.findViewById(R.id.credits);
            gpa = itemView.findViewById(R.id.gpa);
            createdAt = itemView.findViewById(R.id.created_at);
        }
    }
}
