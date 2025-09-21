package com.aquarium.gui;

import com.aquarium.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Диалог для добавления/редактирования рыбы
 */
public class FishDialog extends JDialog {
    private JTextField nameField;
    private JTextField speciesField;
    private JTextField ageField;
    private JTextField sizeField;
    private JTextField colorField;
    private JComboBox<String> fishTypeCombo;
    private JPanel specificPanel;
    private boolean confirmed = false;
    private Fish fish;
    
    public FishDialog(Frame parent, String title, Fish fish) {
        super(parent, title, true);
        this.fish = fish;
        initializeComponents();
        layoutComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(parent);
    }
    
    private void initializeComponents() {
        nameField = new JTextField(20);
        speciesField = new JTextField(20);
        ageField = new JTextField(20);
        sizeField = new JTextField(20);
        colorField = new JTextField(20);
        
        fishTypeCombo = new JComboBox<>(new String[]{"Золотая рыбка", "Акула", "Гуппи"});
        fishTypeCombo.addActionListener(e -> updateSpecificPanel());
        
        specificPanel = new JPanel();
        
        if (fish != null) {
            nameField.setText(fish.getName());
            speciesField.setText(fish.getSpecies());
            ageField.setText(String.valueOf(fish.getAge()));
            sizeField.setText(String.valueOf(fish.getSize()));
            colorField.setText(fish.getColor());
            
            if (fish instanceof Goldfish) {
                fishTypeCombo.setSelectedItem("Золотая рыбка");
            } else if (fish instanceof Shark) {
                fishTypeCombo.setSelectedItem("Акула");
            } else if (fish instanceof Guppy) {
                fishTypeCombo.setSelectedItem("Гуппи");
            }
        }
        
        updateSpecificPanel();
    }
    
    private void updateSpecificPanel() {
        specificPanel.removeAll();
        specificPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        String selectedType = (String) fishTypeCombo.getSelectedItem();
        
        if ("Золотая рыбка".equals(selectedType)) {
            JCheckBox longTailCheck = new JCheckBox("Длинный хвост");
            JTextField tailTypeField = new JTextField(15);
            
            if (fish instanceof Goldfish) {
                longTailCheck.setSelected(((Goldfish) fish).isHasLongTail());
                tailTypeField.setText(((Goldfish) fish).getTailType());
            }
            
            gbc.gridx = 0; gbc.gridy = 0;
            specificPanel.add(longTailCheck, gbc);
            gbc.gridx = 1;
            specificPanel.add(new JLabel("Тип хвоста:"), gbc);
            gbc.gridx = 2;
            specificPanel.add(tailTypeField, gbc);
            
        } else if ("Акула".equals(selectedType)) {
            JCheckBox aggressiveCheck = new JCheckBox("Агрессивная");
            JTextField finTypeField = new JTextField(15);
            JTextField maxSpeedField = new JTextField(15);
            
            if (fish instanceof Shark) {
                aggressiveCheck.setSelected(((Shark) fish).isAggressive());
                finTypeField.setText(((Shark) fish).getFinType());
                maxSpeedField.setText(String.valueOf(((Shark) fish).getMaxSpeed()));
            }
            
            gbc.gridx = 0; gbc.gridy = 0;
            specificPanel.add(aggressiveCheck, gbc);
            gbc.gridx = 1;
            specificPanel.add(new JLabel("Тип плавников:"), gbc);
            gbc.gridx = 2;
            specificPanel.add(finTypeField, gbc);
            gbc.gridx = 1; gbc.gridy = 1;
            specificPanel.add(new JLabel("Макс. скорость:"), gbc);
            gbc.gridx = 2;
            specificPanel.add(maxSpeedField, gbc);
            
        } else if ("Гуппи".equals(selectedType)) {
            JCheckBox colorfulCheck = new JCheckBox("Яркая");
            JTextField patternField = new JTextField(15);
            JTextField offspringField = new JTextField(15);
            
            if (fish instanceof Guppy) {
                colorfulCheck.setSelected(((Guppy) fish).isColorful());
                patternField.setText(((Guppy) fish).getPattern());
                offspringField.setText(String.valueOf(((Guppy) fish).getOffspringCount()));
            }
            
            gbc.gridx = 0; gbc.gridy = 0;
            specificPanel.add(colorfulCheck, gbc);
            gbc.gridx = 1;
            specificPanel.add(new JLabel("Узор:"), gbc);
            gbc.gridx = 2;
            specificPanel.add(patternField, gbc);
            gbc.gridx = 1; gbc.gridy = 1;
            specificPanel.add(new JLabel("Потомство:"), gbc);
            gbc.gridx = 2;
            specificPanel.add(offspringField, gbc);
        }
        
        specificPanel.revalidate();
        specificPanel.repaint();
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Основные поля
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Имя:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Вид:"), gbc);
        gbc.gridx = 1;
        formPanel.add(speciesField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Возраст:"), gbc);
        gbc.gridx = 1;
        formPanel.add(ageField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Размер (см):"), gbc);
        gbc.gridx = 1;
        formPanel.add(sizeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Цвет:"), gbc);
        gbc.gridx = 1;
        formPanel.add(colorField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Тип рыбы:"), gbc);
        gbc.gridx = 1;
        formPanel.add(fishTypeCombo, gbc);
        
        add(formPanel, BorderLayout.NORTH);
        add(specificPanel, BorderLayout.CENTER);
        
        // Кнопки
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Отмена");
        
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    confirmed = true;
                    dispose();
                }
            }
        });
        
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private boolean validateInput() {
        try {
            String name = nameField.getText().trim();
            String species = speciesField.getText().trim();
            String ageText = ageField.getText().trim();
            String sizeText = sizeField.getText().trim();
            String color = colorField.getText().trim();
            
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Имя не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if (species.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Вид не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Проверка на непротиворечивость: имя не должно совпадать с видом
            if (name.equals(species)) {
                JOptionPane.showMessageDialog(this, "Имя не должно совпадать с видом", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if (color.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Цвет не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            int age = Integer.parseInt(ageText);
            if (age < 0 || age > 50) {
                JOptionPane.showMessageDialog(this, "Возраст должен быть от 0 до 50 лет", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            double size = Double.parseDouble(sizeText);
            if (size <= 0 || size > 200) {
                JOptionPane.showMessageDialog(this, "Размер должен быть от 0 до 200 см", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Возраст и размер должны быть числами", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public Fish getFish() {
        if (confirmed) {
            String name = nameField.getText().trim();
            String species = speciesField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            double size = Double.parseDouble(sizeField.getText().trim());
            String color = colorField.getText().trim();
            String selectedType = (String) fishTypeCombo.getSelectedItem();
            
            if ("Золотая рыбка".equals(selectedType)) {
                JCheckBox longTailCheck = (JCheckBox) specificPanel.getComponent(0);
                JTextField tailTypeField = (JTextField) specificPanel.getComponent(2);
                return new Goldfish(name, species, age, size, color, 
                                  longTailCheck.isSelected(), tailTypeField.getText());
            } else if ("Акула".equals(selectedType)) {
                JCheckBox aggressiveCheck = (JCheckBox) specificPanel.getComponent(0);
                JTextField finTypeField = (JTextField) specificPanel.getComponent(2);
                JTextField maxSpeedField = (JTextField) specificPanel.getComponent(4);
                return new Shark(name, species, age, size, color, 
                               aggressiveCheck.isSelected(), finTypeField.getText(), 
                               Double.parseDouble(maxSpeedField.getText()));
            } else if ("Гуппи".equals(selectedType)) {
                JCheckBox colorfulCheck = (JCheckBox) specificPanel.getComponent(0);
                JTextField patternField = (JTextField) specificPanel.getComponent(2);
                JTextField offspringField = (JTextField) specificPanel.getComponent(4);
                return new Guppy(name, species, age, size, color, 
                               colorfulCheck.isSelected(), patternField.getText(), 
                               Integer.parseInt(offspringField.getText()));
            }
        }
        return null;
    }
}