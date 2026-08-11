package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SleepingSession {
    private LocalDateTime startSleepingDateTime;
    private LocalDateTime endSleepingDateTime;
    private SleepQuality sleepQuality;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public SleepingSession(String[] splitLog) {
        setStartSleepingDateTime(splitLog[0]);
        setEndSleepingDateTime(splitLog[1]);
        setSleepQuality(splitLog[2]);
    }

    public double getSessionInterval() {
        // Возвращает интервал в минутах
        Duration duration = Duration.between(startSleepingDateTime, endSleepingDateTime);
        return duration.toMinutes();
    }

    public boolean isBadSession() {
        return sleepQuality.equals(SleepQuality.BAD);
    }

    private boolean IsTimeInRange(LocalTime time) {
        LocalTime start = startSleepingDateTime.toLocalTime();
        LocalTime end = endSleepingDateTime.toLocalTime();
        if (start.isBefore(end)) {
            // Время находится в одном интервале и не пересекает 00:00 (например, start = 01:00, а end = 08:00)
            return time.isAfter(start) && time.isBefore(end);
        } else {
            // Диапазон пересекает 00:00 (например, start = 23:00, а end = 05:00 - т.е. "start" isAtfer "end")
            return time.isAfter(start) || time.isBefore(end);
        }
    }

    // Бессонной ночью считается ночь, когда не было ни одной сессии сна, пересекающей интервал от 0:00 до 6:00.
    // То есть, если пользователь спал с 23:00 до 3:00, ночь не будет считаться бессонной, также как если он спал
    // с 2:00 до 7:00. А вот если сон был только с 7:00 до 11:00, такую ночь мы запишем в бессонные.
    public boolean isSleeplessNight() {
        LocalTime zero = LocalTime.of(0, 0); // 00 часов ночи
        LocalTime six = LocalTime.of(6, 0); // 06 часов утра
        LocalDateTime start = LocalDateTime.of(startSleepingDateTime.toLocalDate(), zero);
        LocalDateTime end = LocalDateTime.of(endSleepingDateTime.toLocalDate(), six);

        // Если пользователь лёг спать в один день, а проснулся на следующий, он точно спал этой ночью
        // Подумайте о случае, когда интервал логирования начинается в одном месяце, а заканчивается в другом!
        if ((startSleepingDateTime.toLocalDate().getMonth() == endSleepingDateTime.toLocalDate().getMonth()) &&
                (startSleepingDateTime.toLocalDate().getDayOfMonth() <
                        endSleepingDateTime.toLocalDate().getDayOfMonth()))
            return false;

        if (IsTimeInRange(zero)) {
            // Диапазон пересекает границу полночи
            return false;
        } else if (IsTimeInRange(six)) {
            // Диапазон пересекает границу 6 утра
            return false;
        }

        // Диапазон находится в границах с 00:00 до 06:00
        return startSleepingDateTime.isAfter(start) && endSleepingDateTime.isBefore(end);
    }

    public boolean isNotSleeplessNight() {
        return !isSleeplessNight();
    }

    // Для каждой ночи на основе времени засыпания и пробуждения определите, относится ночь к типу «сова»,
    // «жаворонок» или «голубь».
    // - «Сова» — если время засыпания было после 23:00, а время пробуждения — после 9:00.
    // - «Жаворонок» — если время засыпания было до 22:00, а время пробуждения до — 7:00.
    // - «Голубь» — во всех остальных случаях.
    // Бессонные ночи и дневные сессии сна в подсчёте должны игнорироваться.
    // Реализуйте представленный алгоритм в отдельной функции. Обратите внимание, что результатом работы новой
    // функции будет не число, а тип пользователя. Подумайте, как это реализовать, чтобы остальные функции и код
    // вывода результата продолжили работать по-прежнему.
    public String getUserType() {
        LocalTime twentyThree = LocalTime.of(23, 0); // 23 часа
        LocalTime nine = LocalTime.of(9, 0); // 09 часов утра
        LocalTime twentyTwo = LocalTime.of(22, 0); // 22 часа
        LocalTime seven = LocalTime.of(7, 0); // 07 часов утра
        LocalDateTime start = LocalDateTime.of(startSleepingDateTime.toLocalDate(), twentyThree);
        LocalDateTime end = LocalDateTime.of(endSleepingDateTime.toLocalDate(), nine);

        if (!startSleepingDateTime.isBefore(start) && endSleepingDateTime.isAfter(end)) {
            return "Сова";
        }

        start = LocalDateTime.of(startSleepingDateTime.toLocalDate(), twentyTwo);
        end = LocalDateTime.of(endSleepingDateTime.toLocalDate(), seven);

        if (startSleepingDateTime.isBefore(start) && !endSleepingDateTime.isAfter(end)) {
            return "Жаворонок";
        }

        return "Голубь";
    }

    public LocalDateTime getStartSleeping() {
        return startSleepingDateTime;
    }

    public void setStartSleepingDateTime(String startSleeping) {
        this.startSleepingDateTime = LocalDateTime.parse(startSleeping, DATE_TIME_FORMATTER);
    }

    public LocalDateTime getEndSleeping() {
        return endSleepingDateTime;
    }

    public void setEndSleepingDateTime(String stopSleeping) {
        this.endSleepingDateTime = LocalDateTime.parse(stopSleeping, DATE_TIME_FORMATTER);
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }

    public void setSleepQuality(String quality) {
        this.sleepQuality = SleepQuality.valueOf(quality.toUpperCase());
    }

    @Override
    public String toString() {
        return "SleepingSession{" +
                "startSleeping='" + getStartSleeping() + '\'' +
                ", stopSleeping=" + getEndSleeping() +
                ", sleepQuality=" + getSleepQuality() +
                '}';
    }
}
