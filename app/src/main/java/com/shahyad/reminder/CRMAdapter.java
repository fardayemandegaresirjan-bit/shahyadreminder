package com.shahyad.reminder;

import java.util.HashMap;
import java.util.Map;

public class CRMAdapter {
    
    private Map<String, Integer> behaviorMap;
    
    public CRMAdapter() {
        this.behaviorMap = new HashMap<>();
    }
    
    public void recordBehavior(String userId, String action) {
        String key = userId + "_" + action;
        behaviorMap.put(key, behaviorMap.getOrDefault(key, 0) + 1);
    }
    
    public int getBehaviorCount(String userId, String action) {
        String key = userId + "_" + action;
        return behaviorMap.getOrDefault(key, 0);
    }
    
    public Map<String, Integer> getUserProfile(String userId) {
        Map<String, Integer> profile = new HashMap<>();
        for (Map.Entry<String, Integer> entry : behaviorMap.entrySet()) {
            if (entry.getKey().startsWith(userId)) {
                profile.put(entry.getKey(), entry.getValue());
            }
        }
        return profile;
    }
}
