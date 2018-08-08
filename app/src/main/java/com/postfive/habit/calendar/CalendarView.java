package com.postfive.habit.calendar;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import com.postfive.habit.R;
/**
 * Created by a7med on 28/06/2015.
 */
public class CalendarView extends LinearLayout
{
    // for logging
    private static final String LOGTAG = "Calendar View";

    // how many days to show, defaults to six weeks, 42 days
//    private static final int DAYS_COUNT = 42;
//    private static final int DAYS_COUNT = 35;
    private int DAYS_COUNT = 35;

    // default date format
//	private static final String DATE_FORMAT = "MMM yyyy";
    private static final String DATE_FORMAT = "yyyy. MM";

    // date format
    private String dateFormat;

    // current displayed month
    private Calendar currentDate = Calendar.getInstance();

    //event handling
    private EventHandler eventHandler = null;

    // internal components
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;

    // seasons' rainbow
    int[] rainbow = new int[] {
            R.color.summer,
            R.color.fall,
            R.color.winter,
            R.color.spring
    };

    // month-season association (northern hemisphere, sorry australia :)
    int[] monthSeason = new int[] {2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};

    public CalendarView(Context context)
    {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initControl(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context, AttributeSet attrs)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_calendar, this);

        loadDateFormat(attrs);
        assignUiElements();
        assignClickHandlers();

        updateCalendar();
    }

    private void loadDateFormat(AttributeSet attrs)
    {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try
        {
            // try to load provided date format, and fallback to default otherwise
            dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);

            dateFormat = null;
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        }
        finally
        {
            ta.recycle();
        }
    }
    private void assignUiElements()
    {
        // layout is inflated, assign local variables to components
        header = (LinearLayout)findViewById(R.id.calendar_header);
        btnPrev = (ImageView)findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView)findViewById(R.id.calendar_next_button);
        txtDate = (TextView)findViewById(R.id.calendar_date_display);
        grid = (GridView)findViewById(R.id.calendar_grid);
    }

    private void assignClickHandlers()
    {
        // add one month and refresh UI
        btnNext.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });

        // subtract one month and refresh UI
        btnPrev.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        // long-pressing a day
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id)
            {
                // handle long-press
                if (eventHandler == null)
                    return false;

                eventHandler.onDayLongPress((Date)view.getItemAtPosition(position));
                return true;
            }
        });
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar()
    {
        updateCalendar(null);
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar(HashSet<Date> events)
    {
        ArrayList<Date> cells = new ArrayList<>();
        Log.d("달력", "1 cel size " + cells.size());
        Calendar calendar = (Calendar)currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int month = calendar.get(Calendar.MONTH);
        int endDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 시작하는 주 선택
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        // 첫번째 나오는 날짜 set
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        Log.d("달력", "2 cel size " + cells.size());

        boolean go = false;
        // fill cells 달력 칸
        while (cells.size() < DAYS_COUNT)
        {
            Log.d("달력", "3 cel size " + cells.size());
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            // 마지막 날짜 지나갔는지 확인 하면 되네
            if(!go && endDay == calendar.get(Calendar.DAY_OF_MONTH) && month == calendar.get(Calendar.MONTH)){
                Log.d("달력", " 마지막 날짜 지나감 cel size "+calendar.get(Calendar.DAY_OF_MONTH));
                go = true;
            }

            // 마지막에 마지막 날짜 지나갔는지 확인.
            /*if(cells.size() == DAYS_COUNT -1 && !go){
                DAYS_COUNT +=7;
                go = true;
            }*/
        }

        DAYS_COUNT = 35;
        // update grid
        grid.setAdapter(new CalendarAdapter(getContext(), cells, events, currentDate));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        txtDate.setText(sdf.format(currentDate.getTime()));

        // set header color according to current season
        // 배경색 없음
//        int month = currentDate.get(Calendar.MONTH);
//		int season = monthSeason[month];
//		int color = rainbow[season];

//		header.setBackgroundColor(getResources().getColor(color));
    }

    private class CalendarAdapter extends ArrayAdapter<Date>
    {
        // days with events
        private HashSet<Date> eventDays;

        // for view inflation
        private LayoutInflater inflater;

        private Calendar currentCal;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays, Calendar currentCal)
        {
            super(context, R.layout.item_view_calendar, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
            this.currentCal = currentCal;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            // day in question
            Date date = getItem(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();

            // today
            Date today = new Date();

            // inflate item if it does not exist yet
            if (view == null)
                view = inflater.inflate(R.layout.item_view_calendar, parent, false);

            RelativeLayout rlItemViewCalendar = (RelativeLayout)view.findViewById(R.id.rlItemViewCalendar);
            TextView tvItemViewCalendar = (TextView)view.findViewById(R.id.tvItemViewCalendar);

            // if this day has an event, specify event image
            if (eventDays != null)
            {
                for (Date eventDate : eventDays)
                {
                    if (eventDate.getDate() == day &&
                            eventDate.getMonth() == month &&
                            eventDate.getYear() == year)
                    {
                        // mark this day for event
                        break;
                    }
                }
            }

            // clear styling
            tvItemViewCalendar.setTypeface(null, Typeface.NORMAL);
            tvItemViewCalendar.setTextColor(Color.BLACK);

            if (month != today.getMonth() || year != today.getYear())
            {
                // if this day is outside current month, grey it out
                tvItemViewCalendar.setTextColor(getResources().getColor(R.color.greyed_out));
            }
            else if (day == today.getDate())
            {
                // if it is today, set it to blue/bold
                tvItemViewCalendar.setTypeface(null, Typeface.BOLD);
                tvItemViewCalendar.setTextColor(getResources().getColor(R.color.today));

                rlItemViewCalendar.setBackgroundColor(getResources().getColor(R.color.darkOrange));
            }

            // set text
            tvItemViewCalendar.setText(String.valueOf(date.getDate()));

            return view;
        }
    }

    /**
     * Assign event handler to be passed needed events
     */
    public void setEventHandler(EventHandler eventHandler)
    {
        this.eventHandler = eventHandler;
    }

    /**
     * This interface defines what events to be reported to
     * the outside world
     */
    public interface EventHandler
    {
        void onDayLongPress(Date date);
    }
}
