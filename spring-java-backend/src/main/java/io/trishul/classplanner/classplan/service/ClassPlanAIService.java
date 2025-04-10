package io.trishul.classplanner.classplan.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import io.trishul.classplanner.availability.model.Availability;
import io.trishul.classplanner.classplan.model.ClassPlan;

@Service
public class ClassPlanAIService {
  @Autowired
  private ChatClient chatClient;

  @Value("classpath:timetable.txt")
  private Resource timetableResource;

  public String generateClassPlan(ClassPlan classPlan, String gradPlanDetails) {
        try {
            String timetable = StreamUtils.copyToString(timetableResource.getInputStream(), StandardCharsets.UTF_8);
            String availabilityString = formatAvailability(classPlan.getAvailability());

            String userPrompt = String.format("""
                Generate a class plan based on the following constraints:

                STUDENT PROGRESS AND REQUIREMENTS:
                %s

                PREFERENCES:
                - Desired number of courses: %d
                - Desired class distribution: %s
                - Burden capacity: %s

                STUDENT AVAILABILITY:
                The student is ONLY available at these hours each day. Do not assign classes outside of these exact windows:

                %s

                TIMETABLE:
                %s

                Instructions:
                1. Only include courses the student is eligible for based on their degree audit.
                2. All selected class times MUST fit entirely within the provided availability — NO EXCEPTIONS.
                3. There must be NO time conflicts between classes.
                4. Match the preferred class distribution (%s) and burden capacity (%s).
                5. Try to match the desired number of courses (%d) strictly.

                Return this response in EXACTLY this JSON format (no extra text):
                {
                "courses": [
                    {
                    "subject": "",
                    "number": 0,
                    "title": "",
                    "classDetail": {
                        "section": "",
                        "instructor": "",
                        "room": "",
                        "method": "",
                        "crn": "" ,
                        "credits": number,
                        "schedule": [
                        {
                            "day": "",
                            "startTime": "",
                            "endTime": ""
                        }
                        ]
                    }
                    }
                ]
                }
                """,
                gradPlanDetails,
                classPlan.getDesiredNumOfCourses(),
                classPlan.getClassDistribution(),
                classPlan.getBurdenCapacity(),
                availabilityString,
                timetable,
                classPlan.getClassDistribution(),
                classPlan.getBurdenCapacity(),
                classPlan.getDesiredNumOfCourses()
            );

            Message systemMessage = new SystemMessage("""
                You are a class schedule optimization assistant. Generate a valid class schedule that meets all constraints.
                Return only the JSON response with no additional explanation or text.
                Ensure all courses exist in the provided timetable and check for time conflicts.
                """);

            Message userMessage = new UserMessage(userPrompt);
            Prompt prompt = new Prompt(systemMessage, userMessage);
            
            return chatClient.prompt(prompt).call().content();

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate class plan", e);
        }
    }

  private String formatAvailability(Availability availability) {
    return availability.getDays().stream().map(day -> {
      String dayKey = day.getDay().substring(0, 1); // Get first letter of day
      String availableHours
          = day.getHours().stream().filter(hour -> Boolean.TRUE.equals(hour.getIsAvailable()))
              .map(hour -> String.format("\"%02d:00\"", hour.getHourOfTheDay()))
              .collect(Collectors.joining(", "));
      return String.format("\"%s\": [%s]", dayKey, availableHours);
    }).collect(Collectors.joining(",\n", "{\n", "\n}"));
  }
}
