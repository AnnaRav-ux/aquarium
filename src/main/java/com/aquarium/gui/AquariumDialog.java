package com.aquarium.gui;

import com.aquarium.model.Aquarium;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Диалог для добавления/редактирования аквариума
 */
public class AquariumDialog extends JDialog {
    private JTextField nameField;
    private JTextField volumeField;
    private JTextField materialField;
    private JTextField locationField;
    private boolean confirmed = false;
    private Aquarium aquarium;
    
    public AquariumDialog(Frame parent, String title, Aquarium aquarium) {
        super(parent, title, true);
        this.aquarium = aquarium;
        initializeComponents();
        layoutComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(parent);
    }
    
    private void initializeComponents() {
        nameField = new JTextField(20);
        volumeField = new JTextField(20);
        materialField = new JTextField(20);
        locationField = new JTextField(20);
        
        if (aquarium != null) {
            nameField.setText(aquarium.getName());
            volumeField.setText(String.valueOf(aquarium.getVolume()));
            materialField.setText(aquarium.getMaterial());
            locationField.setText(aquarium.getLocation());
        }
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Название
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Название:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        
        // Объем
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Объем (л):"), gbc);
        gbc.gridx = 1;
        formPanel.add(volumeField, gbc);
        
        // Материал
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Материал:"), gbc);
        gbc.gridx = 1;
        formPanel.add(materialField, gbc);
        
        // Местоположение
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Местоположение:"), gbc);
        gbc.gridx = 1;
        formPanel.add(locationField, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
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
            String volumeText = volumeField.getText().trim();
            String material = materialField.getText().trim();
            String location = locationField.getText().trim();
            
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Название не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if (material.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Материал не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if (location.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Местоположение не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            double volume = Double.parseDouble(volumeText);
            if (volume <= 0) {
                JOptionPane.showMessageDialog(this, "Объем должен быть положительным числом", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Проверка на непротиворечивость: название не должно совпадать с местоположением
            if (name.equals(location)) {
                JOptionPane.showMessageDialog(this, "Название не должно совпадать с местоположением", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Объем должен быть числом", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public Aquarium getAquarium() {
        if (confirmed) {
            String name = nameField.getText().trim();
            double volume = Double.parseDouble(volumeField.getText().trim());
            String material = materialField.getText().trim();
            String location = locationField.getText().trim();
            
            return new Aquarium(name, volume, material, location);
        }
        return null;
    }
}