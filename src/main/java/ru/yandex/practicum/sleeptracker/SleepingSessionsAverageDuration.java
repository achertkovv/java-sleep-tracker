package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;

// средняя продолжительность сессии (в минутах);
public class SleepingSessionsAverageDuration implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions == null) {
            return null;
        }
        OptionalDouble averageOptional = sessions.stream()
                .mapToDouble(SleepingSession::getSessionInterval)
                .average();
        double count = averageOptional.isPresent() ? Math.round(averageOptional.getAsDouble()) : 0; // Получаем значение
        return new SleepAnalysisResult("Средняя продолжительность сессии (в минутах)", count);
    }
}
