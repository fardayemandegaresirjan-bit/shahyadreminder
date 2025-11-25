package com.shahyad.reminder;

import java.util.Date;

public class TaskModel {
    
    private long id;
    private String title;
    private String description;
    private Date deadline;
    private int priority;
    private float calculatedPriority;
    private String status;
    private long createdAt;
    
    public TaskModel() {
        this.createdAt = System.currentTimeMillis();
        this.status = "PENDING";
    }
    
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }
    
    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
    
    public float getCalculatedPriority() { return calculatedPriority; }
    public void setCalculatedPriority(float calculatedPriority) { 
        this.calculatedPriority = calculatedPriority; 
    }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
cat > app/src/main/java/com/shahyad/reminder/AlertSystem.java << 'EOF'
package com.shahyad.reminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class AlertSystem {
    
    private Context context;
    private static final String CHANNEL_ID = "shahyad_reminders";
    
    public AlertSystem(Context context) {
        this.context = context;
        createNotificationChannel();
    }
    
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Shahyad Reminders";
            String description = "Channel for Shahyad reminder notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    
    public void scheduleMultiPhaseAlerts(TaskModel task) {
        // Phase 1: Soft Nudge (3 hours before)
        // Phase 2: Priority Signal (1 hour before)
        // Phase 3: Critical Override (15 minutes before)
    }
    
    public void sendNotification(String title, String message, int priority) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(priority)
                .setAutoCancel(true);
        
        NotificationManager notificationManager = 
            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
