package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {
    private static List<SleepingSession> sleepingSessions;

    @BeforeAll
    static void testGetWordleDictionary() throws IOException, URISyntaxException {
        java.net.URL url = SleepTrackerAppTest.class.getClassLoader().getResource("sleep_log.txt");
        assertNotNull(url);
        java.nio.file.Path resPath = java.nio.file.Paths.get(url.toURI());
        SleepingSessionLoader sleepingSessionLoader = new SleepingSessionLoader(resPath.toString());
        sleepingSessions = sleepingSessionLoader.createSleepingSessionDictionary();
    }

    @Test
    void testSessionsCounter() {
        Function<List<SleepingSession>, SleepAnalysisResult> counter = new SleepingSessionsCounter();
        SleepAnalysisResult result = counter.apply(sleepingSessions);
        assertEquals(13, Integer.parseInt(result.getValue().toString()));
    }

    @Test
    void testSessionsCounterMessage() {
        Function<List<SleepingSession>, SleepAnalysisResult> counter = new SleepingSessionsCounter();
        SleepAnalysisResult result = counter.apply(sleepingSessions);
        assertEquals("Количество сессий сна", result.getDescription());
    }

    @Test
    void SleepingSessionsBadQuality() {
        Function<List<SleepingSession>, SleepAnalysisResult> bad = new SleepingSessionsBadQuality();
        SleepAnalysisResult result = bad.apply(sleepingSessions);
        assertEquals(2, Integer.parseInt(result.getValue().toString()));
    }

    @Test
    void SleepingSessionsBadQualityMessage() {
        Function<List<SleepingSession>, SleepAnalysisResult> bad = new SleepingSessionsBadQuality();
        SleepAnalysisResult result = bad.apply(sleepingSessions);
        assertEquals("Количество сессий с плохим качеством сна", result.getDescription());
    }

    @Test
    void SleepingSessionsMaxDuration() {
        Function<List<SleepingSession>, SleepAnalysisResult> max = new SleepingSessionsMaxDuration();
        SleepAnalysisResult result = max.apply(sleepingSessions);
        assertEquals(500.0, Double.parseDouble(result.getValue().toString()));
    }

    @Test
    void SleepingSessionsMaxDurationMessage() {
        Function<List<SleepingSession>, SleepAnalysisResult> max = new SleepingSessionsMaxDuration();
        SleepAnalysisResult result = max.apply(sleepingSessions);
        assertEquals("Максимальная продолжительность сессии (в минутах)", result.getDescription());
    }

    @Test
    void SleepingSessionsMinDuration() {
        Function<List<SleepingSession>, SleepAnalysisResult> min = new SleepingSessionsMinDuration();
        SleepAnalysisResult result = min.apply(sleepingSessions);
        assertEquals(45, Double.parseDouble(result.getValue().toString()));
    }

    @Test
    void SleepingSessionsMinDurationMessage() {
        Function<List<SleepingSession>, SleepAnalysisResult> min = new SleepingSessionsMinDuration();
        SleepAnalysisResult result = min.apply(sleepingSessions);
        assertEquals("Минимальная продолжительность сессии (в минутах)", result.getDescription());
    }

    @Test
    void SleepingSessionsAverageDuration() {
        Function<List<SleepingSession>, SleepAnalysisResult> ave = new SleepingSessionsAverageDuration();
        SleepAnalysisResult result = ave.apply(sleepingSessions);
        assertEquals(345, Double.parseDouble(result.getValue().toString()));
    }

    @Test
    void SleepingSessionsAverageDurationMessage() {
        Function<List<SleepingSession>, SleepAnalysisResult> ave = new SleepingSessionsAverageDuration();
        SleepAnalysisResult result = ave.apply(sleepingSessions);
        assertEquals("Средняя продолжительность сессии (в минутах)", result.getDescription());
    }

    @Test
    void SleepingSessionsSleeplessNightCounter() {
        Function<List<SleepingSession>, SleepAnalysisResult> sleep = new SleepingSessionsSleeplessNightCounter();
        SleepAnalysisResult result = sleep.apply(sleepingSessions);
        assertEquals(20, Integer.parseInt(result.getValue().toString()));
    }

    @Test
    void SleepingSessionsSleeplessNightCounterMessage() {
        Function<List<SleepingSession>, SleepAnalysisResult> sleep = new SleepingSessionsSleeplessNightCounter();
        SleepAnalysisResult result = sleep.apply(sleepingSessions);
        assertEquals("Количество бессонных ночей", result.getDescription());
    }

    @Test
    void SleepingSessionsSleeplessNightCounterEquals0BeforeMidnightAndAfter6() {
        Function<List<SleepingSession>, SleepAnalysisResult> sleep = new SleepingSessionsSleeplessNightCounter();
        List<SleepingSession> testSleepingSessions = new ArrayList<>(List.of(
                new SleepingSession("01.10.25 23:15;02.10.25 07:30;GOOD".split(";")),
                new SleepingSession("02.10.25 23:50;03.10.25 06:40;NORMAL".split(";")),
                new SleepingSession("03.10.25 22:10;03.10.25 06:10;NORMAL".split(";"))));
        SleepAnalysisResult result = sleep.apply(testSleepingSessions);
        assertEquals(0, Integer.parseInt(result.getValue().toString()));
    }

    @Test
    void SleepingSessionsSleeplessNightCounterEquals0AfterMidnightAndAfter6() {
        Function<List<SleepingSession>, SleepAnalysisResult> sleep = new SleepingSessionsSleeplessNightCounter();
        List<SleepingSession> testSleepingSessions = new ArrayList<>(List.of(
                new SleepingSession("01.10.25 00:15;02.10.25 07:30;GOOD".split(";")),
                new SleepingSession("02.10.25 00:50;03.10.25 06:40;NORMAL".split(";")),
                new SleepingSession("03.10.25 01:10;03.10.25 06:10;NORMAL".split(";"))));
        SleepAnalysisResult result = sleep.apply(testSleepingSessions);
        assertEquals(0, Integer.parseInt(result.getValue().toString()));
    }

    @Test
    void SleepingSessionsSleeplessNightCounterEquals0AfterMidnightAndBefore6() {
        Function<List<SleepingSession>, SleepAnalysisResult> sleep = new SleepingSessionsSleeplessNightCounter();
        List<SleepingSession> testSleepingSessions = new ArrayList<>(List.of(
                new SleepingSession("01.10.25 00:15;02.10.25 05:30;GOOD".split(";")),
                new SleepingSession("02.10.25 00:50;03.10.25 03:40;NORMAL".split(";")),
                new SleepingSession("03.10.25 01:10;03.10.25 04:10;NORMAL".split(";"))));
        SleepAnalysisResult result = sleep.apply(testSleepingSessions);
        assertEquals(0, Integer.parseInt(result.getValue().toString()));
    }

    @Test
    void SleepingSessionsSleeplessNightCounterEquals1BeforeNoonAndAfterNoon() {
        Function<List<SleepingSession>, SleepAnalysisResult> sleep = new SleepingSessionsSleeplessNightCounter();
        List<SleepingSession> testSleepingSessions = new ArrayList<>(List.of(
                new SleepingSession("01.10.25 23:15;02.10.25 07:30;GOOD".split(";")),
                new SleepingSession("02.10.25 11:50;02.10.25 14:40;BAD".split(";")),
                new SleepingSession("03.10.25 22:10;03.10.25 06:10;NORMAL".split(";"))));
        SleepAnalysisResult result = sleep.apply(testSleepingSessions);
        assertEquals(1, Integer.parseInt(result.getValue().toString()));
    }

    @Test
    void SleepingSessionsSleeplessNightCounterEquals1AfterNoon() {
        Function<List<SleepingSession>, SleepAnalysisResult> sleep = new SleepingSessionsSleeplessNightCounter();
        List<SleepingSession> testSleepingSessions = new ArrayList<>(List.of(
                new SleepingSession("01.10.25 23:15;02.10.25 07:30;GOOD".split(";")),
                new SleepingSession("02.10.25 12:50;02.10.25 14:40;BAD".split(";")),
                new SleepingSession("03.10.25 22:10;03.10.25 06:10;NORMAL".split(";"))));
        SleepAnalysisResult result = sleep.apply(testSleepingSessions);
        assertEquals(1, Integer.parseInt(result.getValue().toString()));
    }

    @Test
    void SleepingSessionsSleepType() {
        Function<List<SleepingSession>, SleepAnalysisResult> type = new SleepingSessionsSleepType();
        SleepAnalysisResult result = type.apply(sleepingSessions);
        assertEquals("Голубь", result.getValue().toString());
    }

    @Test
    void SleepingSessionsSleepTypeMessage() {
        Function<List<SleepingSession>, SleepAnalysisResult> type = new SleepingSessionsSleepType();
        SleepAnalysisResult result = type.apply(sleepingSessions);
        assertEquals("Классифицируем пользователя", result.getDescription());
    }

    @Test
    void SleepingSessionsSleepTypeLark() {
        Function<List<SleepingSession>, SleepAnalysisResult> type = new SleepingSessionsSleepType();
        List<SleepingSession> testSleepingSessions = new ArrayList<>(List.of(
                new SleepingSession("01.10.25 21:15;02.10.25 05:30;GOOD".split(";")),
                new SleepingSession("02.10.25 12:50;02.10.25 14:40;BAD".split(";")),
                new SleepingSession("03.10.25 21:10;03.10.25 06:00;NORMAL".split(";")),
                new SleepingSession("03.10.25 12:10;03.10.25 13:00;NORMAL".split(";"))));
        SleepAnalysisResult result = type.apply(testSleepingSessions);
        assertEquals("Жаворонок", result.getValue().toString());
    }

    @Test
    void SleepingSessionsSleepTypeOwl() {
        Function<List<SleepingSession>, SleepAnalysisResult> type = new SleepingSessionsSleepType();
        List<SleepingSession> testSleepingSessions = new ArrayList<>(List.of(
                new SleepingSession("01.10.25 23:15;02.10.25 09:30;GOOD".split(";")),
                new SleepingSession("02.10.25 12:50;02.10.25 14:40;BAD".split(";")),
                new SleepingSession("03.10.25 23:10;03.10.25 09:00;NORMAL".split(";")),
                new SleepingSession("03.10.25 12:10;03.10.25 13:00;NORMAL".split(";"))));
        SleepAnalysisResult result = type.apply(testSleepingSessions);
        assertEquals("Сова", result.getValue().toString());
    }
}