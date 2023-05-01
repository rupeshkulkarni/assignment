package com.volvocars.congestiontaxcalculator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.volvocars.congestiontaxcalculator.common.Constants;

import java.time.LocalDateTime;
import java.util.List;

public class CTCRequest {
    private String cityName;
    private String vehicleType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> dates;


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public List<LocalDateTime> getDates() {
        return dates;
    }

    public void setDates(List<LocalDateTime> dates) {
        this.dates = dates;
    }

    public String validateInput(CTCRequest request)
    {
        if(request.cityName == null)
            return Constants.INPUT_CANNOT_BE_NULL + "cityName";
        if(request.vehicleType == null)
            return Constants.INPUT_CANNOT_BE_NULL + "vehicleType";
        if(request.dates == null || request.dates.size() == 0)
            return Constants.INPUT_CANNOT_BE_NULL + "dates";

        return Constants.VALID_INPUT_MESSAGE;
    }
}
