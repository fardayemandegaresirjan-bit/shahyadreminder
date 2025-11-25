package com.shahyad.reminder;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;

public class AIEngine {
    
    private Context context;
    private CRMAdapter crmAdapter;
    private SuggestionCore suggestionCore;
    private FullScopeObserver observer;
    
    public AIEngine(Context context) {
        this.context = context;
        this.crmAdapter = new CRMAdapter();
        this.suggestionCore = new SuggestionCore();
        this.observer = new FullScopeObserver();
    }
    
    public void analyzeUserBehavior(String userId, String action) {
        crmAdapter.recordBehavior(userId, action);
    }
    
    public Map<String, Object> getSuggestions(TaskModel task) {
        return suggestionCore.generateSuggestions(task);
    }
    
    public boolean validateTaskConsistency(TaskModel task) {
        return observer.checkConsistency(task);
    }
}
