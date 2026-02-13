package com.gabrielle.project_ia.Service;

import com.gabrielle.project_ia.Dto.Purchase;
import com.gabrielle.project_ia.Dto.PurchaseValidationResponse;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class PurchaseValidationAIService {

    private final OllamaClient client;
    private final ObjectMapper objectMapper;

    public PurchaseValidationAIService(OllamaClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public PurchaseValidationResponse validate(Purchase purchase) {
        String prompt = ( """            
                You are an automated purchase validation system.
                
                Analyze the purchase according to the company rules.
                
                Return ONLY a valid JSON in this format:
                
                {
                  "status": "APPROVED or REJECTED",
                  "reason": "short explanation in Brazilian Portuguese"
                }
                
                Do not include any additional text outside the JSON.
                
                Company rules:
                
                1. The buyer must be at least 18 years old.
                2. The maximum quantity allowed is 1000.
                3. The minimum quantity is 1.
                4. Only national credit cards are accepted.
                5. Only the electronics category is allowed.
                
                Purchase data:
                - Age: %s
                - Quantity: %s
                - Payment: %s
                - Category: %s
                """).formatted(
                    purchase.age(),
                    purchase.quantity(),
                    purchase.payment(),
                    purchase.category()
                );

        String result = client.generate(prompt);

        try{
            return objectMapper.readValue(result, PurchaseValidationResponse.class);
        } catch(Exception ex) {
            throw new RuntimeException("Invalid AI response: " + result, ex);
        }
    }
}
