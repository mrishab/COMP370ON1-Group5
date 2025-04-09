package io.trishul.classplanner.classdistribution.repository;

import org.springframework.stereotype.Repository;

import io.trishul.classplanner.classdistribution.model.ClassDistribution;
import io.trishul.classplanner.common.repository.BaseRepository;

@Repository
public interface ClassDistributionRepository extends BaseRepository<ClassDistribution, String> {
}
