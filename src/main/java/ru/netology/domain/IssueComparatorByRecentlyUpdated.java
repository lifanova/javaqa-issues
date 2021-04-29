package ru.netology.domain;

import java.util.Comparator;
import java.util.Date;

/**
 * Класс-компаратор для сортировки issues по обновлению (recently updated),
 * то есть по дате обновления (чем позже было обновлено issue)
 * */
public class IssueComparatorByRecentlyUpdated implements Comparator<Issue> {
    @Override
    public int compare(Issue firstIssue, Issue secondIssue) {
        Date firstDate = firstIssue.getUpdatedDate();
        Date secondDate = secondIssue.getUpdatedDate();

        if (firstDate.before(secondDate)) {
            return -1;
        } else if (firstDate.after(secondDate)) {
            return 1;
        } else {
            return 0;
        }
    }
}
