package io.trishul.classplanner.availability.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import io.trishul.classplanner.availability.controller.dto.GetAvailabilityDTO;
import io.trishul.classplanner.availability.controller.dto.PostAvailabilityDTO;
import io.trishul.classplanner.availability.controller.dto.PutAvailabilityDTO;
import io.trishul.classplanner.availability.controller.dto.mapper.AvailabilityMapper;
import io.trishul.classplanner.availability.model.Availability;
import io.trishul.classplanner.availability.repository.AvailabilityRepository;

@RestController
@RequestMapping("/api/availabilities")
public class AvailabilityController {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private AvailabilityMapper availabilityMapper;

    @GetMapping
    public ResponseEntity<List<GetAvailabilityDTO>> getAllAvailabilities() {
        List<GetAvailabilityDTO> availabilities = availabilityRepository.findAll().stream()
                .filter(availability -> !availability.isArchived())
                .map(availabilityMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(availabilities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAvailabilityDTO> getAvailability(@PathVariable Long id) {
        return availabilityRepository.findById(id)
                .filter(availability -> !availability.isArchived())
                .map(availabilityMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<GetAvailabilityDTO> createAvailability(@RequestBody PostAvailabilityDTO availabilityDTO) {
        Availability availability = availabilityMapper.toEntity(availabilityDTO);
        availability = availabilityRepository.save(availability);
        return ResponseEntity.status(HttpStatus.CREATED).body(availabilityMapper.toDTO(availability));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<GetAvailabilityDTO> updateAvailability(@PathVariable Long id, @RequestBody PutAvailabilityDTO availabilityDTO) {
        return availabilityRepository.findById(id)
                .filter(availability -> !availability.isArchived())
                .map(availability -> {
                    Availability updated = availabilityMapper.toEntity(availabilityDTO);
                    updated.setId(availability.getId());
                    updated = availabilityRepository.save(updated);
                    return ResponseEntity.ok(availabilityMapper.toDTO(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long id) {
        return availabilityRepository.findById(id)
                .filter(availability -> !availability.isArchived())
                .map(availability -> {
                    availability.setArchived(true);
                    availabilityRepository.save(availability);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
