package com.github.tibolte.agendacalendarview.agenda;

import com.github.tibolte.agendacalendarview.CalendarManager;
import com.github.tibolte.agendacalendarview.R;
import com.github.tibolte.agendacalendarview.utils.DateHelper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Header view for the StickyHeaderListView of the agenda view
 */
public class AgendaHeaderView extends LinearLayout {

    public static AgendaHeaderView inflate(ViewGroup parent) {
        return (AgendaHeaderView) LayoutInflater.from(parent.getContext()).inflate(R.layout.view_agenda_header, parent, false);
    }

    // region Constructors

    public AgendaHeaderView(Context context) {
        super(context);
    }

    public AgendaHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AgendaHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // endregion

    // region Public methods

    public void setDay(Calendar day, int currentDayTextColor) {
        TextView txtDayOfWeek = (TextView) findViewById(R.id.view_agenda_day_of_week);

        Calendar today = CalendarManager.getInstance().getToday();

        SimpleDateFormat dayWeekFormatter =  new SimpleDateFormat("EEEEEEEEE dd", CalendarManager.getInstance().getLocale());;

        txtDayOfWeek.setTextColor(getResources().getColor(R.color.calendar_text_selected));

        if (DateHelper.sameDate(day, today)) {
            txtDayOfWeek.setTextColor(getResources().getColor(R.color.theme_light_primary));
        }

        txtDayOfWeek.setText(dayWeekFormatter.format(day.getTime()));
    }

    // endregion
}
