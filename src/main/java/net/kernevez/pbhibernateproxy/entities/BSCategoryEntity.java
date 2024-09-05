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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_BS_CATEGORY_ID")
    private BSCategoryEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public BSCategoryEntity getParent() {
        return parent;
    }

    public BSCategoryEntity setParent(BSCategoryEntity parent) {
        this.parent = parent;
        return this;
    }

    public Stream<BSCategoryEntity> getChildren() {
        return children.stream();
    }

    public BSCategoryEntity addChild(BSCategoryEntity child) {
        this.children.add(child);
        child.setParent(this);
        return this;
    }


}
