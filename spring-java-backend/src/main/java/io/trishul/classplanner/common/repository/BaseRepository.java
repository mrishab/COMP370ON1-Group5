package io.trishul.classplanner.common.repository;

import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
@SQLRestriction("archived <> true")
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.archived = true WHERE e.id IN :ids")
    Long softDelete(Iterable<ID> ids);
}
