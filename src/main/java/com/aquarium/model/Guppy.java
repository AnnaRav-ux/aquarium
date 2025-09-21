package com.aquarium.model;

/**
 * Класс гуппи - наследуется от Fish
 */
public class Guppy extends Fish {
    private boolean isColorful;
    private String pattern;
    private int offspringCount;
    
    public Guppy(String name, String species, int age, double size, String color, 
                boolean isColorful, String pattern, int offspringCount) {
        super(name, species, age, size, color);
        this.isColorful = isColorful;
        this.pattern = pattern;
        this.offspringCount = offspringCount;
    }
    
    public boolean isColorful() { return isColorful; }
    public void setColorful(boolean colorful) { isColorful = colorful; }
    
    public String getPattern() { return pattern; }
    public void setPattern(String pattern) { this.pattern = pattern; }
    
    public int getOffspringCount() { return offspringCount; }
    public void setOffspringCount(int offspringCount) { this.offspringCount = offspringCount; }
    
    @Override
    public String getFeedingType() {
        return "Всеядная - мелкие хлопья, артемия, дафния";
    }
    
    @Override
    public boolean isCompatibleWith(Fish other) {
        // Гуппи совместимы с мирными рыбами
        if (other instanceof Guppy) return true;
        if (other instanceof Goldfish) return true;
        return false;
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(", яркая: %s, узор: %s, потомство: %d", 
                                               isColorful ? "да" : "нет", pattern, offspringCount);
    }
}