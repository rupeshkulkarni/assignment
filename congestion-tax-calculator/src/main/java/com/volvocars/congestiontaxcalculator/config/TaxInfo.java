package com.volvocars.congestiontaxcalculator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "config.taxinfo")
public class TaxInfo {

    private String name;
    private int timeDuration;
    private int maxTax;
    private List<Rule> rules;
    private List<String> taxExemptVehicles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(int timeDuration) {
        this.timeDuration = timeDuration;
    }

    public int getMaxTax() {
        return maxTax;
    }

    public void setMaxTax(int maxTax) {
        this.maxTax = maxTax;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public List<String> getTaxExemptVehicles() {
        return taxExemptVehicles;
    }

    public void setTaxExemptVehicles(List<String> taxExemptVehicles) {
        this.taxExemptVehicles = taxExemptVehicles;
    }
}
