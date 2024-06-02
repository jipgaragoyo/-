package com.example.myapplicationnnnn;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CalendarAdapter calendarAdapter;
    private List<CalendarDay> calendarDayList;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private int selectedPosition = -1;
    private Calendar currentCalendar;
    private TextView textViewYearMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewYearMonth = findViewById(R.id.textViewYearMonth);
        recyclerView = findViewById(R.id.recyclerView);
        Button prevMonthButton = findViewById(R.id.prevMonthButton);
        Button nextMonthButton = findViewById(R.id.nextMonthButton);

        currentCalendar = Calendar.getInstance();
        calendarDayList = new ArrayList<>();
        calendarAdapter = new CalendarAdapter(calendarDayList, position -> onCalendarDayClick(position));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        recyclerView.setAdapter(calendarAdapter);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && selectedPosition != -1) {
                Uri imageUri = result.getData().getData();
                calendarDayList.get(selectedPosition).setImageUri(imageUri);
                calendarAdapter.notifyItemChanged(selectedPosition);
            }
        });

        textViewYearMonth.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                currentCalendar.set(Calendar.YEAR, year);
                currentCalendar.set(Calendar.MONTH, month);
                updateCalendar();
            }, currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        prevMonthButton.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, -1);
            updateCalendar();
        });

        nextMonthButton.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, 1);
            updateCalendar();
        });

        updateCalendar();
    }

    private void updateCalendar() {
        calendarDayList.clear();
        int year = currentCalendar.get(Calendar.YEAR);
        int month = currentCalendar.get(Calendar.MONTH);
        textViewYearMonth.setText(String.format("%d년 %d월", year, month + 1));

        Calendar calendar = (Calendar) currentCalendar.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -firstDayOfWeek);

        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int weeksInMonth = (int) Math.ceil((float) (daysInMonth + firstDayOfWeek) / 7);

        for (int i = 0; i < weeksInMonth * 7; i++) {
            calendarDayList.add(new CalendarDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        calendarAdapter.notifyDataSetChanged();
    }

    private void onCalendarDayClick(int position) {
        selectedPosition = position;
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(intent);
    }
}
