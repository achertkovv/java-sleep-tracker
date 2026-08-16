package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

// И добавьте первую, самую простую аналитическую функцию, — она будет выводить на экран информацию,
// сколько всего было сессий сна за представленный период
public class SleepingSessionsCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions == null) {
            return null;
        }
        long count = sessions.size();
        return new SleepAnalysisResult("Количество сессий сна", count);
    }
}