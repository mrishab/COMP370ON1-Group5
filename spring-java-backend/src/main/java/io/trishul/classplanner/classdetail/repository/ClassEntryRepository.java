package io.trishul.classplanner.classdetail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.trishul.classplanner.classdetail.model.ClassEntry;

@Repository
public interface ClassEntryRepository extends JpaRepository<ClassEntry, Long> {
}
