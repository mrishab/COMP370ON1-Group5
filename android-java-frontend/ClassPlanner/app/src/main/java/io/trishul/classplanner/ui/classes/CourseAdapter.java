package io.trishul.classplanner.ui.classes;

import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import java.util.List;
import java.util.stream.Collectors;

import io.trishul.classplanner.R;
import io.trishul.classplanner.network.dtos.CourseDTO;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private final List<CourseDTO> courses;

    public CourseAdapter(List<CourseDTO> courses) {
        this.courses = courses;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        CourseDTO course = courses.get(position);
        holder.bind(course);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseCodeView;
        private final TextView courseTitleView;
        private final Chip sectionChip;
        private final Chip creditsChip;
        private final Chip methodChip;
        private final TextView instructorView;
        private final TextView roomView;
        private final TextView scheduleView;
        private final ImageView expandIcon;
        private final LinearLayout detailsLayout;
        private final View headerLayout;
        private boolean isExpanded = false;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseCodeView = itemView.findViewById(R.id.tv_course_code);
            courseTitleView = itemView.findViewById(R.id.tv_course_title);
            sectionChip = itemView.findViewById(R.id.chip_section);
            creditsChip = itemView.findViewById(R.id.chip_credits);
            methodChip = itemView.findViewById(R.id.chip_method);
            instructorView = itemView.findViewById(R.id.tv_instructor);
            roomView = itemView.findViewById(R.id.tv_room);
            scheduleView = itemView.findViewById(R.id.tv_schedule);
            expandIcon = itemView.findViewById(R.id.expand_icon);
            detailsLayout = itemView.findViewById(R.id.details_layout);
            headerLayout = itemView.findViewById(R.id.header_layout);

            headerLayout.setOnClickListener(v -> toggleExpand());
        }

        private void toggleExpand() {
            isExpanded = !isExpanded;
            detailsLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            ObjectAnimator.ofFloat(expandIcon, "rotation", isExpanded ? 180f : 0f).start();
        }

        public void bind(CourseDTO course) {
            courseCodeView.setText(String.format("%s %s", course.getSubject(), course.getNumber()));
            courseTitleView.setText(course.getTitle());
            
            CourseDTO.ClassDetailDTO detail = course.getClassDetail();
            if (detail != null) {
                sectionChip.setText("Section " + detail.getSection());
                creditsChip.setText(detail.getCredits() + " Credits");
                methodChip.setText(detail.getMethod());
                
                instructorView.setText(detail.getInstructor());
                roomView.setText(detail.getRoom());

                if (detail.getSchedule() != null) {
                    String schedule = detail.getSchedule().stream()
                        .map(s -> String.format("%s %s-%s", 
                            s.getDay(), s.getStartTime(), s.getEndTime()))
                        .collect(Collectors.joining("\n"));
                    scheduleView.setText(schedule);
                }
            }
        }
    }
}
