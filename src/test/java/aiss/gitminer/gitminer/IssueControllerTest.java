package aiss.gitminer.gitminer;

import aiss.gitminer.exceptions.IssueNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import aiss.gitminer.controller.IssueController; // Ensure this is the correct package for IssueController

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IssueControllerTest {

    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private IssueController issueController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(issueController).build();
    }

    @Test
    public void testFindIssueByIdFound() throws Exception {
        Issue issue = new Issue();
        issue.setId("1");
        issue.setTitle("Issue 1");

        when(issueRepository.findById("1")).thenReturn(Optional.of(issue));

        mockMvc.perform(get("/gitminer/issues/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value("1"))
               .andExpect(jsonPath("$.title").value("Issue 1"));
    }

    @Test
    public void testFindIssueByIdNotFound() throws Exception {
        when(issueRepository.findById("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/gitminer/issues/1"))
               .andExpect(status().isNotFound());
    }

    @Test
    public void testFindIssuesByAuthor() throws Exception {
        Issue issue = new Issue();
        issue.setId("1");
        issue.setTitle("Issue by Author");

        when(issueRepository.findByAuthorId("author1")).thenReturn(List.of(issue));

        mockMvc.perform(get("/gitminer/issues?authorId=author1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value("1"));
    }

    @Test
    public void testFindIssuesByState() throws Exception {
        Issue issue = new Issue();
        issue.setId("1");
        issue.setTitle("Open Issue");

        when(issueRepository.findByState("open")).thenReturn(List.of(issue));

        mockMvc.perform(get("/gitminer/issues?state=open"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value("1"));
    }

    @Test
    public void testGetIssueComments() throws Exception {
        Issue issue = new Issue();
        issue.setId("1");
        issue.setTitle("Issue 1");
        Comment comment = new Comment();
        comment.setId("1");

        issue.setComments(List.of(comment));

        when(issueRepository.findById("1")).thenReturn(Optional.of(issue));

        mockMvc.perform(get("/gitminer/issues/1/comments"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value("1"))
               .andExpect(jsonPath("$[0].message").value("Comment 1"));
    }
}
