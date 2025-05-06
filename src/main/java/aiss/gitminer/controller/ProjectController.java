package aiss.gitminer.controller;

import aiss.gitminer.exceptions.ProjectNotFoundException;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gitminer/projects")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @GetMapping("/{id}")
    public Project findById(@PathVariable String id) throws ProjectNotFoundException {
        Optional<Project> project = projectRepository.findById(id);
        if (!project.isPresent()) {
            throw new ProjectNotFoundException();
        }
        return project.get();
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project savedProject = projectRepository.save(project);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
        }
    }