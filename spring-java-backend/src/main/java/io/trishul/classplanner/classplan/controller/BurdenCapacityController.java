package io.trishul.classplanner.classplan.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.trishul.classplanner.classplan.controller.dto.GetBurdenCapacityDTO;
import io.trishul.classplanner.classplan.controller.dto.PostBurdenCapacityDTO;
import io.trishul.classplanner.classplan.controller.dto.PutBurdenCapacityDTO;
import io.trishul.classplanner.classplan.controller.dto.mapper.BurdenCapacityMapper;
import io.trishul.classplanner.classplan.model.BurdenCapacity;
import io.trishul.classplanner.classplan.repository.BurdenCapacityRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/burden-capacity")
@RequiredArgsConstructor
public class BurdenCapacityController {
    private final BurdenCapacityRepository repository;
    private final BurdenCapacityMapper mapper;

    @GetMapping
    public List<GetBurdenCapacityDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toGetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GetBurdenCapacityDTO getById(@PathVariable Long id) {
        return mapper.toGetDTO(repository.getReferenceById(id));
    }

    @PostMapping
    public List<GetBurdenCapacityDTO> create(@RequestBody List<PostBurdenCapacityDTO> dtos) {
        List<BurdenCapacity> entities = dtos.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());
        return repository.saveAll(entities).stream()
                .map(mapper::toGetDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public GetBurdenCapacityDTO update(@PathVariable Long id, @RequestBody PutBurdenCapacityDTO dto) {
        BurdenCapacity entity = repository.getReferenceById(id);
        mapper.updateEntity(entity, dto);
        return mapper.toGetDTO(repository.save(entity));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        BurdenCapacity entity = repository.getReferenceById(id);
        entity.setArchived(true);
        repository.save(entity);
    }
}
