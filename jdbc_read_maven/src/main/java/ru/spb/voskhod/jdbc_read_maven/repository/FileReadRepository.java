package ru.spb.voskhod.jdbc_read_maven.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spb.voskhod.jdbc_read_maven.model.ReadFile;

@Repository
public class FileReadRepository {

    @Transactional(readOnly = true)
    public ReadFile findById(Long id) {


        return null;
    }

}
