package io.trishul.classplanner.ui.gradplans;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import io.trishul.classplanner.R;
import io.trishul.classplanner.api.models.GradPlan;

public class GradPlansAdapter extends RecyclerView.Adapter<GradPlansAdapter.ViewHolder> {
    private final List<GradPlan> gradPlans;

    public GradPlansAdapter(List<GradPlan> gradPlans) {
        this.gradPlans = gradPlans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grad_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GradPlan gradPlan = gradPlans.get(position);
        holder.programName.setText(gradPlan.getProgramName());
        holder.credits.setText(String.format("%d/%d", gradPlan.getCreditsCompleted(), gradPlan.getCreditsRequired()));
        holder.gpa.setText(String.valueOf(gradPlan.getCurrentGpa()));
        holder.createdAt.setText(gradPlan.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return gradPlans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
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
