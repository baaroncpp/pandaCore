package com.fredastone.pandacore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/reports")
public class ReportsController {
	
    @RequestMapping(path="get",params = {"direction", "page","size" },method = RequestMethod.GET)
    public ResponseEntity<?> getReportLists(@RequestParam("direction") String direction,@RequestParam("page") int page,@RequestParam("size") int size) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }

}