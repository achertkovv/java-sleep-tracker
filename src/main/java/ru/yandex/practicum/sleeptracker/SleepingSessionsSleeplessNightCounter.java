package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.function.Function;

// Реализуйте функцию, вычисляющую количество бессонных ночей
public class SleepingSessionsSleeplessNightCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        // Также будем считать, что если первая сессия сна в файле началась после 12 дня,
        // потенциальной ночью для сна считается следующая ночь, а если до 12 — то предыдущая.
        long count = sessions.stream()
                .filter(SleepingSession::isNightSleep)
                .map(SleepingSession::getNightDate)
                .distinct()
                .count();
        long totalNights = getLoggingPeriod(sessions);
        return new SleepAnalysisResult("Количество бессонных ночей", totalNights - count);
    }

    // Временем логирования считаем интервал от начала первой сессии сна в файле до окончания последней.
    // При этом считаем, что пользователь носит часы не снимая — то есть не было сессий сна, которые не
    // попали бы в файл.
    // Чтобы найти общее количество ночей, удобно использовать статический метод between класса Period.
    private long getLoggingPeriod(List<SleepingSession> sessions) {
        LocalDateTime loggingStart = sessions.getFirst().getStartSleeping();
        LocalDateTime loggingEnd = sessions.getLast().getEndSleeping();

        LocalDate startDate = getNightDate(loggingStart);
        LocalDate endDate = loggingEnd.toLocalDate();

        // Обратите внимание, что этот метод включает левую границу интервала — то есть дату
        // начала периода, но не включает правую — дату окончания.
        return Period.between(startDate, endDate.plusDays(1)).getDays();
    }

    // Также будем считать, что если первая сессия сна в файле началась после 12 дня,
    // потенциальной ночью для сна считается следующая ночь, а если до 12 — то предыдущая.
    private LocalDate getNightDate(LocalDateTime start) {
        // Определяем к какой ночи время начала сна
        return start.getHour() >= 12
                ? start.toLocalDate().plusDays(1)
                : start.toLocalDate();
    }

}
