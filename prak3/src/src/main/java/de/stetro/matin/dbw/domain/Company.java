package de.stetro.matin.dbw.domain;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToOne
    private Employee boss;

    @ManyToMany
    private List<Product> productList;

}
