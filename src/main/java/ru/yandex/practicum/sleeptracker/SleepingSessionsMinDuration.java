package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;

// минимальная продолжительность сессии (в минутах);
public class SleepingSessionsMinDuration implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions == null) {
            return null;
        }

        OptionalDouble minOptional = sessions.stream()
                .mapToDouble(SleepingSession::getSessionInterval)
                .min();

        double count = minOptional.isPresent() ? minOptional.getAsDouble() : 0; // Получаем значение
        return new SleepAnalysisResult("Минимальная продолжительность сессии (в минутах)", count);
    }
}
