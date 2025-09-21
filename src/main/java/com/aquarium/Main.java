package com.aquarium;

import com.aquarium.gui.AquariumGUI;

import javax.swing.*;

/**
 * Главный класс приложения "Аквариум"
 */
public class Main {
    public static void main(String[] args) {
        // Устанавливаем Look and Feel для лучшего внешнего вида
        try {
        } catch (Exception e) {
            System.err.println("Не удалось установить системный Look and Feel: " + e.getMessage());
        }
        
        // Запускаем GUI в Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                AquariumGUI gui = new AquariumGUI();
                gui.setVisible(true);
                
                // Показываем приветственное сообщение
                JOptionPane.showMessageDialog(gui, 
                    "Добро пожаловать в программу управления аквариумами!\n\n" +
                    "Возможности программы:\n" +
                    "• Управление аквариумами (добавление, редактирование, удаление)\n" +
                    "• Управление рыбами разных видов (золотые рыбки, акулы, гуппи)\n" +
                    "• Управление оборудованием аквариума\n" +
                    "• Проверка совместимости рыб и оборудования\n" +
                    "• Валидация данных с проверкой на непротиворечивость\n\n" +
                    "Программа уже содержит тестовые данные для демонстрации.",
                    "Добро пожаловать!",
                    JOptionPane.INFORMATION_MESSAGE);
                    
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Ошибка при запуске приложения: " + e.getMessage(), 
                    "Ошибка", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}