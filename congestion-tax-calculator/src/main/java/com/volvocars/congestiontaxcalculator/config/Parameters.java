package com.volvocars.congestiontaxcalculator.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@ConfigurationProperties(prefix = "config")
public class Parameters {

    private List<TaxInfo> cities;

    public List<TaxInfo> getCities() {
        return cities;
    }

    public void setCities(List<TaxInfo> cities) {
        this.cities = cities;
    }

    public Optional<TaxInfo> getCityConfigs(String cityName)
    {
        return cities.stream().filter(taxInfo -> taxInfo.getName().equals(cityName)).findFirst();
    }

}
