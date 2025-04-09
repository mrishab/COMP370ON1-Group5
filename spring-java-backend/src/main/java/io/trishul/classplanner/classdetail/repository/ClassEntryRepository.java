package io.trishul.classplanner.classdetail.repository;

import org.springframework.stereotype.Repository;

import io.trishul.classplanner.classdetail.model.ClassEntry;
import io.trishul.classplanner.common.repository.BaseRepository;

@Repository
public interface ClassEntryRepository extends BaseRepository<ClassEntry, Long> {
}
