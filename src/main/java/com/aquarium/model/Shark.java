package com.aquarium.model;

/**
 * Класс акулы - наследуется от Fish
 */
public class Shark extends Fish {
    private boolean isAggressive;
    private String finType;
    private double maxSpeed;
    
    public Shark(String name, String species, int age, double size, String color, 
                boolean isAggressive, String finType, double maxSpeed) {
        super(name, species, age, size, color);
        this.isAggressive = isAggressive;
        this.finType = finType;
        this.maxSpeed = maxSpeed;
    }
    
    public boolean isAggressive() { return isAggressive; }
    public void setAggressive(boolean aggressive) { isAggressive = aggressive; }
    
    public String getFinType() { return finType; }
    public void setFinType(String finType) { this.finType = finType; }
    
    public double getMaxSpeed() { return maxSpeed; }
    public void setMaxSpeed(double maxSpeed) { this.maxSpeed = maxSpeed; }
    
    @Override
    public String getFeedingType() {
        return "Хищник - рыба, мясо, специальный корм для хищников";
    }
    
    @Override
    public boolean isCompatibleWith(Fish other) {
        // Акулы совместимы только с другими акулами или крупными рыбами
        if (other instanceof Shark) return true;
        return false;
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(", агрессивная: %s, тип плавников: %s, макс. скорость: %.1f км/ч", 
                                               isAggressive ? "да" : "нет", finType, maxSpeed);
    }
}