package com.aquarium.model;

/**
 * Базовый класс для всех рыб в аквариуме
 */
public abstract class Fish {
    protected String name;
    protected String species;
    protected int age;
    protected double size;
    protected String color;
    
    public Fish(String name, String species, int age, double size, String color) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.size = size;
        this.color = color;
    }
    
    // Геттеры и сеттеры
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public double getSize() { return size; }
    public void setSize(double size) { this.size = size; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    // Абстрактный метод для получения типа питания
    public abstract String getFeedingType();
    
    // Метод для проверки совместимости с другими рыбами
    public abstract boolean isCompatibleWith(Fish other);
    
    @Override
    public String toString() {
        return String.format("%s (%s) - %d лет, %.1f см, цвет: %s", 
                           name, species, age, size, color);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fish fish = (Fish) obj;
        return name.equals(fish.name) && species.equals(fish.species);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode() + species.hashCode();
    }
}