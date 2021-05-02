package ru.netology.manager;

import ru.netology.domain.Issue;
import ru.netology.domain.IssueComparatorByLeastCommented;
import ru.netology.domain.IssueComparatorByOldest;
import ru.netology.domain.IssueComparatorByLastRecentlyUpdated;
import ru.netology.repository.IssueRepository;

import java.util.*;
import java.util.function.Predicate;

public class IssueManager {
    private IssueRepository repository;

    public IssueManager(IssueRepository repository) {
        this.repository = repository;
    }

    /**
     * Сохранить issue
     */
    public void saveIssue(Issue issue) {
        repository.save(issue);
    }

    /**
     * Получить issue по id
     */
    public Issue getIssueById(int id) {
        return repository.findById(id);
    }

    /**
     * Удалить issue по id
     */
    public void removeIssue(int id) {
        repository.removeById(id);
    }

    /**
     * Открыть issue по id
     */
    public void openIssueById(int id) {
        setOpenedStatus(id, true);
    }

    /**
     * Закрыть issue по id
     */
    public void closeIssueById(int id) {
        setOpenedStatus(id, false);
    }

    private void setOpenedStatus(int id, boolean opened) {
        Issue issue = repository.findById(id);

        if (issue == null) {
            System.out.println("[setOpenedStatus]: Error: issue with id = " + id + "is not found!");
            return;
        }

        issue.setOpened(opened);
        repository.save(issue);
    }

    /**
     * Получить все открытые issues
     */
    public List<Issue> getOpenedIssues() {
        Predicate<Issue> predicate = (issue) -> issue.isOpened();

        return repository.filterByPredicate(predicate);
    }

    /**
     * Получить все закрытые issues
     */
    public List<Issue> getClosedIssues() {
        Predicate<Issue> predicate = (issue) -> !issue.isOpened();

        return repository.filterByPredicate(predicate);
    }

    /**
     * Получить issues, отфильтрованные по автору
     */
    public List<Issue> getIssuesByAuthor(String author) {
        if (author == null || author.isEmpty()) {
            return Collections.emptyList();
        }

        Predicate<Issue> predicate = (issue) -> author.equalsIgnoreCase(issue.getAuthor());

        return repository.filterByPredicate(predicate);
    }

    /**
     * Получить issues, отфильтрованные по milestone
     */
    public List<Issue> getIssuesByMilestone(String milestone) {
        if (milestone == null || milestone.isEmpty()) {
            return Collections.emptyList();
        }

        Predicate<Issue> predicate = (issue) -> milestone.equalsIgnoreCase(issue.getMilestone());

        return repository.filterByPredicate(predicate);
    }

    public List<Issue> getIssuesByLabels(Set<String> labels) {
        if (labels == null || labels.isEmpty()) {
            return Collections.emptyList();
        }

        /*List<Issue> result = new ArrayList<>();
        List<Issue> issues = repository.getIssues();
        for (Issue issue : issues) {
            Set<String> issueLabels = issue.getLabels();
            if(issueLabels.containsAll(labels)) {
                result.add(issue);
            }
        }

        return result;*/

        Predicate<Issue> predicate = (issue) -> issue.getLabels().containsAll(labels);

        return repository.filterByPredicate(predicate);
    }

    public List<Issue> getIssuesByAssignee(Set<String> assignee) {
        if (assignee == null || assignee.isEmpty()) {
            return Collections.emptyList();
        }

        Predicate<Issue> predicate = (issue) -> issue.getAssignee().containsAll(assignee);

        return repository.filterByPredicate(predicate);
    }

    /* Сортировка */
    public List<Issue> sortIssuesByNewest(List<Issue> issues) {
        // Передаем список issue как параметр, т.к. список, получаемый из репозитория,
        // может быть и отфильтрованным, т.е. сначала фильтрация, затем сортировка
        issues.sort(new IssueComparatorByOldest());
        Collections.reverse(issues);

        return issues;
    }

    public List<Issue> sortIssuesByOldest(List<Issue> issues) {
        // Сортируем как в предыдущем методе, затем реверсируем полученный список
        //List<Issue> result = getAllByComparator(new IssueComparatorByNewest());
        issues.sort(new IssueComparatorByOldest());

        return issues;
    }

    public List<Issue> sortIssuesByLeastCommented(List<Issue> issues) {
        issues.sort(new IssueComparatorByLeastCommented());

        return issues;
    }

    public List<Issue> sortIssuesByMostCommented(List<Issue> issues) {
        issues.sort(new IssueComparatorByLeastCommented());
        Collections.reverse(issues);

        return issues;
    }

    public List<Issue> sortIssuesByRecentlyUpdated(List<Issue> issues) {
        issues.sort(new IssueComparatorByLastRecentlyUpdated());
        Collections.reverse(issues);

        return issues;
    }

    public List<Issue> sortIssuesByLastRecentlyUpdated(List<Issue> issues) {
        issues.sort(new IssueComparatorByLastRecentlyUpdated());

        return issues;
    }

    /**
     * Получить список issues и отсортировать его по нужному компаратору
     */
    private List<Issue> getAllByComparator(Comparator<Issue> comparator) {
        List<Issue> result = repository.findAll();
        result.sort(comparator);

        return result;
    }

    public IssueRepository getRepository() {
        return repository;
    }

    public void setRepository(IssueRepository repository) {
        this.repository = repository;
    }
}
