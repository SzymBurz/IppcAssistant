package com.wtd.assistant.frontend.dao;

import com.wtd.assistant.frontend.domain.FileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDao extends CrudRepository<FileEntity, Integer> {

    /*
    private final EntityManager entityManager;

    @Autowired
    public FileDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveFile(FileEntity fileEntity) {
        entityManager.persist(fileEntity);
    }

    public FileEntity getFileById(int id) {
        return entityManager.find(FileEntity.class, id);
    }
    */
}
