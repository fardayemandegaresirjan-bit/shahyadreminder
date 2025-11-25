package com.shahyad.reminder;

public class FullScopeObserver {
    
    public boolean checkConsistency(TaskModel task) {
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            return false;
        }
        
        if (task.getDeadline() == null) {
            return false;
        }
        
        if (task.getDeadline().getTime() < System.currentTimeMillis()) {
            return false;
        }
        
        if (task.getPriority() < 1 || task.getPriority() > 10) {
            return false;
        }
        
        return true;
    }
    
    public void logInconsistency(TaskModel task, String reason) {
        System.err.println("Task inconsistency detected: " + reason);
        System.err.println("Task: " + task.getTitle());
    }
}
