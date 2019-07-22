package com.aparovich.barterspot.model.bean;

import com.aparovich.barterspot.model.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Maxim on 09.04.2017
 */
public class Bid extends Model {
    private BigDecimal bid;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private User user;
    private Lot lot;

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
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

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bid)) return false;
        if (!super.equals(o)) return false;

        Bid bid1 = (Bid) o;

        if (bid != null ? !bid.equals(bid1.bid) : bid1.bid != null) return false;
        if (createdAt != null ? !createdAt.equals(bid1.createdAt) : bid1.createdAt != null) return false;
        if (deletedAt != null ? !deletedAt.equals(bid1.deletedAt) : bid1.deletedAt != null) return false;
        if (user != null ? !user.equals(bid1.user) : bid1.user != null) return false;
        return lot != null ? lot.equals(bid1.lot) : bid1.lot == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (bid != null ? bid.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (deletedAt != null ? deletedAt.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (lot != null ? lot.hashCode() : 0);
        return result;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }
}
