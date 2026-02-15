package com.example.checkcard.web.controllers.impl;

import com.example.checkcard.services.HistoricalService;
import com.example.checkcard.web.controllers.HistoricalController;
import com.example.checkcard.web.dto.responses.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HistoricalControllerImpl implements HistoricalController {
    private final HistoricalService historicalService;
    @Override
    public ResponseEntity<Map<String, Object>> getAll() {
        Map<String, Object> response = historicalService.getAll();
        if (response.get("message").equals("Historical not found")) {
            return new ResponseEntity<>(RestResponse.response(HttpStatus.NOT_FOUND, response.get("message"), null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, response.get("historicals"), response.get("message")), HttpStatus.OK);
    }
}
