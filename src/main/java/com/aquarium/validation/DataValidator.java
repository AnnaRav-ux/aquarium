package com.aquarium.validation;

import com.aquarium.model.Fish;
import com.aquarium.model.Aquarium;
import com.aquarium.model.Equipment;

/**
 * Класс для валидации данных аквариума
 */
public class DataValidator {
    
    /**
     * Валидирует рыбу
     */
    public static ValidationResult validateFish(Fish fish) {
        ValidationResult result = new ValidationResult();
        
        if (fish == null) {
            result.addError("Рыба не может быть null");
            return result;
        }
        
        // Проверка имени
        if (fish.getName() == null || fish.getName().trim().isEmpty()) {
            result.addError("Имя рыбы не может быть пустым");
        }
        
        // Проверка вида
        if (fish.getSpecies() == null || fish.getSpecies().trim().isEmpty()) {
            result.addError("Вид рыбы не может быть пустым");
        }
        
        // Проверка на непротиворечивость: имя не должно совпадать с видом
        if (fish.getName() != null && fish.getSpecies() != null && 
            fish.getName().equals(fish.getSpecies())) {
            result.addError("Имя рыбы не должно совпадать с видом");
        }
        
        // Проверка возраста
        if (fish.getAge() < 0) {
            result.addError("Возраст рыбы не может быть отрицательным");
        }
        
        if (fish.getAge() > 50) {
            result.addError("Возраст рыбы не может превышать 50 лет");
        }
        
        // Проверка размера
        if (fish.getSize() <= 0) {
            result.addError("Размер рыбы должен быть положительным");
        }
        
        if (fish.getSize() > 200) {
            result.addError("Размер рыбы не может превышать 200 см");
        }
        
        // Проверка цвета
        if (fish.getColor() == null || fish.getColor().trim().isEmpty()) {
            result.addError("Цвет рыбы не может быть пустым");
        }
        
        return result;
    }
    
    /**
     * Валидирует оборудование
     */
    public static ValidationResult validateEquipment(Equipment equipment) {
        ValidationResult result = new ValidationResult();
        
        if (equipment == null) {
            result.addError("Оборудование не может быть null");
            return result;
        }
        
        // Проверка имени
        if (equipment.getName() == null || equipment.getName().trim().isEmpty()) {
            result.addError("Имя оборудования не может быть пустым");
        }
        
        // Проверка типа
        if (equipment.getType() == null || equipment.getType().trim().isEmpty()) {
            result.addError("Тип оборудования не может быть пустым");
        }
        
        // Проверка на непротиворечивость: имя не должно совпадать с типом
        if (equipment.getName() != null && equipment.getType() != null && 
            equipment.getName().equals(equipment.getType())) {
            result.addError("Имя оборудования не должно совпадать с типом");
        }
        
        // Проверка бренда
        if (equipment.getBrand() == null || equipment.getBrand().trim().isEmpty()) {
            result.addError("Бренд оборудования не может быть пустым");
        }
        
        // Проверка мощности
        if (equipment.getPower() < 0) {
            result.addError("Мощность оборудования не может быть отрицательной");
        }
        
        if (equipment.getPower() > 10000) {
            result.addError("Мощность оборудования не может превышать 10000 Вт");
        }
        
        return result;
    }
    
    /**
     * Валидирует аквариум
     */
    public static ValidationResult validateAquarium(Aquarium aquarium) {
        ValidationResult result = new ValidationResult();
        
        if (aquarium == null) {
            result.addError("Аквариум не может быть null");
            return result;
        }
        
        // Используем встроенную валидацию аквариума
        if (!aquarium.validateFields()) {
            result.addError("Поля аквариума содержат противоречивые данные");
        }
        
        // Проверка объема
        if (aquarium.getVolume() <= 0) {
            result.addError("Объем аквариума должен быть положительным");
        }
        
        if (aquarium.getVolume() > 10000) {
            result.addError("Объем аквариума не может превышать 10000 литров");
        }
        
        // Проверка совместимости рыб
        if (!aquarium.checkFishCompatibility()) {
            result.addError("Некоторые рыбы в аквариуме несовместимы друг с другом");
        }
        
        // Проверка совместимости оборудования
        if (!aquarium.checkEquipmentCompatibility()) {
            result.addError("Некоторые виды оборудования несовместимы с объемом аквариума");
        }
        
        // Валидация каждой рыбы
        for (Fish fish : aquarium.getFishList()) {
            ValidationResult fishResult = validateFish(fish);
            if (!fishResult.isValid()) {
                result.addError("Ошибка в данных рыбы '" + fish.getName() + "': " + 
                              String.join(", ", fishResult.getErrors()));
            }
        }
        
        // Валидация каждого оборудования
        for (Equipment equipment : aquarium.getEquipmentList()) {
            ValidationResult equipmentResult = validateEquipment(equipment);
            if (!equipmentResult.isValid()) {
                result.addError("Ошибка в данных оборудования '" + equipment.getName() + "': " + 
                              String.join(", ", equipmentResult.getErrors()));
            }
        }
        
        return result;
    }
}