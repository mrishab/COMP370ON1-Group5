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
          Analyze this graduation plan image and provide the information in the specified JSON format.
          Image data: data:image/png;base64,%s
          """, base64Image));
        Message systemMessage = new SystemMessage("""
            You are a graduation plan analyzer. Analyze the image and extract academic information into this JSON structure:
            {
              "programName": "",
              "majorName": "",
              "creditsCompleted": number,
              "creditsRequired": number,
              "cgpa": number,
              "calendarTermSemester": "",
              "calendarTermYear": number
            }
            Ensure all numeric values are properly formatted and term information is current.
            """);
        Prompt prompt = new Prompt(systemMessage, userMessage);
        return chatClient.prompt(prompt).call().content();
    }

}
