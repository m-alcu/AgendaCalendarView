package com.github.tibolte.sample;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarManager;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.github.tibolte.agendacalendarview.models.IDayItem;
import com.github.tibolte.agendacalendarview.models.IWeekItem;
import com.github.tibolte.agendacalendarview.models.WeekItem;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CalendarPickerController {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.activity_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.agenda_calendar_view)
    AgendaCalendarView mAgendaCalendarView;

    // region Lifecycle methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        List<CalendarEvent> eventList = new ArrayList<>();
        mockList(eventList);
        // Sync way
        /*
        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);
        mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());
        */
        //Async way


        //////// This can be done once in another thread
        CalendarManager calendarManager = CalendarManager.getInstance(getApplicationContext());
        calendarManager.buildCal(minDate, maxDate, Locale.getDefault(), new DayItem(), new WeekItem());
        calendarManager.loadEvents(eventList, new BaseCalendarEvent());
        ////////

        List<CalendarEvent> readyEvents = calendarManager.getEvents();
        List<IDayItem> readyDays = calendarManager.getDays();
        List<IWeekItem> readyWeeks = calendarManager.getWeeks();
        mAgendaCalendarView.init(Locale.getDefault(), readyWeeks,readyDays,readyEvents,this);
        mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());
        mAgendaCalendarView.enableCalenderView(true);

    }

    // endregion

    // region Interface - CalendarPickerController

    @Override
    public void onDaySelected(IDayItem dayItem) {
        Log.d(LOG_TAG, String.format("Selected day: %s", dayItem));
    }

    @Override
    public void onEventSelected(CalendarEvent event) {
        Log.d(LOG_TAG, String.format("Selected event: %s", event));
    }

    @Override
    public void onScrollToDate(Calendar calendar) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())+" "+calendar.get(Calendar.YEAR));
        }
    }

    // endregion

    // region Private Methods

    private void mockList(List<CalendarEvent> eventList) {
        Calendar startTime1 = Calendar.getInstance();
        Calendar endTime1 = Calendar.getInstance();
        endTime1.add(Calendar.MONTH, 1);
        BaseCalendarEvent event1 = new BaseCalendarEvent("Revisión requerimientos", "Diseño App", "Despacho 1",
                ContextCompat.getColor(this, R.color.theme_event_pending), startTime1, endTime1, true);
        eventList.add(event1);

        Calendar startTime2 = Calendar.getInstance();
        startTime2.add(Calendar.DAY_OF_YEAR, 1);
        Calendar endTime2 = Calendar.getInstance();
        endTime2.add(Calendar.DAY_OF_YEAR, 3);
        BaseCalendarEvent event2 = new BaseCalendarEvent("Arquitectura, reunión seguimiento", "Reunión periodica importante", "Sala 2B",
                ContextCompat.getColor(this, R.color.theme_event_pending), startTime2, endTime2, true);
        eventList.add(event2);

        Calendar startTime3 = Calendar.getInstance();
        Calendar endTime3 = Calendar.getInstance();
        startTime3.set(Calendar.HOUR_OF_DAY, 14);
        startTime3.set(Calendar.MINUTE, 0);
        endTime3.set(Calendar.HOUR_OF_DAY, 15);
        endTime3.set(Calendar.MINUTE, 0);
        BaseCalendarEvent event3 = new BaseCalendarEvent("14:00 - 15:00 Reunión de coordinación AGN", "i", "Despacho 2",
                ContextCompat.getColor(this, R.color.theme_event_confirmed), startTime3, endTime3, false);
        eventList.add(event3);

        Calendar startTime4 = Calendar.getInstance();
        Calendar endTime4 = Calendar.getInstance();
        startTime4.set(Calendar.HOUR_OF_DAY, 16);
        startTime4.set(Calendar.MINUTE, 0);
        endTime4.set(Calendar.HOUR_OF_DAY, 17);
        endTime4.set(Calendar.MINUTE, 0);
        BaseCalendarEvent event4 = new BaseCalendarEvent("16:00 - 17:00 Reunión de coordinación AGN 2", "i", "Despacho 3",
                ContextCompat.getColor(this, R.color.theme_event_confirmed), startTime4, endTime4, false);
        eventList.add(event4);

        Calendar startTime5 = Calendar.getInstance();
        Calendar endTime5 = Calendar.getInstance();
        startTime5.set(Calendar.HOUR_OF_DAY, 18);
        startTime5.set(Calendar.MINUTE, 0);
        endTime5.set(Calendar.HOUR_OF_DAY, 19);
        endTime5.set(Calendar.MINUTE, 0);
        BaseCalendarEvent event5 = new BaseCalendarEvent("16:00 - 17:00 Reunión de coordinación AGN 3", "i", "Despacho 3",
                ContextCompat.getColor(this, R.color.theme_event_confirmed), startTime5, endTime5, false);
        eventList.add(event5);
    }

    // endregion
}
