package com.shahyad.reminder;

public class PriorityEngine {
    
    private static final float URGENCY_WEIGHT = 0.5f;
    private static final float SIGNIFICANCE_WEIGHT = 0.3f;
    private static final float DISRUPTION_WEIGHT = 0.2f;
    
    public float calculateDynamicPriority(TaskModel task) {
        float urgency = calculateUrgency(task.getDeadline().getTime());
        float significance = task.getPriority() / 10.0f;
        float disruption = estimateDisruptionPotential(task);
        
        return (urgency * URGENCY_WEIGHT) + 
               (significance * SIGNIFICANCE_WEIGHT) + 
               (disruption * DISRUPTION_WEIGHT);
    }
    
    private float calculateUrgency(long deadlineMillis) {
        long now = System.currentTimeMillis();
        long timeLeft = deadlineMillis - now;
        long hoursLeft = timeLeft / (1000 * 60 * 60);
        
        if (hoursLeft <= 1) return 1.0f;
        if (hoursLeft <= 3) return 0.8f;
        if (hoursLeft <= 24) return 0.5f;
        return 0.2f;
    }
    
    private float estimateDisruptionPotential(TaskModel task) {
        return task.getPriority() > 7 ? 0.8f : 0.3f;
    }
    
    public double calculateDeltaT(int priority, long duration) {
        return 1.0 - (priority / 10.0) * 0.3;
    }
}
