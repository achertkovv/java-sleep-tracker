package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {

    public static void main(String[] args) {
        try {
            // Проверяем, что путь был передан
            if (args.length == 0) {
                System.out.println("Использование: java SleepTrackerApp <file-path>");
                return;
            }

            SleepingSessionLoader sleepingSessionLoader = new SleepingSessionLoader(args[0]);
            List<SleepingSession> sleepingSessions = sleepingSessionLoader.createSleepingSessionDictionary();

            // Список аналитических функций
            List<Function<List<SleepingSession>, SleepAnalysisResult>> analysisFunctions = List.of(
                    new SleepingSessionsCounter(),
                    new SleepingSessionsMinDuration(),
                    new SleepingSessionsMaxDuration(),
                    new SleepingSessionsAverageDuration(),
                    new SleepingSessionsBadQuality(),
                    new SleepingSessionsSleeplessNightCounter(),
                    new SleepingSessionsSleepType()
            );

            // Вызов аналитических функций
            analysisFunctions.stream()
                    .map(func -> func.apply(sleepingSessions))
                    .forEach(System.out::println);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}