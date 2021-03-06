package com.ifood.domain;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "Dish_Ingredient", schema = "dbo", catalog = "I_Food")
public class DishIngredientEntity {
    private int id;
    private String dishId;
    private Double amount;
    private Integer unitId;
    private String description;


    private IngredientEntity dishIngredient;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="IngredientId")
    public IngredientEntity getDishIngredient() {
        return dishIngredient;
    }

    public void setDishIngredient(IngredientEntity dishIngredient) {
        this.dishIngredient = dishIngredient;
    }

    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DishId")
    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    @Basic
    @Column(name = "Amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "UnitId")
    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    @Basic
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishIngredientEntity that = (DishIngredientEntity) o;
        return id == that.id &&
                Objects.equals(dishId, that.dishId) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(unitId, that.unitId) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dishId, amount, unitId, description);
    }
}
