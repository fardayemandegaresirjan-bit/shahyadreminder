package com.shahyad.reminder;

import android.content.Context;
import java.util.Date;

public class ReminderCore {
    
    private Context context;
    private DatabaseHelper dbHelper;
    private AIEngine aiEngine;
    private PriorityEngine priorityEngine;
    private AlertSystem alertSystem;
    
    public ReminderCore(Context context, DatabaseHelper dbHelper, AIEngine aiEngine) {
        this.context = context;
        this.dbHelper = dbHelper;
        this.aiEngine = aiEngine;
        this.priorityEngine = new PriorityEngine();
        this.alertSystem = new AlertSystem(context);
    }
    
    public long createReminder(String title, String description, Date deadline, int priority) {
        TaskModel task = new TaskModel();
        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(deadline);
        task.setPriority(priority);
        
        float calculatedPriority = priorityEngine.calculateDynamicPriority(task);
        task.setCalculatedPriority(calculatedPriority);
        
        long taskId = dbHelper.insertTask(task);
        alertSystem.scheduleMultiPhaseAlerts(task);
        
        return taskId;
    }
    
    public double calculateReminderTime(Date deadline, long duration, int priority) {
        long now = System.currentTimeMillis();
        long deadlineTime = deadline.getTime();
        double deltaT = priorityEngine.calculateDeltaT(priority, duration);
        
        return (deadlineTime - now) * deltaT;
    }
}
