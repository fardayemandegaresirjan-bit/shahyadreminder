package com.shahyad.reminder;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    private static final String CODE_ZERO_TOKEN = "29b8fa19-8714-4c5a-b623-d4b7c03c1a9b";
    private ReminderCore reminderCore;
    private AIEngine aiEngine;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeCoreModules();
        startNightReviewScheduler();
    }
    
    private void initializeCoreModules() {
        dbHelper = new DatabaseHelper(this);
        aiEngine = new AIEngine(this);
        reminderCore = new ReminderCore(this, dbHelper, aiEngine);
    }
    
    private void startNightReviewScheduler() {
        NightReview nightReview = new NightReview(this);
        nightReview.scheduleNightlyReview();
    }
}
