package ru.yandex.practicum.sleeptracker;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

// максимальная продолжительность сессии (в минутах);
public class SleepingSessionsMaxDuration implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        double count = 0;
        Optional<SleepingSession> minOptional = sessions.stream()
                .max(Comparator.comparing(SleepingSession::getSessionInterval));
        if (minOptional.isPresent()) {
            count = minOptional.get().getSessionInterval(); // Получаем значение
        }
        return new SleepAnalysisResult("Максимальная продолжительность сессии (в минутах)", count);
    }
}
