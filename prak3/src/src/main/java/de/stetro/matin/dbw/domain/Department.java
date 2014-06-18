package de.stetro.matin.dbw.domain;


import javax.persistence.*;

@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToOne
    private Employee conductor;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
