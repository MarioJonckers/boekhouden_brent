package be.vermolen.boekhouden.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class GeneralController {

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("welcome");
    }
}
