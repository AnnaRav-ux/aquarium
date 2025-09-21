package com.aquarium.gui;

import com.aquarium.model.*;
import com.aquarium.service.AquariumService;
import com.aquarium.validation.ValidationResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Главное окно приложения "Аквариум"
 */
public class AquariumGUI extends JFrame {
    private AquariumService aquariumService;
    private JTable aquariumTable;
    private JTable fishTable;
    private JTable equipmentTable;
    private DefaultTableModel aquariumTableModel;
    private DefaultTableModel fishTableModel;
    private DefaultTableModel equipmentTableModel;
    private JTextArea infoArea;
    private int selectedAquariumIndex = -1;
    
    public AquariumGUI() {
        this.aquariumService = new AquariumService();
        initializeGUI();
        loadData();
    }
    
    private void initializeGUI() {
        setTitle("Управление аквариумами");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Создаем панель с вкладками
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Вкладка "Аквариумы"
        tabbedPane.addTab("Аквариумы", createAquariumPanel());
        
        // Вкладка "Рыбы"
        tabbedPane.addTab("Рыбы", createFishPanel());
        
        // Вкладка "Оборудование"
        tabbedPane.addTab("Оборудование", createEquipmentPanel());
        
        // Вкладка "Информация"
        tabbedPane.addTab("Информация", createInfoPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Панель кнопок
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }
    
    private JPanel createAquariumPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Таблица аквариумов
        String[] columnNames = {"Название", "Объем (л)", "Материал", "Местоположение", "Рыб", "Оборудования"};
        aquariumTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        aquariumTable = new JTable(aquariumTableModel);
        aquariumTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        aquariumTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedAquariumIndex = aquariumTable.getSelectedRow();
                updateFishTable();
                updateEquipmentTable();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(aquariumTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Кнопки для аквариумов
        JPanel aquariumButtonPanel = new JPanel(new FlowLayout());
        JButton addAquariumBtn = new JButton("Добавить аквариум");
        JButton editAquariumBtn = new JButton("Редактировать");
        JButton deleteAquariumBtn = new JButton("Удалить");
        
        addAquariumBtn.addActionListener(e -> showAddAquariumDialog());
        editAquariumBtn.addActionListener(e -> showEditAquariumDialog());
        deleteAquariumBtn.addActionListener(e -> deleteAquarium());
        
        aquariumButtonPanel.add(addAquariumBtn);
        aquariumButtonPanel.add(editAquariumBtn);
        aquariumButtonPanel.add(deleteAquariumBtn);
        
        panel.add(aquariumButtonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createFishPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Таблица рыб
        String[] columnNames = {"Имя", "Вид", "Возраст", "Размер (см)", "Цвет", "Тип питания"};
        fishTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        fishTable = new JTable(fishTableModel);
        
        JScrollPane scrollPane = new JScrollPane(fishTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Кнопки для рыб
        JPanel fishButtonPanel = new JPanel(new FlowLayout());
        JButton addFishBtn = new JButton("Добавить рыбу");
        JButton editFishBtn = new JButton("Редактировать");
        JButton deleteFishBtn = new JButton("Удалить");
        
        addFishBtn.addActionListener(e -> showAddFishDialog());
        editFishBtn.addActionListener(e -> showEditFishDialog());
        deleteFishBtn.addActionListener(e -> deleteFish());
        
        fishButtonPanel.add(addFishBtn);
        fishButtonPanel.add(editFishBtn);
        fishButtonPanel.add(deleteFishBtn);
        
        panel.add(fishButtonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createEquipmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Таблица оборудования
        String[] columnNames = {"Название", "Тип", "Бренд", "Мощность (Вт)", "Статус"};
        equipmentTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        equipmentTable = new JTable(equipmentTableModel);
        
        JScrollPane scrollPane = new JScrollPane(equipmentTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Кнопки для оборудования
        JPanel equipmentButtonPanel = new JPanel(new FlowLayout());
        JButton addEquipmentBtn = new JButton("Добавить оборудование");
        JButton editEquipmentBtn = new JButton("Редактировать");
        JButton deleteEquipmentBtn = new JButton("Удалить");
        
        addEquipmentBtn.addActionListener(e -> showAddEquipmentDialog());
        editEquipmentBtn.addActionListener(e -> showEditEquipmentDialog());
        deleteEquipmentBtn.addActionListener(e -> deleteEquipment());
        
        equipmentButtonPanel.add(addEquipmentBtn);
        equipmentButtonPanel.add(editEquipmentBtn);
        equipmentButtonPanel.add(deleteEquipmentBtn);
        
        panel.add(equipmentButtonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        infoArea = new JTextArea(20, 50);
        infoArea.setEditable(false);
        infoArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(infoArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Кнопка обновления информации
        JPanel infoButtonPanel = new JPanel(new FlowLayout());
        JButton refreshInfoBtn = new JButton("Обновить информацию");
        JButton validateBtn = new JButton("Проверить данные");
        
        refreshInfoBtn.addActionListener(e -> updateInfo());
        validateBtn.addActionListener(e -> validateData());
        
        infoButtonPanel.add(refreshInfoBtn);
        infoButtonPanel.add(validateBtn);
        
        panel.add(infoButtonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        JButton exitBtn = new JButton("Выход");
        exitBtn.addActionListener(e -> System.exit(0));
        
        panel.add(exitBtn);
        
        return panel;
    }
    
    private void loadData() {
        updateAquariumTable();
        updateInfo();
    }
    
    private void updateAquariumTable() {
        aquariumTableModel.setRowCount(0);
        List<Aquarium> aquariums = aquariumService.getAllAquariums();
        
        for (Aquarium aquarium : aquariums) {
            Object[] row = {
                aquarium.getName(),
                aquarium.getVolume(),
                aquarium.getMaterial(),
                aquarium.getLocation(),
                aquarium.getFishList().size(),
                aquarium.getEquipmentList().size()
            };
            aquariumTableModel.addRow(row);
        }
    }
    
    private void updateFishTable() {
        fishTableModel.setRowCount(0);
        
        if (selectedAquariumIndex >= 0) {
            Aquarium aquarium = aquariumService.getAquariumById(selectedAquariumIndex);
            if (aquarium != null) {
                for (Fish fish : aquarium.getFishList()) {
                    Object[] row = {
                        fish.getName(),
                        fish.getSpecies(),
                        fish.getAge(),
                        fish.getSize(),
                        fish.getColor(),
                        fish.getFeedingType()
                    };
                    fishTableModel.addRow(row);
                }
            }
        }
    }
    
    private void updateEquipmentTable() {
        equipmentTableModel.setRowCount(0);
        
        if (selectedAquariumIndex >= 0) {
            Aquarium aquarium = aquariumService.getAquariumById(selectedAquariumIndex);
            if (aquarium != null) {
                for (Equipment equipment : aquarium.getEquipmentList()) {
                    Object[] row = {
                        equipment.getName(),
                        equipment.getType(),
                        equipment.getBrand(),
                        equipment.getPower(),
                        equipment.getStatus()
                    };
                    equipmentTableModel.addRow(row);
                }
            }
        }
    }
    
    private void updateInfo() {
        StringBuilder info = new StringBuilder();
        info.append("=== ИНФОРМАЦИЯ ОБ АКВАРИУМАХ ===\n\n");
        info.append(aquariumService.getStatistics()).append("\n\n");
        
        List<Aquarium> aquariums = aquariumService.getAllAquariums();
        for (int i = 0; i < aquariums.size(); i++) {
            Aquarium aquarium = aquariums.get(i);
            info.append("Аквариум #").append(i + 1).append(": ").append(aquarium.getInfo()).append("\n");
            
            info.append("  Рыбы:\n");
            for (Fish fish : aquarium.getFishList()) {
                info.append("    - ").append(fish.toString()).append("\n");
            }
            
            info.append("  Оборудование:\n");
            for (Equipment equipment : aquarium.getEquipmentList()) {
                info.append("    - ").append(equipment.toString()).append("\n");
            }
            info.append("\n");
        }
        
        infoArea.setText(info.toString());
    }
    
    private void validateData() {
        StringBuilder validationInfo = new StringBuilder();
        validationInfo.append("=== РЕЗУЛЬТАТЫ ВАЛИДАЦИИ ===\n\n");
        
        List<Aquarium> aquariums = aquariumService.getAllAquariums();
        boolean allValid = true;
        
        for (int i = 0; i < aquariums.size(); i++) {
            Aquarium aquarium = aquariums.get(i);
            ValidationResult result = aquariumService.addAquarium(aquarium); // Проверяем валидацию
            
            validationInfo.append("Аквариум #").append(i + 1).append(" (").append(aquarium.getName()).append("):\n");
            if (result.isValid()) {
                validationInfo.append("  ✓ Валидация прошла успешно\n");
            } else {
                validationInfo.append("  ✗ Ошибки валидации:\n");
                for (String error : result.getErrors()) {
                    validationInfo.append("    - ").append(error).append("\n");
                }
                allValid = false;
            }
            
            if (result.hasWarnings()) {
                validationInfo.append("  ⚠ Предупреждения:\n");
                for (String warning : result.getWarnings()) {
                    validationInfo.append("    - ").append(warning).append("\n");
                }
            }
            validationInfo.append("\n");
        }
        
        if (allValid) {
            validationInfo.append("✓ Все данные прошли валидацию успешно!\n");
        } else {
            validationInfo.append("✗ Обнаружены ошибки валидации. Проверьте данные.\n");
        }
        
        infoArea.setText(validationInfo.toString());
    }
    
    // Диалоги для добавления/редактирования
    private void showAddAquariumDialog() {
        AquariumDialog dialog = new AquariumDialog(this, "Добавить аквариум", null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Aquarium aquarium = dialog.getAquarium();
            ValidationResult result = aquariumService.addAquarium(aquarium);
            
            if (result.isValid()) {
                updateAquariumTable();
                updateInfo();
                JOptionPane.showMessageDialog(this, "Аквариум успешно добавлен!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Ошибка валидации:\n" + result.getErrorSummary(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showEditAquariumDialog() {
        if (selectedAquariumIndex < 0) {
            JOptionPane.showMessageDialog(this, "Выберите аквариум для редактирования", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Aquarium aquarium = aquariumService.getAquariumById(selectedAquariumIndex);
        AquariumDialog dialog = new AquariumDialog(this, "Редактировать аквариум", aquarium);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Aquarium newAquarium = dialog.getAquarium();
            ValidationResult result = aquariumService.updateAquarium(selectedAquariumIndex, newAquarium);
            
            if (result.isValid()) {
                updateAquariumTable();
                updateInfo();
                JOptionPane.showMessageDialog(this, "Аквариум успешно обновлен!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Ошибка валидации:\n" + result.getErrorSummary(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteAquarium() {
        if (selectedAquariumIndex < 0) {
            JOptionPane.showMessageDialog(this, "Выберите аквариум для удаления", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить выбранный аквариум?", "Подтверждение", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            if (aquariumService.removeAquarium(selectedAquariumIndex)) {
                selectedAquariumIndex = -1;
                updateAquariumTable();
                updateFishTable();
                updateEquipmentTable();
                updateInfo();
                JOptionPane.showMessageDialog(this, "Аквариум успешно удален!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void showAddFishDialog() {
        if (selectedAquariumIndex < 0) {
            JOptionPane.showMessageDialog(this, "Выберите аквариум для добавления рыбы", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        FishDialog dialog = new FishDialog(this, "Добавить рыбу", null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Fish fish = dialog.getFish();
            ValidationResult result = aquariumService.addFishToAquarium(selectedAquariumIndex, fish);
            
            if (result.isValid()) {
                updateFishTable();
                updateAquariumTable();
                updateInfo();
                JOptionPane.showMessageDialog(this, "Рыба успешно добавлена!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Ошибка валидации:\n" + result.getErrorSummary(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showEditFishDialog() {
        if (selectedAquariumIndex < 0 || fishTable.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Выберите рыбу для редактирования", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Aquarium aquarium = aquariumService.getAquariumById(selectedAquariumIndex);
        Fish fish = aquarium.getFishList().get(fishTable.getSelectedRow());
        
        FishDialog dialog = new FishDialog(this, "Редактировать рыбу", fish);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Fish newFish = dialog.getFish();
            ValidationResult result = aquariumService.updateFishInAquarium(selectedAquariumIndex, fish, newFish);
            
            if (result.isValid()) {
                updateFishTable();
                updateInfo();
                JOptionPane.showMessageDialog(this, "Рыба успешно обновлена!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Ошибка валидации:\n" + result.getErrorSummary(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteFish() {
        if (selectedAquariumIndex < 0 || fishTable.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Выберите рыбу для удаления", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить выбранную рыбу?", "Подтверждение", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            Aquarium aquarium = aquariumService.getAquariumById(selectedAquariumIndex);
            Fish fish = aquarium.getFishList().get(fishTable.getSelectedRow());
            
            if (aquariumService.removeFishFromAquarium(selectedAquariumIndex, fish)) {
                updateFishTable();
                updateAquariumTable();
                updateInfo();
                JOptionPane.showMessageDialog(this, "Рыба успешно удалена!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void showAddEquipmentDialog() {
        if (selectedAquariumIndex < 0) {
            JOptionPane.showMessageDialog(this, "Выберите аквариум для добавления оборудования", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        EquipmentDialog dialog = new EquipmentDialog(this, "Добавить оборудование", null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Equipment equipment = dialog.getEquipment();
            ValidationResult result = aquariumService.addEquipmentToAquarium(selectedAquariumIndex, equipment);
            
            if (result.isValid()) {
                updateEquipmentTable();
                updateAquariumTable();
                updateInfo();
                JOptionPane.showMessageDialog(this, "Оборудование успешно добавлено!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Ошибка валидации:\n" + result.getErrorSummary(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showEditEquipmentDialog() {
        if (selectedAquariumIndex < 0 || equipmentTable.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Выберите оборудование для редактирования", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Aquarium aquarium = aquariumService.getAquariumById(selectedAquariumIndex);
        Equipment equipment = aquarium.getEquipmentList().get(equipmentTable.getSelectedRow());
        
        EquipmentDialog dialog = new EquipmentDialog(this, "Редактировать оборудование", equipment);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Equipment newEquipment = dialog.getEquipment();
            ValidationResult result = aquariumService.updateEquipmentInAquarium(selectedAquariumIndex, equipment, newEquipment);
            
            if (result.isValid()) {
                updateEquipmentTable();
                updateInfo();
                JOptionPane.showMessageDialog(this, "Оборудование успешно обновлено!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Ошибка валидации:\n" + result.getErrorSummary(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteEquipment() {
        if (selectedAquariumIndex < 0 || equipmentTable.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Выберите оборудование для удаления", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить выбранное оборудование?", "Подтверждение", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            Aquarium aquarium = aquariumService.getAquariumById(selectedAquariumIndex);
            Equipment equipment = aquarium.getEquipmentList().get(equipmentTable.getSelectedRow());
            
            if (aquariumService.removeEquipmentFromAquarium(selectedAquariumIndex, equipment)) {
                updateEquipmentTable();
                updateAquariumTable();
                updateInfo();
                JOptionPane.showMessageDialog(this, "Оборудование успешно удалено!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}