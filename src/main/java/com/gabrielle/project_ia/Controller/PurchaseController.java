package com.gabrielle.project_ia.Controller;

import com.gabrielle.project_ia.Dto.Purchase;
import com.gabrielle.project_ia.Dto.PurchaseValidationResponse;
import com.gabrielle.project_ia.Service.PurchaseValidationAIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseValidationAIService service;

    public PurchaseController(PurchaseValidationAIService service) {
        this.service = service;
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody Purchase purchase){
        PurchaseValidationResponse response = service.validate(purchase);

        if("APPROVED".equals(response.status())) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(response);
    }
}
