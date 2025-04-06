package io.trishul.classplanner.classschedule.controller;

import io.trishul.classplanner.classschedule.controller.dto.GetClassScheduleDTO;
import io.trishul.classplanner.classschedule.controller.dto.PostClassScheduleDTO;
import io.trishul.classplanner.classschedule.controller.dto.PutClassScheduleDTO;
import io.trishul.classplanner.classschedule.controller.dto.mapper.ClassScheduleMapper;
import io.trishul.classplanner.classschedule.model.ClassSchedule;
import io.trishul.classplanner.classschedule.repository.ClassScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/class-schedules")
public class ClassScheduleController {
    @Autowired
    private ClassScheduleRepository repository;

    @Autowired
    private ClassScheduleMapper mapper;

    @GetMapping
    public List<GetClassScheduleDTO> getAll() {
        ClassSchedule probe = new ClassSchedule();
        probe.setArchived(false);
        return repository.findAll(Example.of(probe))
                .stream()
                .map(mapper::toGetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetClassScheduleDTO> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(mapper::toGetDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public GetClassScheduleDTO create(@RequestBody PostClassScheduleDTO dto) {
        ClassSchedule entity = mapper.toEntity(dto);
        return mapper.toGetDTO(repository.save(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetClassScheduleDTO> update(@PathVariable Long id, @RequestBody PutClassScheduleDTO dto) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntity(entity, dto);
                    return mapper.toGetDTO(repository.save(entity));
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setArchived(true);
                    repository.save(entity);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
