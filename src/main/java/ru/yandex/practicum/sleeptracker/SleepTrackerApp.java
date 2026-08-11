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

/*    public static long countDifferencesGreaterThanOneDay(List<SleepingSession> dates) {
        List<LocalDate> list = dates.stream()
                .map(s -> s.getStartSleeping().toLocalDate())
                .distinct()
                .toList();

        return list.stream()
                .mapToLong(currentDate -> {
                    LocalDate nextDate = currentDate.plusDays(1);
                    if (currentDate.getMonthValue() != nextDate.getMonthValue())
                        return 1;
                    if (!list.contains(nextDate)) {
                        nextDate = nextDate.plusDays(1);
                    }
                    return Period.between(currentDate, nextDate).getDays();
                })
                .filter(daysDifference -> daysDifference > 1)
                .count();
    }*/
}