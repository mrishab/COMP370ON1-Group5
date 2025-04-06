package io.trishul.classplanner.classdistribution.controller;

import io.trishul.classplanner.classdistribution.controller.dto.GetClassDistributionDTO;
import io.trishul.classplanner.classdistribution.controller.dto.PostClassDistributionDTO;
import io.trishul.classplanner.classdistribution.controller.dto.PutClassDistributionDTO;
import io.trishul.classplanner.classdistribution.controller.dto.mapper.ClassDistributionMapper;
import io.trishul.classplanner.classdistribution.model.ClassDistribution;
import io.trishul.classplanner.classdistribution.repository.ClassDistributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/class-distributions")
public class ClassDistributionController {

    @Autowired
    private ClassDistributionRepository repository;

    @Autowired
    private ClassDistributionMapper mapper;

    @GetMapping
    public List<GetClassDistributionDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toGetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetClassDistributionDTO> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(mapper::toGetDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public GetClassDistributionDTO create(@RequestBody PostClassDistributionDTO dto) {
        ClassDistribution entity = mapper.toEntity(dto);
        return mapper.toGetDTO(repository.save(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetClassDistributionDTO> update(@PathVariable Long id, @RequestBody PutClassDistributionDTO dto) {
        Optional<ClassDistribution> optionalEntity = repository.findById(id);
        if (optionalEntity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ClassDistribution entity = optionalEntity.get();
        mapper.updateEntity(entity, dto);
        return ResponseEntity.ok(mapper.toGetDTO(repository.save(entity)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<ClassDistribution> optionalEntity = repository.findById(id);
        if (optionalEntity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ClassDistribution entity = optionalEntity.get();
        entity.setArchived(true);
        repository.save(entity);
        return ResponseEntity.ok().build();
    }
}
