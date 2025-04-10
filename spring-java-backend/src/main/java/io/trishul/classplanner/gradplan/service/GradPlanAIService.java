package io.trishul.classplanner.gradplan.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradPlanAIService {
    @Autowired    
    private ChatClient chatClient;
    public String processImageContent(String base64Image) {
        Message userMessage = new UserMessage(String.format("""
          Analyze this graduation plan image and extract only the requested information in the exact JSON format specified.
          Image data: data:image/png;base64,%s
          """, base64Image));
        Message systemMessage = new SystemMessage("""
            You are a graduation plan analyzer. Extract ONLY the following information into the exact JSON structure shown.
            Do not include any explanation, commentary, or additional fields. Return only valid JSON matching this structure:

            {
              "programName": "",            // Extract from Degree
              "majorName": "",              // Extract from Major
              "creditsCompleted": 0,        // Extract from Classification
              "creditsRequired": 0,         // Extract from Classification
              "cgpa": 0.0,                  // Extract from Institutional CGPA
              "programLevel": "",           // Extract from Level
              "details": ""                 // Summarize all program requirements. Use the following instructions:
                                            //
                                            // - Include total credits required and completed, upper-level credits status,
                                            //   institutional and upper-level GPA requirements, and their current status.
                                            // - Go through all general education and program requirement sections.
                                            // - Use the checkmark legend: classify each requirement as “Completed”, “In Progress”, or “Pending”.
                                            // - For each course group (e.g., STATISTICS REQUIREMENT, COMMUNICATIONS), list courses taken
                                            //   and clearly mention what’s still required.
                                            // - Preserve as much raw detail as possible (course names, grades, credit values, terms, etc.).
                                            // - This entire section will be reused in a subsequent prompt to GPT. Be thorough and dense with data.
            }
            All string values must be valid strings, all number values must be valid numbers without quotes.
            """);
        Prompt prompt = new Prompt(systemMessage, userMessage);
        return chatClient.prompt(prompt).call().content();
    }

}
