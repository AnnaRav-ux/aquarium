package com.aquarium.model;

/**
 * Класс золотой рыбки - наследуется от Fish
 */
public class Goldfish extends Fish {
    private boolean hasLongTail;
    private String tailType;
    
    public Goldfish(String name, String species, int age, double size, String color, 
                   boolean hasLongTail, String tailType) {
        super(name, species, age, size, color);
        this.hasLongTail = hasLongTail;
        this.tailType = tailType;
    }
    
    public boolean isHasLongTail() { return hasLongTail; }
    public void setHasLongTail(boolean hasLongTail) { this.hasLongTail = hasLongTail; }
    
    public String getTailType() { return tailType; }
    public void setTailType(String tailType) { this.tailType = tailType; }
    
    @Override
    public String getFeedingType() {
        return "Всеядная - хлопья, гранулы, живой корм";
    }
    
    @Override
    public boolean isCompatibleWith(Fish other) {
        // Золотые рыбки совместимы с мирными рыбами
        if (other instanceof Goldfish) return true;
        if (other instanceof Guppy) return true;
        return false;
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(", длинный хвост: %s, тип хвоста: %s", 
                                               hasLongTail ? "да" : "нет", tailType);
    }
}