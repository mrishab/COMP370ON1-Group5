package io.trishul.classplanner.courseclass.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.trishul.classplanner.course.model.Course;
import io.trishul.classplanner.courseclass.controller.dto.GetCourseClassDTO;
import io.trishul.classplanner.courseclass.controller.dto.PostCourseClassDTO;
import io.trishul.classplanner.courseclass.controller.dto.PutCourseClassDTO;
import io.trishul.classplanner.courseclass.controller.dto.mapper.CourseClassMapper;
import io.trishul.classplanner.courseclass.model.CourseClass;
import io.trishul.classplanner.courseclass.repository.CourseClassRepository;

@RestController
@RequestMapping("/api/v1/classes")
public class CourseClassController {
    @Autowired
    private CourseClassRepository repository;

    @Autowired
    private CourseClassMapper mapper;

    @GetMapping
    public List<GetCourseClassDTO> getClasses() {
        return repository.findAll().stream()
                .map(mapper::toGetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCourseClassDTO> getClass(@PathVariable Long id) {
        return repository.findById(id)
                .map(mapper::toGetDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public GetCourseClassDTO createClass(@RequestBody PostCourseClassDTO dto) {
        CourseClass courseClass = mapper.toEntity(dto);
        return mapper.toGetDTO(repository.save(courseClass));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<GetCourseClassDTO> updateClass(@PathVariable Long id, @RequestBody PutCourseClassDTO dto) {
        return repository.findById(id)
                .map(courseClass -> {
                    mapper.updateEntity(courseClass, dto);
                    return mapper.toGetDTO(repository.save(courseClass));
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deleteClasses(@RequestBody List<Long> ids) {
        repository.softDelete(ids);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/course/{courseId}")
    public List<GetCourseClassDTO> getClassesByCourseId(@PathVariable Long courseId) {
        CourseClass probe = new CourseClass();
        probe.setCourse(Course.builder().id(courseId).build());

        return repository.findAll(Example.of(probe))
                .stream()
                .map(mapper::toGetDTO)
                .collect(Collectors.toList());
    }
}
