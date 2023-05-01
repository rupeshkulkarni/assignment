package com.volvocars.congestiontaxcalculator.service;

import com.volvocars.congestiontaxcalculator.common.Constants;
import com.volvocars.congestiontaxcalculator.common.DateTimeUtil;
import com.volvocars.congestiontaxcalculator.config.TaxInfo;
import com.volvocars.congestiontaxcalculator.config.Parameters;
import com.volvocars.congestiontaxcalculator.config.Rule;
import com.volvocars.congestiontaxcalculator.model.CTCResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CongestionTaxCalculatorService
{
    @Autowired
    Parameters parameters;

    Optional<TaxInfo> cityConfigs;

    public CTCResponse congestionTaxCalculation(String cityName, String vehicleType, List<LocalDateTime> dates) {
        CTCResponse response = new CTCResponse();
        int taxAmount = -1;
        cityConfigs = parameters.getCityConfigs(cityName);

        if(!cityConfigs.isEmpty()) {
            taxAmount = getTaxAmount(vehicleType, dates, cityConfigs.get().getTimeDuration());
            response.setDetailMessage(Constants.TAX_CALCULATED_MESSAGE + vehicleType);
        }
        else {
            response.setDetailMessage(Constants.CONFIG_NOT_FOUND + cityName);
        }
        response.setTaxAmount(taxAmount);
        return response;
    }

    public int getTaxAmount(String vehicleType, List<LocalDateTime> dates, int timeConfig) {
        int totalFee = 0;
        int tollFee = 0;
        int taxAmountInSameDuration = 0;

        Collections.sort(dates);

        if(!checkVehicleIsTollFree(vehicleType)) {
            for (int i = 0; i < dates.size(); i++) {
                if (i == 0 || DateTimeUtil.IsDateBetweenConfigMinutes(dates.get(i - 1), dates.get(i), timeConfig)) {
                    tollFee = getTollFee(dates.get(i));
                    totalFee += tollFee;
                } else {
                    taxAmountInSameDuration = getTollFee(dates.get(i));
                    if (tollFee != 0 && tollFee < taxAmountInSameDuration) {
                        totalFee = taxAmountInSameDuration;
                    }
                }
            }

            if (totalFee > cityConfigs.get().getMaxTax()) {
                totalFee = cityConfigs.get().getMaxTax();
            }
        }
        return totalFee;
    }

    public int getTollFee(LocalDateTime date) {
        if (DateTimeUtil.IsTollFreeDate(date)) {
            return 0;
        }

        String hour = date.getHour() + ":" + date.getMinute();
        return cityConfigs.get().getRules().stream().filter(obj -> obj.isTimeBetweenLimits(hour))
                .findFirst().orElse(new Rule()).getAmount();
    }

    private boolean checkVehicleIsTollFree(String vehicleType) {
        if (vehicleType == null) {
            return false;
        }
        return cityConfigs.get().getTaxExemptVehicles().contains(vehicleType);
    }
}