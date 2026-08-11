package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

// Классифицируем пользователя
public class SleepingSessionsSleepType implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        // Подсчитываем количество вхождений каждой строки
        Map<String, Long> countMap = sessions.stream()
                .filter(SleepingSession::isNotSleeplessNight)
                .map(SleepingSession::getUserType)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Находим строку с максимальным количеством
        Optional<Map.Entry<String, Long>> mostCommon = countMap.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        String value = null;
        if (mostCommon.isPresent()) {
            value = mostCommon.get().getKey();
        }
//        mostCommon.ifPresent(s ->
//                System.out.println("Строка '" + s.getKey() + "' встречается " + s.getValue() + " раз(а)."));

        return new SleepAnalysisResult("Классифицируем пользователя", value);
    }
}
