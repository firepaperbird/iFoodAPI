package com.ifood.repository;

import com.ifood.domain.IngredientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends CrudRepository<IngredientEntity, String> {
    @Query("SELECT ingredient as Ingredient FROM DishEntity dish join DishIngredientEntity di on dish.id = di.dishId" +
            " join IngredientEntity ingredient on di.dishIngredient.id = ingredient.id" +
            " WHERE dish.id = :dishId AND di.dishId = :dishId AND ingredient.delete = false")
    List<IngredientEntity> findByDishId (@Param("dishId") String dishId);
}
