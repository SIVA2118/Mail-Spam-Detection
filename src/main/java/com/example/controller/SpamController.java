package com.example.controller;

import com.example.model.Email;
import com.example.repository.EmailRepository;
import com.example.service.SpamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SpamController {

    @Autowired
    private SpamService spamService;

    @Autowired
    private EmailRepository emailRepository;

    @PostMapping("/check")
    public Email checkSpam(@RequestBody Email email) {
        String result = spamService.checkSpam(email.getMessage());
        email.setResult(result);
        return emailRepository.save(email);
    }

    @GetMapping("/history")
    public List<Email> getHistory() {
        return emailRepository.findAllByOrderByTimestampDesc();
    }

    @GetMapping("/stats")
    public Map<String, Long> getStats() {
        long total = emailRepository.count();
        long spamCount = emailRepository.findAll().stream()
                .filter(e -> "Spam".equals(e.getResult()))
                .count();
        long hamCount = total - spamCount;
        
        return Map.of(
                "total", total,
                "spam", spamCount,
                "ham", hamCount
        );
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, String> deleteEmail(@PathVariable String id) {
        if (emailRepository.existsById(id)) {
            emailRepository.deleteById(id);
            return Map.of("status", "success", "message", "Email deleted successfully");
        }
        return Map.of("status", "error", "message", "Email not found");
    }
}
