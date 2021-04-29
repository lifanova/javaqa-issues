package ru.netology.repository;

import ru.netology.domain.Issue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class IssueRepository {
    private List<Issue> issues = new ArrayList<>();

    /**
     * Сохранить элемент
     * */
    public  void save(Issue issue){
        if (issue == null) {
            System.out.println("[save]: Error: null entity!");
            return;
        }

       /* Если эемент уже есть в нашей коллекции */
        if (issues.contains(issue)) {
            // Обновляем существующий
            issues.set(issues.indexOf(issue), issue);
        } else {
            // Добавляем новый
            issues.add(issue);
        }
    }

    /**
     * Получить элемент по ID
     * */
    public Issue findById(int id){
        for (Issue item : issues) {
            if (item.getId() == id) {
                return item;
            }
        }

        return null;
    }

    /**
     * Удалить элемент по ID
     * */
    public void removeById(int id){
        issues.removeIf(item -> item.getId() == id);
    }

    /**
     * Получить все элементы
     * */
    public List<Issue> findAll(){
        return getIssues();
    }

    /**
     * Фильтрация по параметрам
     * */
    public List<Issue> filterByPredicate(Predicate<Issue> predicate){
        List<Issue> openedIssues = findAll()
                .stream()
                .filter(i -> predicate.test(i))
                .collect(Collectors.toList());

        return openedIssues;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }


}
