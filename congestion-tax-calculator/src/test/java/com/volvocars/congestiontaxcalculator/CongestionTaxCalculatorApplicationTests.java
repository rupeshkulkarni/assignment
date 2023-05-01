package com.volvocars.congestiontaxcalculator;

import com.volvocars.congestiontaxcalculator.model.CTCResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CongestionTaxCalculatorApplicationTests
{
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;


	@Test
	public void whenExemptVehicleOnInput() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("{\"cityName\": \"Gothenburg\", \"vehicleType\": \"Emergency\",\"dates\": [\"2013-03-29 06:27:00\"]}", headers);
		ResponseEntity<CTCResponse> response = restTemplate
				.postForEntity(new URL("http://localhost:" + port + "/calculator").toString(), request, CTCResponse.class);
		assertEquals(0, response.getBody().getTaxAmount());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void whenDateIsHoliday() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("{\"cityName\": \"Gothenburg\", \"vehicleType\": \"Car\",\"dates\": [\"2013-03-29 06:27:00\"]}", headers);
		ResponseEntity<CTCResponse> response = restTemplate
				.postForEntity(new URL("http://localhost:" + port + "/calculator").toString(), request, CTCResponse.class);
		assertEquals(0, response.getBody().getTaxAmount());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
