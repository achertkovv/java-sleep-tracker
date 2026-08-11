package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

// Реализуйте функцию, вычисляющую количество бессонных ночей
public class SleepingSessionsSleeplessNightCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        // Также будем считать, что если первая сессия сна в файле началась после 12 дня,
        // потенциальной ночью для сна считается следующая ночь, а если до 12 — то предыдущая.
        long count = sessions.stream()
                .filter(SleepingSession::isSleeplessNight)
                .filter(s -> {
                    // Полдень дня засыпания потенциальной бессонной ночи
                    LocalDateTime midday = LocalDateTime.of(s.getStartSleeping().toLocalDate(),
                            LocalTime.of(12, 0));
                    if (s.getStartSleeping().isBefore(midday)) {
                        // Проверяем предыдущую сессию ночи
                        LocalDate oneDayAgo = s.getStartSleeping().toLocalDate().minusDays(1);
                        Optional<SleepingSession> opt = findSleepingSessionByDate(sessions, oneDayAgo);
                        return opt.map(SleepingSession::isSleeplessNight).orElse(false);
                    } else {
                        // Проверяем следующую сессию ночи
                        LocalDate toDay = s.getStartSleeping().toLocalDate();
                        Optional<SleepingSession> opt = findSleepingSessionByDate(sessions, toDay);
                        return opt.map(SleepingSession::isSleeplessNight).orElse(false);
                    }
                })
                .count();
        return new SleepAnalysisResult("Количество бессонных ночей", count);
    }

    private Optional<SleepingSession> findSleepingSessionByDate(List<SleepingSession> list, LocalDate date) {
        return list.stream()
                .filter(object -> object.getStartSleeping().toLocalDate().isAfter(date))
                .findFirst();
    }


}
