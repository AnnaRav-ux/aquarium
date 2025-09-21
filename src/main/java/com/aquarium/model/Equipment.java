package com.aquarium.model;

/**
 * Класс оборудования для аквариума
 */
public class Equipment {
    private String name;
    private String type;
    private String brand;
    private double power;
    private boolean isWorking;
    private String description;
    
    public Equipment(String name, String type, String brand, double power, 
                    boolean isWorking, String description) {
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.power = power;
        this.isWorking = isWorking;
        this.description = description;
    }
    
    // Геттеры и сеттеры
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    
    public double getPower() { return power; }
    public void setPower(double power) { this.power = power; }
    
    public boolean isWorking() { return isWorking; }
    public void setWorking(boolean working) { isWorking = working; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    /**
     * Проверяет совместимость оборудования с аквариумом
     */
    public boolean isCompatibleWith(double aquariumVolume) {
        // Простая логика проверки совместимости
        if (type.equals("Фильтр") && power < aquariumVolume * 0.1) return false;
        if (type.equals("Нагреватель") && power < aquariumVolume * 0.05) return false;
        return true;
    }
    
    /**
     * Получает статус оборудования
     */
    public String getStatus() {
        return isWorking ? "Работает" : "Не работает";
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s) - %s, мощность: %.1f Вт, статус: %s", 
                           name, type, brand, power, getStatus());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Equipment equipment = (Equipment) obj;
        return name.equals(equipment.name) && type.equals(equipment.type);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode() + type.hashCode();
    }
}