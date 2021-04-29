package ru.netology.manager;

import ru.netology.domain.Issue;
import ru.netology.domain.IssueComparatorByMostCommented;
import ru.netology.domain.IssueComparatorByNewest;
import ru.netology.domain.IssueComparatorByRecentlyUpdated;
import ru.netology.repository.IssueRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IssueManager {
    private IssueRepository repository;

    public IssueManager(IssueRepository repository) {
        this.repository = repository;
    }

    /**
     * Сохранить issue
     * */
    public void saveIssue(Issue issue){
        repository.save(issue);
    }

    /**
     * Получить issue по id
     * */
    public Issue getIssueById(int id){
       return repository.findById(id);
    }

    /**
     * Удалить issue по id
     * */
    public void removeIssue(int id){
        repository.removeById(id);
    }

    /**
     * Открыть issue по id
     * */
    public void openIssueById(int id){
        setOpenedStatus(id, true);
    }

    /**
     * Закрыть issue по id
     * */
    public void closeIssueById(int id){
        setOpenedStatus(id, false);
    }

    private void setOpenedStatus(int id, boolean opened){
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
     * */
    public List<Issue> getOpenedIssues(){
        return repository.findAll();
    }

    /**
     * Получить все закрытые issues
     * */
    public List<Issue> getClosedIssues(){
        return repository.findAll();
    }

    /* Сортировка */
    public List<Issue> getAllByNewest(){
        return getAllByComparator(new IssueComparatorByNewest());
    }

    public List<Issue> getAllByOldest(){
        List<Issue> result = getAllByComparator(new IssueComparatorByNewest());
        Collections.reverse(result);

        return result;
    }

    public List<Issue> getAllByMostCommented(){
        return getAllByComparator(new IssueComparatorByMostCommented());
    }

    public List<Issue> getAllByLeastCommented(){
        List<Issue> result = getAllByComparator(new IssueComparatorByMostCommented());
        Collections.reverse(result);

        return result;
    }

    public List<Issue> getAllByRecentlyUpdated(){
        return getAllByComparator(new IssueComparatorByRecentlyUpdated());
    }

    public List<Issue> getAllByLastRecentlyUpdated(){
        List<Issue> result = getAllByComparator(new IssueComparatorByRecentlyUpdated());
        Collections.reverse(result);

        return result;
    }

    /**
     * Получить список issues и отсортировать его по нужному компаратору
     * */
    private List<Issue> getAllByComparator(Comparator<Issue> comparator){
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
