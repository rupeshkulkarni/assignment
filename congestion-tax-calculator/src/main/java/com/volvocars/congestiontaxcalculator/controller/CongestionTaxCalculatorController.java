package com.volvocars.congestiontaxcalculator.controller;

import com.volvocars.congestiontaxcalculator.common.Constants;
import com.volvocars.congestiontaxcalculator.model.CTCRequest;
import com.volvocars.congestiontaxcalculator.model.CTCResponse;
import com.volvocars.congestiontaxcalculator.service.CongestionTaxCalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CongestionTaxCalculatorController {

    @Autowired
    CongestionTaxCalculatorService congestionTaxCalculator;

    @GetMapping("/ctc")
    public String congestionTaxCalculatorInfo(){
        return "Congestion tax calculator v1";
    }

    @RequestMapping(value = "/calculator", method = RequestMethod.POST)
    @Operation(summary = "Congestion Tax Calculator")
    public ResponseEntity<CTCResponse> congestionTaxCalculate(@RequestBody CTCRequest request)
    {
        CTCResponse response = new CTCResponse();
        try {
            String validationMessage = request.validateInput(request);
            if(validationMessage.equals(Constants.VALID_INPUT_MESSAGE)) {
                response = congestionTaxCalculator.congestionTaxCalculation(request.getCityName(), request.getVehicleType(), request.getDates());

            }
            else {
                response.setDetailMessage(validationMessage);
            }
            return new ResponseEntity<CTCResponse>(response, HttpStatus.OK);
        }
        catch (Exception e) {
            response = new CTCResponse(-1, Constants.SERVICE_FAILED);
            return new ResponseEntity<CTCResponse>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
