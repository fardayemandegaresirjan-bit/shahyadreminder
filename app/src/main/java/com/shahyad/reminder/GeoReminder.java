package com.shahyad.reminder;

import android.content.Context;
import android.location.Location;

public class GeoReminder {
    
    private Context context;
    
    public GeoReminder(Context context) {
        this.context = context;
    }
    
    public void setupLocationBasedReminder(TaskModel task, double latitude, double longitude, float radius) {
        // Placeholder for Geo-aware reminder logic
    }
    
    public boolean isWithinTargetLocation(Location currentLocation, double targetLat, double targetLng, float radius) {
        float[] distance = new float[1];
        Location.distanceBetween(
            currentLocation.getLatitude(), 
            currentLocation.getLongitude(),
            targetLat, 
            targetLng, 
            distance
        );
        
        return distance[0] <= radius;
    }
}
