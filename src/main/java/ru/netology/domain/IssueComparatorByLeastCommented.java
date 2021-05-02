package ru.netology.domain;

import java.util.Comparator;
import java.util.Set;


/**
 * Класс-компаратор для сортировки issues по числу комментариев (least commented)
 * */
public class IssueComparatorByLeastCommented implements Comparator<Issue> {
    @Override
    public int compare(Issue firstIssue, Issue secondIssue) {
        Set<String> firstComments = firstIssue.getComments();
        Set<String> secondComments = secondIssue.getComments();

       return firstComments.size() - secondComments.size();
    }
}
