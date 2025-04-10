package io.trishul.classplanner.gradplan.service;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.trishul.classplanner.gradplan.model.GradPlan;

@Service
public class GradPlanAIResponseProcessor {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public void updateGradPlanFromAIResponse(GradPlan plan, String aiResponse) {
    try {
      JsonNode json = objectMapper.readTree(aiResponse);

      // Update basic information
      setIfPresent(json, "programName", value -> plan.setProgramName(value.asText()));
      setIfPresent(json, "majorName", value -> plan.setMajorName(value.asText()));
      setIfPresent(json, "programLevel", value -> plan.setProgramLevel(value.asText()));

      // Update numeric values
      setIfPresent(json, "creditsCompleted", value -> plan.setCreditsCompleted(value.asLong()));
      setIfPresent(json, "creditsRequired", value -> plan.setCreditsRequired(value.asLong()));
      setIfPresent(json, "cgpa", value -> plan.setCgpa(value.asDouble()));

    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to process AI response", e);
    }
  }

  private void setIfPresent(JsonNode json, String field, JsonNodeConsumer consumer) {
    if (json.has(field) && !json.get(field).isNull()) {
      consumer.accept(json.get(field));
    }
  }

  @FunctionalInterface
  private interface JsonNodeConsumer {
    void accept(JsonNode value);
  }
}
