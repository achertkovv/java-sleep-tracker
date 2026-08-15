package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/*
    этот класс содержит в себе всю рутину по работе с файлами логов и с кодировками
    ему нужны методы по загрузке списка логов сна из файла по имени файла
    на выходе должен быть список классов SleepingSession
 */
public class SleepingSessionLoader {
    String filePath;

    public SleepingSessionLoader(String filePath) {
        this.filePath = filePath;

        // Проверяем, что файл существует
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new RuntimeException("Файл не найден: " + path);
        }
    }

    private List<String> loadLogsFromFileToList() throws IOException {
        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, UTF_8))) {
            lines = reader.lines().toList();
        }
        return lines;
    }

    public List<SleepingSession> createSleepingSessionDictionary() throws IOException {

        List<String> logsFromFile = loadLogsFromFileToList();

        // Преобразование списка строк в список объектов SleepingSession
        return logsFromFile.stream()
                .map(line -> line.split(";"))
                .map(SleepingSession::new)  // Создаём объект SleepingSession для каждой строки
                .sorted(Comparator.comparing(SleepingSession::getStartSleeping))
                .toList();
    }
}
