package com.ifood.repository;

import com.ifood.domain.CookBookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CookBookRepository  extends CrudRepository<CookBookEntity, Integer> {
    CookBookEntity findByUserIdAndName(String userId, String name);
    List<CookBookEntity> findByUserId(String userId);
    CookBookEntity findById(String cookbookId);
}
