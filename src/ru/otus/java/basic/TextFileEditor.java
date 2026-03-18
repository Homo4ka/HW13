package ru.otus.java.basic;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TextFileEditor {

    public static void main(String[] args) {
        File currentDir = new File(".");
        File[] files = currentDir.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("В текущем каталоге нет файлов");
            return;
        }

        System.out.println("Список текстовых файлов в корневом каталоге:");
        for (File file : files) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                System.out.println(" - " + file.getName());
            }
        }

        Scanner consoleScanner = new Scanner(System.in);
        System.out.print("\nВведите имя файла (например, file.txt): ");
        String fileName = consoleScanner.nextLine().trim();

        File selectedFile = new File(fileName);
        if (!selectedFile.exists()) {
            try {
                if (selectedFile.createNewFile()) {
                    System.out.println("Файл не найден создан новый файл: " + fileName);
                } else {
                    System.out.println("Не удалось создать файл");
                    return;
                }
            } catch (IOException e) {
                System.err.println("Ошибка при создании файла: " + e.getMessage());
                return;
            }
        }

        System.out.println("\nСодержимое файла \"" + fileName + "\":");
        System.out.println("----------------------------------------");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(selectedFile), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }
        System.out.println("----------------------------------------");

        System.out.println("\nТеперь вы можете дописывать строки в файл");
        System.out.println("Введите строку и нажмите Enter. Для выхода введите пустую строку или 'exit'");

        try (PrintStream printStream = new PrintStream(
                new BufferedOutputStream(new FileOutputStream(selectedFile, true)), true, StandardCharsets.UTF_8)) {
            while (true) {
                System.out.print("> ");
                String input = consoleScanner.nextLine();
                if (input.isEmpty() || input.equalsIgnoreCase("exit")) {
                    System.out.println("Завершение работы");
                    break;
                }
                printStream.println(input);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}