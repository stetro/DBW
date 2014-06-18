package de.stetro.matin.dbw.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany
    private List<Product> contains;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
