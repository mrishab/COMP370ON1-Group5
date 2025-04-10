package io.trishul.classplanner.common.repository;

import java.util.List;

import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
@SQLRestriction("archived <> true")
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.archived = true WHERE e.id IN :ids AND (?#{#probe} IS NULL OR e IN (SELECT x FROM #{#entityName} x WHERE x IN ?#{#probe}))")
    Integer softDelete(Iterable<ID> ids, Example<T> probe);

    @Modifying
    @Query("UPDATE #{#entityName} e SET e.archived = false WHERE e.id IN :ids AND (?#{#probe} IS NULL OR e IN (SELECT x FROM #{#entityName} x WHERE x IN ?#{#probe}))")
    Integer restore(Iterable<ID> ids, Example<T> probe);

    @Query("SELECT e FROM #{#entityName} e WHERE e.archived = true AND (?#{#probe} IS NULL OR e IN (SELECT x FROM #{#entityName} x WHERE x IN ?#{#probe}))")
    List<T> findAllArchived(Example<T> probe);
}
