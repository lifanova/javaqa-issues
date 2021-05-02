package ru.netology.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Issue;
import ru.netology.repository.IssueRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class IssueManagerTest {
    private IssueManager manager;
    private IssueRepository repository;

    @BeforeEach
    public void PrepareData() {
        repository = new IssueRepository();
        manager = new IssueManager(repository);
    }

    @Test
    public void shouldSetRepository() {
        manager.setRepository(repository);

        assertNotNull(manager.getRepository());
    }

    @Test
    public void shouldSaveIssue() {
        Issue issue = createIssueById(1);
        manager.saveIssue(issue);

        assertEquals(issue, manager.getIssueById(issue.getId()));
    }

    @Test
    public void shouldGetIssueById() {
        Issue issue = createIssueById(1);
        manager.saveIssue(issue);

        assertEquals(issue, manager.getIssueById(issue.getId()));
    }

    @Test
    public void shouldRemoveIssueById() {
        Issue issue = createIssueById(1);
        manager.saveIssue(issue);

        manager.removeIssue(issue.getId());

        assertNull(manager.getIssueById(issue.getId()));
    }

    @Test
    public void shouldOpenIssueById() {
        Issue issue = createIssueById(1);
        issue.setOpened(false);
        manager.saveIssue(issue);

        manager.openIssueById(issue.getId());

        assertTrue(manager.getIssueById(issue.getId()).isOpened());
    }

    @Test
    public void shouldCloseIssueById() {
        Issue issue = createIssueById(1);

        manager.saveIssue(issue);

        manager.closeIssueById(issue.getId());

        assertFalse(manager.getIssueById(issue.getId()).isOpened());
    }

    @Test
    public void shouldGetOpenedIssues() {
        createIssues(4);

        List<Issue> actual = manager.getOpenedIssues();

        assertEquals(2, actual.size());
    }

    @Test
    public void shouldGetClosedIssues() {
        createIssues(4);

        List<Issue> actual = manager.getClosedIssues();

        assertEquals(2, actual.size());
    }

    @Test
    public void shouldGetIssuesByAuthor() {
        createIssues(4);
        String author = "sbranner";

        List<Issue> actual = manager.getIssuesByAuthor(author);

        assertEquals(2, actual.size());
    }

    @Test
    public void shouldGetIssuesByMilestone() {
        createIssues(4);
        String milestone = "5.7.2";

        List<Issue> actual = manager.getIssuesByMilestone(milestone);

        assertEquals(4, actual.size());
    }

    @Test
    public void shouldGetIssuesByLabels() {
        createIssues(4);
        Set<String> labels = new HashSet<>();
        labels.add("type: task");

        List<Issue> actual = manager.getIssuesByLabels(labels);

        assertEquals(4, actual.size());
    }

    @Test
    public void shouldGetIssuesByAssignee() {
        createIssues(4);
        Set<String> assignee = new HashSet<>();
        assignee.add("sormuras");

        List<Issue> actual = manager.getIssuesByAssignee(assignee);

        assertEquals(4, actual.size());
    }

    @Test
    public void shouldSortIssuesByNewest() {
        List<Issue> issues = createIssues(4);

        Calendar calendar = new GregorianCalendar(2021, Calendar.MAY , 1);
        // Переустанавливаем для теста даты создания
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        issues.get(0).setCreatedDate(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        issues.get(1).setCreatedDate(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        issues.get(2).setCreatedDate(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        issues.get(3).setCreatedDate(calendar.getTime());

        // Пересохраняем с новыми датами
        manager.saveIssue(issues.get(0));
        manager.saveIssue(issues.get(1));
        manager.saveIssue(issues.get(2));
        manager.saveIssue(issues.get(3));

        Issue[] expected = {issues.get(2), issues.get(3), issues.get(0), issues.get(1)};
        List<Issue> actual = manager.sortIssuesByNewest(issues);

        /*for (Issue issue : actual) {
            System.out.println(issue.getId() + " -> " +issue.getCreatedDate());
        }*/

        assertArrayEquals(expected, actual.toArray());
    }

    @Test
    public void shouldSortIssuesByOldest() {
        List<Issue> issues = createIssues(4);
        Calendar calendar = new GregorianCalendar(2021, Calendar.MAY , 1);
        // Переустанавливаем для теста даты создания
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        issues.get(0).setCreatedDate(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        issues.get(1).setCreatedDate(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        issues.get(2).setCreatedDate(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        issues.get(3).setCreatedDate(calendar.getTime());

        // Пересохраняем с новыми датами
        manager.saveIssue(issues.get(0));
        manager.saveIssue(issues.get(1));
        manager.saveIssue(issues.get(2));
        manager.saveIssue(issues.get(3));

        Issue[] expected = {issues.get(1), issues.get(0), issues.get(3), issues.get(2)};

        List<Issue> actual = manager.sortIssuesByOldest(issues);

        assertArrayEquals(expected, actual.toArray());
    }

    @Test
    public void shouldSortIssuesByLeastCommented() {
        List<Issue> issues = createIssues(4);
        String[] comments = {"Test Comment 1", "Test Comment 2", "Test Comment 3", "Test Comment 4"};
        //2
        issues.get(0).getComments().add(comments[0]);
        issues.get(0).getComments().add(comments[1]);
        //1
        issues.get(1).getComments().add(comments[0]);
        //0
        //4
        issues.get(3).getComments().add(comments[0]);
        issues.get(3).getComments().add(comments[1]);
        issues.get(3).getComments().add(comments[2]);
        issues.get(3).getComments().add(comments[3]);

        // Пересохраняем с комментариями
        manager.saveIssue(issues.get(0));
        manager.saveIssue(issues.get(1));
        manager.saveIssue(issues.get(2));
        manager.saveIssue(issues.get(3));

        Issue[] expected = {issues.get(2), issues.get(1), issues.get(0), issues.get(3)};

        List<Issue> actual = manager.sortIssuesByLeastCommented(issues);
        System.out.println("Sorted: ");
        for (Issue issue : actual) {
            System.out.println(issue.getId());
        }

        assertArrayEquals(expected, actual.toArray());
    }

    @Test
    public void shouldSortIssuesByMostCommented() {
        List<Issue> issues = createIssues(4);
        String[] comments = {"Test Comment 1", "Test Comment 2", "Test Comment 3", "Test Comment 4"};
        //2
        issues.get(0).getComments().add(comments[0]);
        issues.get(0).getComments().add(comments[1]);
        //1
        issues.get(1).getComments().add(comments[0]);
        //0
        //4
        issues.get(3).getComments().add(comments[0]);
        issues.get(3).getComments().add(comments[1]);
        issues.get(3).getComments().add(comments[2]);
        issues.get(3).getComments().add(comments[3]);

        // Пересохраняем с комментариями
        manager.saveIssue(issues.get(0));
        manager.saveIssue(issues.get(1));
        manager.saveIssue(issues.get(2));
        manager.saveIssue(issues.get(3));

        Issue[] expected = {issues.get(3), issues.get(0), issues.get(1), issues.get(2)};

        List<Issue> actual = manager.sortIssuesByMostCommented(issues);
        System.out.println("Sorted: ");
        for (Issue issue : actual) {
            System.out.println(issue.getId());
        }

        assertArrayEquals(expected, actual.toArray());
    }

    @Test
    public void shouldSortIssuesByRecentlyUpdated() {
        List<Issue> issues = createIssues(4);
        Calendar calendar = new GregorianCalendar(2021, Calendar.MAY , 1);
        // Переустанавливаем для теста даты создания
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        issues.get(0).setUpdatedDate(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        issues.get(1).setUpdatedDate(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        issues.get(2).setUpdatedDate(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        issues.get(3).setUpdatedDate(calendar.getTime());

        // Пересохраняем с новыми датами
        manager.saveIssue(issues.get(0));
        manager.saveIssue(issues.get(1));
        manager.saveIssue(issues.get(2));
        manager.saveIssue(issues.get(3));

        Issue[] expected = {issues.get(2), issues.get(3), issues.get(0), issues.get(1)};

        List<Issue> actual = manager.sortIssuesByRecentlyUpdated(issues);

        System.out.println("shouldSortIssuesByRecentlyUpdated");
        for (Issue issue : actual) {
            System.out.println(issue.getId() + " -> " +issue.getUpdatedDate());
        }

        assertArrayEquals(expected, actual.toArray());
    }

    @Test
    public void shouldSortIssuesByLastRecentlyUpdated() {
        List<Issue> issues = createIssues(4);
        Calendar calendar = new GregorianCalendar(2021, Calendar.MAY , 1);
        // Переустанавливаем для теста даты создания
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        issues.get(0).setUpdatedDate(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        issues.get(1).setUpdatedDate(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        issues.get(2).setUpdatedDate(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        issues.get(3).setUpdatedDate(calendar.getTime());

        // Пересохраняем с новыми датами
        manager.saveIssue(issues.get(0));
        manager.saveIssue(issues.get(1));
        manager.saveIssue(issues.get(2));
        manager.saveIssue(issues.get(3));

        Issue[] expected = {issues.get(1), issues.get(0), issues.get(3), issues.get(2)};

        List<Issue> actual = manager.sortIssuesByLastRecentlyUpdated(issues);

        System.out.println("shouldSortIssuesByLastRecentlyUpdated");
        for (Issue issue : actual) {
            System.out.println(issue.getId() + " -> " +issue.getUpdatedDate());
        }

        assertArrayEquals(expected, actual.toArray());
    }

    private Issue createIssueById(int id) {
        Issue issue = new Issue();
        issue.setId(id);
        issue.setName("Enable stalebot");
        issue.setDescription("We should enable Stalebot to comment on issues ");
        issue.setOpened(true);
        issue.setAuthor("marcphilipp");
        issue.setMilestone("5.7.2");
        issue.setCreatedDate(new Date());
        issue.setUpdatedDate(new Date());

        // Labels
        Set<String> labels = new HashSet<>();
        labels.add("status: team discussion");
        labels.add("type: task");
        issue.setLabels(labels);

        // Assignee
        Set<String> assignee = new HashSet<>();
        assignee.add("sormuras");
        assignee.add("marcphilipp");
        issue.setAssignee(assignee);

        return issue;
    }

    private List<Issue> createIssues(int size) {
        List<Issue> issues = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Issue issue = createIssueById(i);
            if (i % 2 == 0) {
                issue.setOpened(false);
                issue.setAuthor("sbranner");
            }

            manager.saveIssue(issue);
            issues.add(issue);
        }

        return issues;
    }
}
