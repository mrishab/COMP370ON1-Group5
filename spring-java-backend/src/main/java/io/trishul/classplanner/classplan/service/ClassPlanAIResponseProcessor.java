package io.trishul.classplanner.classplan.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.trishul.classplanner.classdetail.model.ClassDetail;
import io.trishul.classplanner.classdetail.model.Course;
import io.trishul.classplanner.classplan.model.ClassPlan;
import io.trishul.classplanner.classschedule.model.ClassSchedule;

@Service
public class ClassPlanAIResponseProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void updateClassPlanFromAIResponse(ClassPlan classPlan, String aiResponse) {
        try {
            JsonNode json = objectMapper.readTree(aiResponse);
            List<Course> courses = new ArrayList<>();

            for (JsonNode courseNode : json.get("courses")) {
                Course course = new Course();
                course.setTitle(courseNode.get("title").asText());
                course.setSubject(courseNode.get("subject").asText());
                course.setNumber(courseNode.get("number").asInt());

                ClassDetail classDetail = new ClassDetail();
                JsonNode classDetailNode = courseNode.get("classDetail");
                classDetail.setSection(classDetailNode.get("section").asText());
                classDetail.setInstructor(classDetailNode.get("instructor").asText());
                classDetail.setRoom(classDetailNode.get("room").asText());
                classDetail.setMethod(classDetailNode.get("method").asText());

                List<ClassSchedule> schedules = new ArrayList<>();
                for (JsonNode scheduleNode : courseNode.get("classDetail").get("schedule")) {
                    ClassSchedule schedule = new ClassSchedule();
                    schedule.setDay(scheduleNode.get("day").asText());
                    schedule.setStartTime(scheduleNode.get("startTime").asText());
                    schedule.setEndTime(scheduleNode.get("endTime").asText());
                    schedule.setClassDetail(classDetail);
                    schedules.add(schedule);
                }
                classDetail.setSchedule(schedules);
                course.setClassDetail(classDetail);
                course.setClassPlan(classPlan);
                courses.add(course);
            }
            classPlan.setClasses(courses);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to process AI response", e);
        }
    }
}
