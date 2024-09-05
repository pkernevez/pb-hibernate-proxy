package net.kernevez.pbhibernateproxy.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "BS")
public class BSEntity {
    @Id
    private Long id;

    private String name;

    public BSEntity() {
    }

    public BSEntity(Long id) {
        this.id = id;
    }

    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BSCategoryEntity root;

    public BSEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }


    public BSCategoryEntity getRootCategory() {
        return this.root;
    }

    public BSEntity setRoot(BSCategoryEntity root) {
        this.root = root;
        return this;
    }

}
