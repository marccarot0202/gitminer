package aiss.gitminer.controller;

import aiss.gitminer.exceptions.ProjectNotFoundException;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    // Find projects with params: page, size, sortBy, direction and filter
    @GetMapping
    public ResponseEntity<Page<Project>> getProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String filter) {

        Sort sort = Sort.by(Sort.Order.asc(sortBy));
        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(Sort.Order.desc(sortBy));
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Project> projects;
        if (filter != null && !filter.isEmpty()) {
            projects = projectRepository.findByNameContaining(filter, pageable);
        } else {
            projects = projectRepository.findAll(pageable);
        }
        return ResponseEntity.ok(projects);
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
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        Project savedProject = projectRepository.save(project);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }
}