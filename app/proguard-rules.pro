# Add project specific ProGuard rules here.
-keep class com.shahyad.reminder.** { *; }
-keepclassmembers class * {
    public <init>(...);
}
-dontwarn androidx.**
