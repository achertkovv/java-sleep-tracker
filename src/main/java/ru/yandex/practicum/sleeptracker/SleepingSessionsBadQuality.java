package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

// количество сессий с плохим качеством сна.
public class SleepingSessionsBadQuality implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions == null) {
            return null;
        }
        long count = sessions.stream()
                .filter(SleepingSession::isBadSession)
                .count();
        return new SleepAnalysisResult("Количество сессий с плохим качеством сна", count);
    }
}
