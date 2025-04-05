package io.trishul.classplanner.ui.classplans;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import io.trishul.classplanner.R;
import io.trishul.classplanner.api.models.ClassPlan;

public class ClassPlansAdapter extends RecyclerView.Adapter<ClassPlansAdapter.ViewHolder> {
    private final List<ClassPlan> classPlans;

    public ClassPlansAdapter(List<ClassPlan> classPlans) {
        this.classPlans = classPlans;
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
        ClassPlan classPlan = classPlans.get(position);
        holder.programName.setText(classPlan.getProgramName());
        holder.credits.setText(String.format("%d credits", classPlan.getTotalCredits()));
        holder.courses.setText(String.format("%d courses", classPlan.getTotalCourses()));
        holder.term.setText(classPlan.getTerm());
        holder.year.setText(String.valueOf(classPlan.getYear()));
    }

    @Override
    public int getItemCount() {
        return classPlans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView programName, credits, courses, term, year;

        ViewHolder(View itemView) {
            super(itemView);
            programName = itemView.findViewById(R.id.program_name);
            credits = itemView.findViewById(R.id.credits);
            courses = itemView.findViewById(R.id.courses);
            term = itemView.findViewById(R.id.term);
            year = itemView.findViewById(R.id.year);
        }
    }
}
