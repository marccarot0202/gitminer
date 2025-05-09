package aiss.gitminer.gitminer;

import aiss.gitminer.exceptions.CommitNotFoundException;
import aiss.gitminer.repository.CommitRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import aiss.gitminer.controller.CommitController;
import java.util.Optional;
import aiss.gitminer.model.Commit; // Ensure this is the correct package for the Commit class

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class CommitControllerTest {

    @Mock
    private CommitRepository commitRepository;

    @InjectMocks
    private CommitController commitController;

    private MockMvc mockMvc;

    public CommitControllerTest() {
        mockMvc = MockMvcBuilders.standaloneSetup(commitController).build();
    }

    @Test
    public void testFindCommitByIdFound() throws Exception {
        // Aquí simulamos el repositorio devolviendo un Commit cuando se consulta por ID
        Commit commit = new Commit();
        commit.setId("1");
        commit.setMessage("Commit message");

        when(commitRepository.findById("1")).thenReturn(Optional.of(commit));

        mockMvc.perform(get("/gitminer/commits/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value("1"))
               .andExpect(jsonPath("$.message").value("Commit message"));
    }

    @Test
    public void testFindCommitByIdNotFound() throws Exception {
        // Simulamos que el repositorio no encuentra el commit
        when(commitRepository.findById("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/gitminer/commits/1"))
               .andExpect(status().isNotFound());
    }

    @Test
    public void testFindCommitByEmail() throws Exception {
        // Simulamos un commit con un email específico
        Commit commit = new Commit();
        commit.setId("1");
        commit.setMessage("Commit from author@example.com");
        commit.setAuthorEmail("author@example.com");

        when(commitRepository.findByAuthorEmail("author@example.com")).thenReturn(Optional.of(commit));

        mockMvc.perform(get("/gitminer/commits?email=author@example.com"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value("1"))
               .andExpect(jsonPath("$[0].message").value("Commit from author@example.com"));
    }

    @Test
    public void testFindAllCommits() throws Exception {
        mockMvc.perform(get("/gitminer/commits"))
               .andExpect(status().isOk());
    }
}