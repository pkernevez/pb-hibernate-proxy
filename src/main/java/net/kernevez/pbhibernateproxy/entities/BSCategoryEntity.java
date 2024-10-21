package net.kernevez.pbhibernateproxy.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Table(name = "BS_CATEGORY")
public class BSCategoryEntity {
    @Id
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private BSCategoryEntity other;


    public BSCategoryEntity() {
    }

    public BSCategoryEntity(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BSCategoryEntity setName(String name) {
        this.name = name;
        return this;
    }

    public BSCategoryEntity getOther() {
        return other;
    }

    public BSCategoryEntity setOther(BSCategoryEntity other) {
        this.other = other;
        return this;
    }
}
