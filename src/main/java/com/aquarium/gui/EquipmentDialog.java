package com.aquarium.gui;

import com.aquarium.model.Equipment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Диалог для добавления/редактирования оборудования
 */
public class EquipmentDialog extends JDialog {
    private JTextField nameField;
    private JTextField typeField;
    private JTextField brandField;
    private JTextField powerField;
    private JCheckBox workingCheck;
    private JTextArea descriptionArea;
    private boolean confirmed = false;
    private Equipment equipment;
    
    public EquipmentDialog(Frame parent, String title, Equipment equipment) {
        super(parent, title, true);
        this.equipment = equipment;
        initializeComponents();
        layoutComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(parent);
    }
    
    private void initializeComponents() {
        nameField = new JTextField(20);
        typeField = new JTextField(20);
        brandField = new JTextField(20);
        powerField = new JTextField(20);
        workingCheck = new JCheckBox("Работает");
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        
        if (equipment != null) {
            nameField.setText(equipment.getName());
            typeField.setText(equipment.getType());
            brandField.setText(equipment.getBrand());
            powerField.setText(String.valueOf(equipment.getPower()));
            workingCheck.setSelected(equipment.isWorking());
            descriptionArea.setText(equipment.getDescription());
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
        
        // Тип
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Тип:"), gbc);
        gbc.gridx = 1;
        formPanel.add(typeField, gbc);
        
        // Бренд
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Бренд:"), gbc);
        gbc.gridx = 1;
        formPanel.add(brandField, gbc);
        
        // Мощность
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Мощность (Вт):"), gbc);
        gbc.gridx = 1;
        formPanel.add(powerField, gbc);
        
        // Статус
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(workingCheck, gbc);
        
        // Описание
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Описание:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(descriptionArea), gbc);
        
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
            String type = typeField.getText().trim();
            String brand = brandField.getText().trim();
            String powerText = powerField.getText().trim();
            
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Название не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if (type.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Тип не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Проверка на непротиворечивость: название не должно совпадать с типом
            if (name.equals(type)) {
                JOptionPane.showMessageDialog(this, "Название не должно совпадать с типом", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if (brand.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Бренд не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            double power = Double.parseDouble(powerText);
            if (power < 0 || power > 10000) {
                JOptionPane.showMessageDialog(this, "Мощность должна быть от 0 до 10000 Вт", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Мощность должна быть числом", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public Equipment getEquipment() {
        if (confirmed) {
            String name = nameField.getText().trim();
            String type = typeField.getText().trim();
            String brand = brandField.getText().trim();
            double power = Double.parseDouble(powerField.getText().trim());
            boolean working = workingCheck.isSelected();
            String description = descriptionArea.getText().trim();
            
            return new Equipment(name, type, brand, power, working, description);
        }
        return null;
    }
}