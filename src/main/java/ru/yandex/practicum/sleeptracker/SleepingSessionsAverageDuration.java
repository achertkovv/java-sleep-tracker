package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;

// средняя продолжительность сессии (в минутах);
public class SleepingSessionsAverageDuration implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        double count = 0;
        OptionalDouble averageOptional = sessions.stream()
                .mapToDouble(SleepingSession::getSessionInterval)
                .average();
        if (averageOptional.isPresent()) {
            count = Math.round(averageOptional.getAsDouble()); // Получаем значение
        }
        return new SleepAnalysisResult("Средняя продолжительность сессии (в минутах)", count);
    }
}
