package ru.netology.domain;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class Issue {
    private int id;
    /* Название, заголовок issue */
    private String name;
    /* Текст issue */
    private String description;

    /* Issue открыт/закрыт */
    private boolean isOpened;
    /* Автор, кто создал */
    private String author;

    /* Метки (категории) */
    private Set<String> labels;

    /* Связанные с issue проекты */
    private Set<String> projects;

    /* Этап; мера, на сколько выполнена задача */
    private String milestone;

    /* На кого назначено */
    private Set<String> assignee;

    /* Комментарии */
    private Set<String> comments;
    /* Pull requests, связанные с данным issue */
    private Set<String> pullRequests;
    /* Дата создания */
    private Date createdDate;
    /* Дата последнего обновления */
    private Date updatedDate;

    public Issue() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public void setLabels(Set<String> labels) {
        this.labels = labels;
    }

    public Set<String> getProjects() {
        return projects;
    }

    public void setProjects(Set<String> projects) {
        this.projects = projects;
    }

    public String getMilestone() {
        return milestone;
    }

    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }

    public Set<String> getAssignee() {
        return assignee;
    }

    public void setAssignee(Set<String> assignee) {
        this.assignee = assignee;
    }

    public Set<String> getComments() {
        return comments;
    }

    public void setComments(Set<String> comments) {
        this.comments = comments;
    }

    public Set<String> getPullRequests() {
        return pullRequests;
    }

    public void setPullRequests(Set<String> pullRequests) {
        this.pullRequests = pullRequests;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return id == issue.id &&
                isOpened == issue.isOpened &&
                Objects.equals(name, issue.name) &&
                Objects.equals(description, issue.description) &&
                Objects.equals(author, issue.author) &&
                Objects.equals(labels, issue.labels) &&
                Objects.equals(projects, issue.projects) &&
                Objects.equals(milestone, issue.milestone) &&
                Objects.equals(assignee, issue.assignee) &&
                Objects.equals(comments, issue.comments) &&
                Objects.equals(pullRequests, issue.pullRequests) &&
                Objects.equals(createdDate, issue.createdDate) &&
                Objects.equals(updatedDate, issue.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, isOpened, author, labels, projects, milestone, assignee, comments, pullRequests, createdDate, updatedDate);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isOpened=" + isOpened +
                ", author='" + author + '\'' +
                ", labels=" + labels +
                ", projects=" + projects +
                ", milestone='" + milestone + '\'' +
                ", assignee=" + assignee +
                ", comments=" + comments +
                ", pullRequests=" + pullRequests +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
