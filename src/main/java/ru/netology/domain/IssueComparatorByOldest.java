package ru.netology.domain;

import java.util.Comparator;
import java.util.Date;

/**
 * Класс-компаратор для сортировки issues по дате создания
 * (чем позже было создано, тем "новее" issue)
 * */
public class IssueComparatorByOldest implements Comparator<Issue> {
    @Override
    public int compare(Issue firstIssue, Issue secondIssue) {
        Date firstDate = firstIssue.getCreatedDate();
        Date secondDate = secondIssue.getCreatedDate();

        if (firstDate.before(secondDate)) {
            return -1;
        } else if (firstDate.after(secondDate)) {
            return 1;
        } else {
            return 0;
        }
    }
}
