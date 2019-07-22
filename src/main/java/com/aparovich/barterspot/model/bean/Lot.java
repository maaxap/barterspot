package com.aparovich.barterspot.model.bean;

import com.aparovich.barterspot.model.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Maxim on 09.04.2017
 */
public class Lot extends Model {
    private String name;
    private String description;
    private BigDecimal defaultPrice;
    private BigDecimal finalPrice;
    private LocalDateTime finishing;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private Category category;
    private User user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(BigDecimal defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getFinishing() {
        return finishing;
    }

    public void setFinishing(LocalDateTime finishing) {
        this.finishing = finishing;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lot)) return false;
        if (!super.equals(o)) return false;

        Lot lot = (Lot) o;

        if (name != null ? !name.equals(lot.name) : lot.name != null) return false;
        if (description != null ? !description.equals(lot.description) : lot.description != null) return false;
        if (defaultPrice != null ? !defaultPrice.equals(lot.defaultPrice) : lot.defaultPrice != null) return false;
        if (finalPrice != null ? !finalPrice.equals(lot.finalPrice) : lot.finalPrice != null) return false;
        if (finishing != null ? !finishing.equals(lot.finishing) : lot.finishing != null) return false;
        if (createdAt != null ? !createdAt.equals(lot.createdAt) : lot.createdAt != null) return false;
        if (deletedAt != null ? !deletedAt.equals(lot.deletedAt) : lot.deletedAt != null) return false;
        if (category != null ? !category.equals(lot.category) : lot.category != null) return false;
        return user != null ? user.equals(lot.user) : lot.user == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (defaultPrice != null ? defaultPrice.hashCode() : 0);
        result = 31 * result + (finalPrice != null ? finalPrice.hashCode() : 0);
        result = 31 * result + (finishing != null ? finishing.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (deletedAt != null ? deletedAt.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
