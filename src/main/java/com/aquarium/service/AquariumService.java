package com.aquarium.service;

import com.aquarium.model.*;
import com.aquarium.validation.DataValidator;
import com.aquarium.validation.ValidationResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервисный класс для управления аквариумами
 */
public class AquariumService {
    private List<Aquarium> aquariums;
    
    public AquariumService() {
        this.aquariums = new ArrayList<>();
        initializeSampleData();
    }
    
    /**
     * Инициализация тестовых данных
     */
    private void initializeSampleData() {
        // Создаем тестовый аквариум
        Aquarium aquarium = new Aquarium("Морской аквариум", 200.0, "Стекло", "Гостиная");
        
        // Добавляем рыб
        Goldfish goldfish = new Goldfish("Золотушка", "Золотая рыбка", 2, 8.5, "Золотой", true, "Веерный");
        Shark shark = new Shark("Акуленок", "Белая акула", 5, 45.0, "Серый", false, "Треугольный", 25.0);
        Guppy guppy = new Guppy("Гуппик", "Гуппи", 1, 3.2, "Радужный", true, "Пятнистый", 12);
        
        aquarium.addFish(goldfish);
        aquarium.addFish(shark);
        aquarium.addFish(guppy);
        
        // Добавляем оборудование
        Equipment filter = new Equipment("Внешний фильтр", "Фильтр", "Eheim", 25.0, true, "Внешний фильтр для очистки воды");
        Equipment heater = new Equipment("Нагреватель", "Нагреватель", "Tetra", 200.0, true, "Автоматический нагреватель");
        Equipment pump = new Equipment("Помпа", "Помпа", "Aquael", 15.0, true, "Циркуляционная помпа");
        
        aquarium.addEquipment(filter);
        aquarium.addEquipment(heater);
        aquarium.addEquipment(pump);
        
        aquariums.add(aquarium);
    }
    
    // CRUD операции для аквариумов
    public List<Aquarium> getAllAquariums() {
        return new ArrayList<>(aquariums);
    }
    
    public Aquarium getAquariumById(int index) {
        if (index >= 0 && index < aquariums.size()) {
            return aquariums.get(index);
        }
        return null;
    }
    
    public ValidationResult addAquarium(Aquarium aquarium) {
        ValidationResult result = DataValidator.validateAquarium(aquarium);
        if (result.isValid()) {
            aquariums.add(aquarium);
        }
        return result;
    }
    
    public ValidationResult updateAquarium(int index, Aquarium newAquarium) {
        ValidationResult result = DataValidator.validateAquarium(newAquarium);
        if (result.isValid() && index >= 0 && index < aquariums.size()) {
            aquariums.set(index, newAquarium);
        } else if (index < 0 || index >= aquariums.size()) {
            result.addError("Индекс аквариума вне допустимого диапазона");
        }
        return result;
    }
    
    public boolean removeAquarium(int index) {
        if (index >= 0 && index < aquariums.size()) {
            aquariums.remove(index);
            return true;
        }
        return false;
    }
    
    // CRUD операции для рыб
    public ValidationResult addFishToAquarium(int aquariumIndex, Fish fish) {
        ValidationResult result = DataValidator.validateFish(fish);
        if (result.isValid() && aquariumIndex >= 0 && aquariumIndex < aquariums.size()) {
            Aquarium aquarium = aquariums.get(aquariumIndex);
            aquarium.addFish(fish);
            
            // Проверяем совместимость после добавления
            if (!aquarium.checkFishCompatibility()) {
                result.addWarning("Добавленная рыба может быть несовместима с другими рыбами в аквариуме");
            }
        } else if (aquariumIndex < 0 || aquariumIndex >= aquariums.size()) {
            result.addError("Индекс аквариума вне допустимого диапазона");
        }
        return result;
    }
    
    public ValidationResult updateFishInAquarium(int aquariumIndex, Fish oldFish, Fish newFish) {
        ValidationResult result = DataValidator.validateFish(newFish);
        if (result.isValid() && aquariumIndex >= 0 && aquariumIndex < aquariums.size()) {
            Aquarium aquarium = aquariums.get(aquariumIndex);
            aquarium.updateFish(oldFish, newFish);
            
            // Проверяем совместимость после обновления
            if (!aquarium.checkFishCompatibility()) {
                result.addWarning("Обновленная рыба может быть несовместима с другими рыбами в аквариуме");
            }
        } else if (aquariumIndex < 0 || aquariumIndex >= aquariums.size()) {
            result.addError("Индекс аквариума вне допустимого диапазона");
        }
        return result;
    }
    
    public boolean removeFishFromAquarium(int aquariumIndex, Fish fish) {
        if (aquariumIndex >= 0 && aquariumIndex < aquariums.size()) {
            Aquarium aquarium = aquariums.get(aquariumIndex);
            return aquarium.removeFish(fish);
        }
        return false;
    }
    
    // CRUD операции для оборудования
    public ValidationResult addEquipmentToAquarium(int aquariumIndex, Equipment equipment) {
        ValidationResult result = DataValidator.validateEquipment(equipment);
        if (result.isValid() && aquariumIndex >= 0 && aquariumIndex < aquariums.size()) {
            Aquarium aquarium = aquariums.get(aquariumIndex);
            aquarium.addEquipment(equipment);
            
            // Проверяем совместимость после добавления
            if (!equipment.isCompatibleWith(aquarium.getVolume())) {
                result.addWarning("Добавленное оборудование может быть несовместимо с объемом аквариума");
            }
        } else if (aquariumIndex < 0 || aquariumIndex >= aquariums.size()) {
            result.addError("Индекс аквариума вне допустимого диапазона");
        }
        return result;
    }
    
    public ValidationResult updateEquipmentInAquarium(int aquariumIndex, Equipment oldEquipment, Equipment newEquipment) {
        ValidationResult result = DataValidator.validateEquipment(newEquipment);
        if (result.isValid() && aquariumIndex >= 0 && aquariumIndex < aquariums.size()) {
            Aquarium aquarium = aquariums.get(aquariumIndex);
            aquarium.updateEquipment(oldEquipment, newEquipment);
            
            // Проверяем совместимость после обновления
            if (!newEquipment.isCompatibleWith(aquarium.getVolume())) {
                result.addWarning("Обновленное оборудование может быть несовместимо с объемом аквариума");
            }
        } else if (aquariumIndex < 0 || aquariumIndex >= aquariums.size()) {
            result.addError("Индекс аквариума вне допустимого диапазона");
        }
        return result;
    }
    
    public boolean removeEquipmentFromAquarium(int aquariumIndex, Equipment equipment) {
        if (aquariumIndex >= 0 && aquariumIndex < aquariums.size()) {
            Aquarium aquarium = aquariums.get(aquariumIndex);
            return aquarium.removeEquipment(equipment);
        }
        return false;
    }
    
    /**
     * Получает статистику по всем аквариумам
     */
    public String getStatistics() {
        int totalAquariums = aquariums.size();
        int totalFish = aquariums.stream().mapToInt(a -> a.getFishList().size()).sum();
        int totalEquipment = aquariums.stream().mapToInt(a -> a.getEquipmentList().size()).sum();
        double totalVolume = aquariums.stream().mapToDouble(Aquarium::getVolume).sum();
        
        return String.format("Статистика:\n" +
                           "Аквариумов: %d\n" +
                           "Рыб: %d\n" +
                           "Оборудования: %d\n" +
                           "Общий объем: %.1f л",
                           totalAquariums, totalFish, totalEquipment, totalVolume);
    }
}