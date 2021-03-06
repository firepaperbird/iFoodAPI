package com.ifood.service;

import com.ifood.domain.*;
import com.ifood.domain.model.RelatedDish;
import com.ifood.domain.model.ReviewByUser;
import com.ifood.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ifood.config.Constants.SUCCESS;

@Service
@Slf4j
public class DishService {
    @Autowired(required = true)
    private DishRepository dishRepository;
    @Autowired(required = true)
    private IngredientRepository ingredientRepository;
    @Autowired(required = true)
    private ReviewRepository reviewRepository;
    @Autowired(required = true)
    private CourseRepository courseRepository;
    @Autowired(required = true)
    private StepByStepRepository stepByStepRepository;

    public ResponseEntity<Object> getDishesById (String dishId){
        HttpHeaders responseHeaders = new HttpHeaders();
        Optional<DishEntity> dishEntity = null;
        DishEntity dish = null;
        try {
            dishEntity = dishRepository.findById(dishId);
            dish = dishEntity.get();

            //Courses
            List<CourseEntity> courses = courseRepository.findByDishId(dish.getId());
            dish.setCourses(courses);

            //Ingredients
            List<IngredientEntity> ingredients = ingredientRepository.findByDishId(dish.getId());
            dish.setIngredients(ingredients);

            //Step By Step
            List<StepByStepEntity> stepByStep = stepByStepRepository.findByDishId(dishId);
            dish.setStepByStep(stepByStep);

            //Reviews
            List<ReviewEntity> reviewEntities = reviewRepository.findReviewEntitiesByDishId(dish.getId());
            List<ReviewEntity> reviews = new ArrayList<>();

            for (Object reviewEntity : reviewEntities){
                Object[] objects = (Object[]) reviewEntity;
                ReviewEntity review = (ReviewEntity) objects[0];
                reviews.add(review);
            }
            dish.setReviews(reviews);

            //Related Dishes
            List<DishEntity> relatedDishesEntity = new ArrayList<>();
            for (CourseEntity courseEntity : dish.getCourses()){
                String coursName = courseEntity.getName();
                List<DishEntity> dishesInCourse = dishRepository.findByCourse(coursName, dish.getId());
                relatedDishesEntity.addAll(dishesInCourse);
            }
            List<RelatedDish> relatedDishes = new ArrayList<>();
            for (DishEntity entity : relatedDishesEntity){
                RelatedDish relatedDish = new RelatedDish(entity.getId(), entity.getName(), entity.getImageLink());
                relatedDishes.add(relatedDish);
            }
            dish.setRelatedDishes(relatedDishes);

            responseHeaders.set(SUCCESS, "get dishes success");
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(dish);
        }
    }

    public ResponseEntity<Object> getDishesByCategoryId(String categoryId){
        HttpHeaders responseHeaders = new HttpHeaders();
       List<DishEntity> dishes = new ArrayList<>();
       try {
            dishes = dishRepository.findByCategoryId(categoryId);
            for (DishEntity dish : dishes){
                List<CourseEntity> courses = courseRepository.findByDishId(dish.getId());
                dish.setCourses(courses);
            }

           responseHeaders.set(SUCCESS, "get dishes success");
       } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
       } finally {
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(dishes);
       }
    }

    public ResponseEntity<Object> getDishesByString(String string){
        HttpHeaders responseHeaders = new HttpHeaders();
        List<DishEntity> dishes = new ArrayList<>();
        try {
            dishes = dishRepository.findByString(string);
            responseHeaders.set(SUCCESS, "get dishes success");
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(dishes);
        }
    }

    public ResponseEntity<Object> getDishesByCourses (List<String> courses, String dishId){
        HttpHeaders responseHeaders = new HttpHeaders();
        List<DishEntity> dishes = new ArrayList<>();
        try {
            for (String course : courses){
                List<DishEntity> dishEntities = dishRepository.findByCourse(course, dishId);
                dishes.addAll(dishEntities);
            }
            responseHeaders.set(SUCCESS, "get dishes success");
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(dishes);
        }
    }


}
