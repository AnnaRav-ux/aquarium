package com.aquarium.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс аквариума - содержит агрегации Fish и Equipment
 */
public class Aquarium {
    private String name;
    private double volume;
    private String material;
    private String location;
    private List<Fish> fishList;
    private List<Equipment> equipmentList;
    
    public Aquarium(String name, double volume, String material, String location) {
        this.name = name;
        this.volume = volume;
        this.material = material;
        this.location = location;
        this.fishList = new ArrayList<>();
        this.equipmentList = new ArrayList<>();
    }
    
    // Геттеры и сеттеры
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public double getVolume() { return volume; }
    public void setVolume(double volume) { this.volume = volume; }
    
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public List<Fish> getFishList() { return new ArrayList<>(fishList); }
    public List<Equipment> getEquipmentList() { return new ArrayList<>(equipmentList); }
    
    // CRUD операции для рыб
    public void addFish(Fish fish) {
        if (fish != null && !fishList.contains(fish)) {
            fishList.add(fish);
        }
    }
    
    public boolean removeFish(Fish fish) {
        return fishList.remove(fish);
    }
    
    public Fish findFishByName(String name) {
        return fishList.stream()
                .filter(fish -> fish.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
    
    public void updateFish(Fish oldFish, Fish newFish) {
        int index = fishList.indexOf(oldFish);
        if (index != -1) {
            fishList.set(index, newFish);
        }
    }
    
    // CRUD операции для оборудования
    public void addEquipment(Equipment equipment) {
        if (equipment != null && !equipmentList.contains(equipment)) {
            equipmentList.add(equipment);
        }
    }
    
    public boolean removeEquipment(Equipment equipment) {
        return equipmentList.remove(equipment);
    }
    
    public Equipment findEquipmentByName(String name) {
        return equipmentList.stream()
                .filter(equipment -> equipment.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
    
    public void updateEquipment(Equipment oldEquipment, Equipment newEquipment) {
        int index = equipmentList.indexOf(oldEquipment);
        if (index != -1) {
            equipmentList.set(index, newEquipment);
        }
    }
    
    /**
     * Проверяет совместимость всех рыб в аквариуме
     */
    public boolean checkFishCompatibility() {
        for (int i = 0; i < fishList.size(); i++) {
            for (int j = i + 1; j < fishList.size(); j++) {
                Fish fish1 = fishList.get(i);
                Fish fish2 = fishList.get(j);
                if (!fish1.isCompatibleWith(fish2) || !fish2.isCompatibleWith(fish1)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Проверяет совместимость всего оборудования с аквариумом
     */
    public boolean checkEquipmentCompatibility() {
        return equipmentList.stream()
                .allMatch(equipment -> equipment.isCompatibleWith(volume));
    }
    
    /**
     * Получает общую информацию об аквариуме
     */
    public String getInfo() {
        return String.format("Аквариум '%s': объем %.1f л, материал: %s, место: %s, рыб: %d, оборудование: %d",
                           name, volume, material, location, fishList.size(), equipmentList.size());
    }
    
    /**
     * Проверяет непротиворечивость полей аквариума
     */
    public boolean validateFields() {
        // Проверка на непротиворечивость: название не должно совпадать с местоположением
        if (name != null && location != null && name.equals(location)) {
            return false;
        }
        
        // Проверка на разумные значения
        if (volume <= 0) return false;
        if (name == null || name.trim().isEmpty()) return false;
        if (material == null || material.trim().isEmpty()) return false;
        if (location == null || location.trim().isEmpty()) return false;
        
        return true;
    }
    
    @Override
    public String toString() {
        return getInfo();
    }
}