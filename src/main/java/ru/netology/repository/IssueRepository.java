package ru.netology.repository;

import ru.netology.domain.Issue;

import java.util.ArrayList;
import java.util.List;

public class IssueRepository {
    private List<Issue> issues = new ArrayList<>();

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

    public Issue findById(int id){
        for (Issue item : issues) {
            if (item.getId() == id) {
                return item;
            }
        }

        return null;
    }

    public List<Issue> findAll(){
        return getIssues();
    }

    public void removeById(int id){
        issues.removeIf(item -> item.getId() == id);
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }


}
