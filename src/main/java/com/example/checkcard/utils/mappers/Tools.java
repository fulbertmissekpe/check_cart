package com.example.checkcard.utils.mappers;

import com.example.checkcard.web.dto.responses.RestResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class Tools {
    public static String getCellValue(Cell cell) {
        if (cell == null) return "";
        if (cell.getCellType() == CellType.NUMERIC) {
            return Double.valueOf(cell.getNumericCellValue()).toString();
        }
        return cell.toString();
    }
    public static ResponseEntity<Map<String, Object>> getMapResponseEntity(Map<String, Object> response) {
        if (response.get("message").equals("Successfully")) {
            return new ResponseEntity<>(RestResponse.response(HttpStatus.CREATED, response.get("message"), null),HttpStatus.CREATED);
        }
        return new ResponseEntity<>(RestResponse.response(HttpStatus.BAD_REQUEST, response.get("message"), null),HttpStatus.BAD_REQUEST);
    }
}
