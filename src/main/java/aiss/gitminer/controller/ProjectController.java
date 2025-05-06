package aiss.gitminer.controller;

import aiss.gitminer.exceptions.ProjectNotFoundException;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/projects")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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
    public Project createProject(@RequestBody Project project) {
        return projectRepository.save(project);
    }
}
