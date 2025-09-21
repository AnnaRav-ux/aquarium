package com.aquarium.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для хранения результатов валидации
 */
public class ValidationResult {
    private List<String> errors;
    private List<String> warnings;
    
    public ValidationResult() {
        this.errors = new ArrayList<>();
        this.warnings = new ArrayList<>();
    }
    
    public void addError(String error) {
        errors.add(error);
    }
    
    public void addWarning(String warning) {
        warnings.add(warning);
    }
    
    public boolean isValid() {
        return errors.isEmpty();
    }
    
    public boolean hasWarnings() {
        return !warnings.isEmpty();
    }
    
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }
    
    public List<String> getWarnings() {
        return new ArrayList<>(warnings);
    }
    
    public String getErrorSummary() {
        if (errors.isEmpty()) {
            return "Валидация прошла успешно";
        }
        return "Найдено ошибок: " + errors.size() + "\n" + String.join("\n", errors);
    }
    
    public String getWarningSummary() {
        if (warnings.isEmpty()) {
            return "Предупреждений нет";
        }
        return "Найдено предупреждений: " + warnings.size() + "\n" + String.join("\n", warnings);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getErrorSummary());
        if (hasWarnings()) {
            sb.append("\n\n").append(getWarningSummary());
        }
        return sb.toString();
    }
}