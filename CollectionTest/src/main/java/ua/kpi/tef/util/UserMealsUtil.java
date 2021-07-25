package ua.kpi.tef.util;

import ua.kpi.tef.model.UserMeal;
import ua.kpi.tef.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000));
    }

    /**
     * Provides a filtered based on LocalDate bounds collection with information about exceed of calories for particular LocalDate.
     * @param mealList a collection of UserMeal objects with data about LocalDateTime, meal description and number of consumed calories.
     * @param startTime a LocalTime object which states the lower bound for filtering based on LocalDate.
     * @param endTime a LocalTime object which states the upper bound for filtering based on LocalDate.
     * @param caloriesPerDay a limit of calories should be consumed daily.
     * @return a collection of UserMealWithExceed objects with data whether number of calories consumed at particular LocalDate more than caloriesPerDay.
     * @see UserMeal
     * @see LocalDateTime
     * @see LocalDate
     * @see UserMealWithExceed
     * @author rbozhko
     */
    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Long> sumOfDailyConsumedCalories = mealList.stream()
                                                                    .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                                                                                                    Collectors.summingLong(UserMeal::getCalories)));
        return mealList.stream()
                        .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                        .map(meal -> new UserMealWithExceed(meal.getDateTime(),
                                                            meal.getDescription(),
                                                            meal.getCalories(),
                                                            sumOfDailyConsumedCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                        .collect(Collectors.toList());
    }
}
