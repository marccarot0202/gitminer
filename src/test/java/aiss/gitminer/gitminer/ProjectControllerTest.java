package aiss.gitminer.gitminer;

import aiss.gitminer.controller.ProjectController;
import aiss.gitminer.exceptions.ProjectNotFoundException;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ProjectControllerTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectController projectController;

    private MockMvc mockMvc;

    public ProjectControllerTest() {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    public void testFindProjectByIdFound() throws Exception {
        Project project = new Project();
        project.setId("1");
        project.setName("Project 1");

        when(projectRepository.findById("1")).thenReturn(Optional.of(project));

        mockMvc.perform(get("/gitminer/projects/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value("1"))
               .andExpect(jsonPath("$.name").value("Project 1"));
    }

    @Test
    public void testFindProjectByIdNotFound() throws Exception {
        when(projectRepository.findById("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/gitminer/projects/1"))
               .andExpect(status().isNotFound());
    }

    @Test
    public void testGetProjects() throws Exception {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Project> projects = mock(Page.class);

        when(projectRepository.findAll(pageable)).thenReturn(projects);

        mockMvc.perform(get("/gitminer/projects"))
               .andExpect(status().isOk());
    }

    @Test
    public void testCreateProject() throws Exception {
        Project project = new Project();
        project.setName("New Project");

        when(projectRepository.save(project)).thenReturn(project);

        mockMvc.perform(post("/gitminer/projects")
               .contentType("application/json")
               .content("{\"name\":\"New Project\"}"))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.name").value("New Project"));
    }
}