package com.volvocars.congestiontaxcalculator.config;

import com.volvocars.congestiontaxcalculator.common.Constants;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

@Component
public class Rule {
    private String startTime;
    private String endTime;
    private int amount;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isTimeBetweenLimits(String dateTime)
    {
        Calendar startDate = getCalenderByGivenDateTime(this.startTime);
        Calendar endDate = getCalenderByGivenDateTime(this.endTime);
        Calendar requestingDate = getCalenderByGivenDateTime(dateTime);

        Date reqTime = requestingDate.getTime();
        if ((reqTime.after(startDate.getTime()) || reqTime.equals(startDate.getTime()))
                && (reqTime.before(endDate.getTime())) || reqTime.equals(endDate.getTime()))
        {
            return true;
        }

        return false;
    }


    private Calendar getCalenderByGivenDateTime(String dateTime)
    {
        Calendar calendar = null;
        try {
            Date startDate = Constants.ruleDateFormat.parse(dateTime);
            calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, 1);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }

}
