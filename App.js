import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, TouchableOpacity, FlatList, Alert, Platform } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import * as Notifications from 'expo-notifications';

Notifications.setNotificationHandler({
  handleNotification: async () => ({
    shouldShowAlert: true,
    shouldPlaySound: true,
    shouldSetBadge: false,
  }),
});

export default function App() {
  const [title, setTitle] = useState('');
  const [time, setTime] = useState('');
  const [reminders, setReminders] = useState([]);

  useEffect(() => {
    loadReminders();
    requestPermissions();
  }, []);

  const requestPermissions = async () => {
    const { status } = await Notifications.requestPermissionsAsync();
    if (status !== 'granted') {
      Alert.alert('خطا', 'دسترسی به نوتیفیکیشن داده نشد!');
    }
  };

  const loadReminders = async () => {
    try {
      const stored = await AsyncStorage.getItem('reminders');
      if (stored) setReminders(JSON.parse(stored));
    } catch (error) {
      console.error(error);
    }
  };

  const saveReminders = async (newReminders) => {
    try {
      await AsyncStorage.setItem('reminders', JSON.stringify(newReminders));
      setReminders(newReminders);
    } catch (error) {
      console.error(error);
    }
  };

  const addReminder = async () => {
    if (!title.trim() || !time.trim()) {
      Alert.alert('خطا', 'لطفاً عنوان و زمان را وارد کنید');
      return;
    }

    const [hours, minutes] = time.split(':').map(Number);
    if (isNaN(hours) || isNaN(minutes) || hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
      Alert.alert('خطا', 'زمان باید به فرمت HH:MM باشد (مثلاً 14:30)');
      return;
    }

    const now = new Date();
    const reminderTime = new Date(now.getFullYear(), now.getMonth(), now.getDate(), hours, minutes);
    
    if (reminderTime <= now) {
      reminderTime.setDate(reminderTime.getDate() + 1);
    }

    const trigger = reminderTime.getTime();

    await Notifications.scheduleNotificationAsync({
      content: {
        title: '🔔 یادآوری',
        body: title,
        sound: true,
      },
      trigger: { date: trigger },
    });

    const newReminder = {
      id: Date.now().toString(),
      title,
      time,
      timestamp: trigger,
    };

    saveReminders([...reminders, newReminder]);
    setTitle('');
    setTime('');
    Alert.alert('موفق', 'یادآوری اضافه شد!');
  };

  const deleteReminder = (id) => {
    Alert.alert(
      'حذف یادآوری',
      'آیا مطمئن هستید؟',
      [
        { text: 'خیر', style: 'cancel' },
        {
          text: 'بله',
          onPress: () => {
            const updated = reminders.filter(r => r.id !== id);
            saveReminders(updated);
          }
        }
      ]
    );
  };

  return (
    <View style={styles.container}>
      <Text style={styles.header}>📝 یادآور</Text>
      
      <View style={styles.inputContainer}>
        <TextInput
          style={styles.input}
          placeholder="عنوان یادآوری"
          value={title}
          onChangeText={setTitle}
        />
        <TextInput
          style={styles.input}
          placeholder="زمان (مثال: 14:30)"
          value={time}
          onChangeText={setTime}
          keyboardType="numbers-and-punctuation"
        />
        <TouchableOpacity style={styles.addButton} onPress={addReminder}>
          <Text style={styles.addButtonText}>➕ افزودن</Text>
        </TouchableOpacity>
      </View>

      <FlatList
        data={reminders}
        keyExtractor={item => item.id}
        renderItem={({ item }) => (
          <View style={styles.reminderItem}>
            <View style={styles.reminderInfo}>
              <Text style={styles.reminderTitle}>{item.title}</Text>
              <Text style={styles.reminderTime}>🕐 {item.time}</Text>
            </View>
            <TouchableOpacity
              style={styles.deleteButton}
              onPress={() => deleteReminder(item.id)}
            >
              <Text style={styles.deleteButtonText}>❌</Text>
            </TouchableOpacity>
          </View>
        )}
        ListEmptyComponent={
          <Text style={styles.emptyText}>هنوز یادآوری اضافه نکرده‌اید</Text>
        }
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
    paddingTop: 50,
    paddingHorizontal: 20,
  },
  header: {
    fontSize: 32,
    fontWeight: 'bold',
    textAlign: 'center',
    marginBottom: 20,
    color: '#333',
  },
  inputContainer: {
    backgroundColor: 'white',
    padding: 15,
    borderRadius: 10,
    marginBottom: 20,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  input: {
    borderWidth: 1,
    borderColor: '#ddd',
    borderRadius: 8,
    padding: 12,
    marginBottom: 10,
    fontSize: 16,
  },
  addButton: {
    backgroundColor: '#4CAF50',
    padding: 15,
    borderRadius: 8,
    alignItems: 'center',
  },
  addButtonText: {
    color: 'white',
    fontSize: 18,
    fontWeight: 'bold',
  },
  reminderItem: {
    flexDirection: 'row',
    backgroundColor: 'white',
    padding: 15,
    borderRadius: 10,
    marginBottom: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.1,
    shadowRadius: 2,
    elevation: 2,
  },
  reminderInfo: {
    flex: 1,
  },
  reminderTitle: {
    fontSize: 18,
    fontWeight: '600',
    color: '#333',
    marginBottom: 5,
  },
  reminderTime: {
    fontSize: 14,
    color: '#666',
  },
  deleteButton: {
    justifyContent: 'center',
    paddingHorizontal: 10,
  },
  deleteButtonText: {
    fontSize: 24,
  },
  emptyText: {
    textAlign: 'center',
    fontSize: 16,
    color: '#999',
    marginTop: 50,
  },
});
