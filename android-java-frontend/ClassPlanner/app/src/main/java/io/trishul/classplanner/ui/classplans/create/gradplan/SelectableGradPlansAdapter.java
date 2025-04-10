package io.trishul.classplanner.ui.classplans.create.gradplan;

import androidx.annotation.NonNull;

import io.trishul.classplanner.network.dtos.GradPlanDTO;
import io.trishul.classplanner.ui.gradplans.GradPlansAdapter;
import io.trishul.classplanner.ui.classplans.create.CreateNewClassPlanActivityModel;
import java.util.List;

public class SelectableGradPlansAdapter extends GradPlansAdapter {
    private Long selectedGradPlanId = null;
    private final CreateNewClassPlanActivityModel viewModel;

    public SelectableGradPlansAdapter(List<GradPlanDTO.Get> gradPlans, CreateNewClassPlanActivityModel viewModel) {
        super(gradPlans);
        this.viewModel = viewModel;
        // Initialize selection from view model
        this.selectedGradPlanId = viewModel.getSelectedGradPlanId().getValue();
    }

    @Override
    public void onBindViewHolder(@NonNull GradPlansAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        GradPlanDTO.Get gradPlan = gradPlans.get(position);
        
        // Set selected state
        holder.itemView.setSelected(gradPlan.getId().equals(selectedGradPlanId));
        
        // Add click listener
        holder.itemView.setOnClickListener(v -> {
            // Update selection
            Long oldSelectedId = selectedGradPlanId;
            selectedGradPlanId = gradPlan.getId();
            
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
        for (int i = 0; i < gradPlans.size(); i++) {
            if (gradPlans.get(i).getId().equals(gradPlanId)) {
                return i;
            }
        }
        return -1;
    }

    private GradPlanDTO.Get getGradPlanAt(int position) {
        return gradPlans.get(position);
    }
}
