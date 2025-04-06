package io.trishul.classplanner.course.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import io.trishul.classplanner.course.controller.dto.GetCourseDTO;
import io.trishul.classplanner.course.controller.dto.PostCourseDTO;
import io.trishul.classplanner.course.controller.dto.PutCourseDTO;
import io.trishul.classplanner.course.controller.dto.mapper.CourseMapper;
import io.trishul.classplanner.course.model.Course;
import io.trishul.classplanner.course.repository.CourseRepository;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    @Autowired
    private CourseRepository repository;

    @Autowired
    private CourseMapper mapper;

    @GetMapping
    public List<GetCourseDTO> getCourses() {
        return repository.findAll().stream()
                .map(mapper::toGetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCourseDTO> getCourse(@PathVariable Long id) {
        return repository.findById(id)
                .map(mapper::toGetDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public List<GetCourseDTO> createCourses(@RequestBody List<PostCourseDTO> dtos) {
        List<Course> courses = dtos.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());
        return repository.saveAll(courses).stream()
                .map(mapper::toGetDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<GetCourseDTO> updateCourse(@PathVariable Long id, @RequestBody PutCourseDTO dto) {
        return repository.findById(id)
                .map(course -> {
                    mapper.updateEntity(course, dto);
                    return mapper.toGetDTO(repository.save(course));
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deleteCourses(@RequestBody List<Long> ids) {
        repository.softDelete(ids);
        return ResponseEntity.noContent().build();
    }
}
