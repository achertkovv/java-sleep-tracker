package ru.yandex.practicum.sleeptracker;

/***
 * Результаты каждой функции должны выводиться в методе main, при этом пользователю должно быть понятно,
 * что именно посчиталось. Чтобы этого добиться, можно добавить в результат каждой функции её текстовое описание,
 * кроме непосредственно вычисленного значения. Для этого вам понадобится создать дополнительный класс-обёртку
 * для возвращаемого значения. Мы предлагаем назвать такой класс SleepAnalysisResult.
 */
public class SleepAnalysisResult {
    private final String description;
    private final Object value;

    public SleepAnalysisResult(String description, Object value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return description + ": " + value;
    }
}
