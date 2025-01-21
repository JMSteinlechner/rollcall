package org.sakaiproject.rollcall.tool;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AttendanceCallbackController {

    @PostMapping("/attendance/callback")
    public ResponseEntity<String> handleCallback(@RequestBody String callback) {
        System.out.println("Received Callback: " + callback);

        // TODO: add logic to parse and analyze callback info

        return new ResponseEntity<>("Callback received", HttpStatus.OK);
    }
}