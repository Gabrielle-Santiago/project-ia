package com.gabrielle.project_ia.Dto;

public record OllamaRequest(
        String model,
        String prompt,
        boolean stream
) {
    public static OllamaRequest nonStreaming(String model, String prompt){
        return new OllamaRequest(model, prompt, false);
    }
}
