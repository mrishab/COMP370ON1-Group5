package io.trishul.classplanner.ui.classplans;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import io.trishul.classplanner.R;
import io.trishul.classplanner.network.dtos.ClassPlanDTO;

public class ClassPlansAdapter extends RecyclerView.Adapter<ClassPlansAdapter.ViewHolder> {
    private final List<ClassPlanDTO.Get> classPlans;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ClassPlanDTO.Get classPlan);
    }

    public ClassPlansAdapter(List<ClassPlanDTO.Get> classPlans) {
        this.classPlans = classPlans;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_class_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassPlanDTO.Get classPlan = classPlans.get(position);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(classPlan);
            }
        });
        holder.programName.setText(classPlan.getGradPlan().getProgramName());
        holder.courses.setText(String.format("%d courses", classPlan.getClasses().size()));
    }

    @Override
    public int getItemCount() {
        return classPlans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView programName, credits, courses;

        ViewHolder(View itemView) {
            super(itemView);
            programName = itemView.findViewById(R.id.program_name);
            credits = itemView.findViewById(R.id.credits);
            courses = itemView.findViewById(R.id.courses);
        }
    }
}
