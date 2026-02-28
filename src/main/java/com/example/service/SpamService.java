package com.example.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SpamService {

    // Rule-based keyword detection
    private final String[] spamWords = {"win", "free", "offer", "money", "urgent", "lottery", "prize", "cash", "subscribe"};

    // Mock Naive Bayes Data (In a real scenario, this would be trained on a dataset)
    private final Map<String, Double> spamWordProbabilities = new HashMap<>();
    private final Map<String, Double> hamWordProbabilities = new HashMap<>();
    private final double pSpam = 0.5; // Probability that an email is spam
    private final double pHam = 0.5;  // Probability that an email is ham (not spam)

    public SpamService() {
        // Simple mock training
        spamWordProbabilities.put("win", 0.8);
        spamWordProbabilities.put("free", 0.9);
        spamWordProbabilities.put("money", 0.7);
        hamWordProbabilities.put("meeting", 0.9);
        hamWordProbabilities.put("hello", 0.8);
        hamWordProbabilities.put("project", 0.7);
    }

    public String checkSpam(String message) {
        // Upgrade to a hybrid approach: Keyword + Simple Naive Bayes
        if (isKeywordMatch(message)) {
            return "Spam";
        }
        
        return naiveBayesCheck(message) ? "Spam" : "Not Spam";
    }

    private boolean isKeywordMatch(String message) {
        String lowerMessage = message.toLowerCase();
        for (String word : spamWords) {
            if (lowerMessage.contains(word)) {
                return true;
            }
        }
        return false;
    }

    private boolean naiveBayesCheck(String message) {
        String[] words = message.toLowerCase().split("\\s+");
        double spamLikelihood = Math.log(pSpam);
        double hamLikelihood = Math.log(pHam);

        for (String word : words) {
            spamLikelihood += Math.log(spamWordProbabilities.getOrDefault(word, 0.01));
            hamLikelihood += Math.log(hamWordProbabilities.getOrDefault(word, 0.01));
        }

        return spamLikelihood > hamLikelihood;
    }
}
