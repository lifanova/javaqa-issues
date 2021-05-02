package ru.netology.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Issue;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class IssueRepositoryTest {
    private IssueRepository repository;


    @BeforeEach
    public void PrepareData() {
        repository = new IssueRepository();
    }

    @Test
    public void shouldSaveOneItem() {
        Issue issue = createIssueById(1);

        repository.save(issue);

        Issue actual = repository.findById(issue.getId());

        assertEquals(issue, actual);
    }

    @Test
    public void shouldSaveComplexItem() {
        Issue issue = createIssueById(1);

        // Labels
        Set<String> labels = new HashSet<>();
        labels.add("status: team discussion");
        labels.add("type: task");
        issue.setLabels(labels);

        // Projects
        Set<String> projects = new HashSet<>();
        projects.add("Junit 4");
        projects.add("Junit 5");
        issue.setProjects(projects);

        issue.setMilestone("5.8 M2");

        // Assignee
        Set<String> assignee = new HashSet<>();
        assignee.add("sormuras");
        assignee.add("marcphilipp");
        issue.setAssignee(assignee);

        // Comments
        Set<String> comments = new HashSet<>();
        comments.add("Test 1");
        comments.add("Test 2");
        comments.add("Test 3");
        issue.setComments(comments);

        // Pull Requests
        Set<String> pullRequests = new HashSet<>();
        pullRequests.add("Additional example");
        pullRequests.add("Provide a way to run tests");
        issue.setPullRequests(pullRequests);

        //System.out.println("[shouldSaveComplexItem]" + issue.toString());
        repository.save(issue);

        Issue actual = repository.findById(issue.getId());

        assertEquals(issue, actual);
    }

    @Test
    public void shouldFindByNonExistingId() {
        assertNull(repository.findById(2));
    }

    @Test
    public void shouldRemoveById() {
        Issue issue = createIssueById(1);

        repository.save(issue);
        repository.removeById(issue.getId());

        assertNull(repository.findById(issue.getId()));
    }

    @Test
    public void shouldFindAll() {
        List<Issue> expected = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Issue issue = createIssueById(i);
            repository.save(issue);
            expected.add(issue);
        }

        List<Issue> actual = repository.findAll();

        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void filterByPredicate(){
        List<Issue> expected = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Issue issue = createIssueById(i);
            repository.save(issue);
            expected.add(issue);
        }

        Predicate<Issue> predicate = (issue) -> issue.isOpened();
        List<Issue> actual = repository.filterByPredicate(predicate);

        assertEquals(expected.size(), actual.size());
    }

    private Issue createIssueById(int id) {
        Issue issue = new Issue();
        issue.setId(id);
        issue.setName("Enable stalebot");
        issue.setDescription("We should enable Stalebot to comment on issues ");
        issue.setOpened(true);
        issue.setAuthor("marcphilipp");
        issue.setCreatedDate(new Date());
        issue.setUpdatedDate(new Date());

        return issue;
    }

}
