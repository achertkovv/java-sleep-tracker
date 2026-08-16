package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;

// максимальная продолжительность сессии (в минутах);
public class SleepingSessionsMaxDuration implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions == null) {
            return null;
        }
        OptionalDouble maxOptional = sessions.stream()
                .mapToDouble(SleepingSession::getSessionInterval)
                .max();
        double count = maxOptional.isPresent() ? maxOptional.getAsDouble() : 0;  // Получаем значение
        return new SleepAnalysisResult("Максимальная продолжительность сессии (в минутах)", count);
    }
}
