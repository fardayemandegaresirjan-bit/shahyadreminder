package com.shahyad.reminder;

import java.util.HashMap;
import java.util.Map;

public class SuggestionCore {
    
    public Map<String, Object> generateSuggestions(TaskModel task) {
        Map<String, Object> suggestions = new HashMap<>();
        
        long now = System.currentTimeMillis();
        long timeLeft = task.getDeadline().getTime() - now;
        long hoursLeft = timeLeft / (1000 * 60 * 60);
        
        if (hoursLeft <= 1) {
            suggestions.put("urgency_level", "CRITICAL");
            suggestions.put("suggested_action", "Complete immediately");
            suggestions.put("estimated_time", "15-30 minutes");
        } else if (hoursLeft <= 3) {
            suggestions.put("urgency_level", "HIGH");
            suggestions.put("suggested_action", "Start within 30 minutes");
            suggestions.put("estimated_time", "45-60 minutes");
        } else {
            suggestions.put("urgency_level", "NORMAL");
            suggestions.put("suggested_action", "Plan for later today");
            suggestions.put("estimated_time", "As scheduled");
        }
        
        return suggestions;
    }
}
