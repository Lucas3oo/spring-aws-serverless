package se.solrike.cloud.serverless.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interface for all repositories
 *
 * @param <T>
 */
@NoRepositoryBean
public interface EntityRepository<T extends AbstractEntity> extends JpaRepository<T, Integer> {

}
