package io.trishul.classplanner.ui.classplans.create.gradplan;

import androidx.annotation.NonNull;

import io.trishul.classplanner.ui.gradplans.GradPlansAdapter;
import io.trishul.classplanner.ui.classplans.create.CreateNewClassPlanActivityModel;
import java.util.List;

public class SelectableGradPlansAdapter extends GradPlansAdapter {
    private Long selectedGradPlanId = null;
    private final CreateNewClassPlanActivityModel viewModel;

    public SelectableGradPlansAdapter(List<GradPlan> gradPlans, CreateNewClassPlanActivityModel viewModel) {
        super(gradPlans);
        this.viewModel = viewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull GradPlansAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        
        GradPlan gradPlan = getGradPlanAt(position);
        
        // Set selected state
        holder.itemView.setActivated(gradPlan.getGradPlanId().equals(selectedGradPlanId));
        
        // Add click listener
        holder.itemView.setOnClickListener(v -> {
            Long oldSelectedId = selectedGradPlanId;
            selectedGradPlanId = gradPlan.getGradPlanId();
            
            // Update view model
            viewModel.setSelectedGradPlanId(selectedGradPlanId);
            
            // Refresh views
            if (oldSelectedId != null) {
                notifyItemChanged(getPositionForGradPlanId(oldSelectedId));
            }
            notifyItemChanged(position);
        });
    }

    private int getPositionForGradPlanId(Long gradPlanId) {
        for (int i = 0; i < getItemCount(); i++) {
            if (getGradPlanAt(i).getGradPlanId().equals(gradPlanId)) {
                return i;
            }
        }
        return -1;
    }

    private GradPlan getGradPlanAt(int position) {
        return getGradPlans().get(position);
    }
}
