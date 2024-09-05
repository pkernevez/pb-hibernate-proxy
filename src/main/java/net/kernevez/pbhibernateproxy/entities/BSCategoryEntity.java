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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "PARENT_BS_CATEGORY_ID")
//    @OrderColumn(name = "children_order")
    private List<BSCategoryEntity> children = new ArrayList<>();


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

    public Stream<BSCategoryEntity> getChildren() {
        return children.stream();
    }

    public BSCategoryEntity addChild(BSCategoryEntity child) {
        this.children.add(child);
        return this;
    }


}
