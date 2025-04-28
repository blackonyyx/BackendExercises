package org.acme;

import java.time.LocalDate;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Person extends PanacheEntity {

    @Id @GeneratedValue private Long id;
    private String name;
    private LocalDate birth;

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getBirth() {
        return birth;
    }
    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }
    @Override
    public String toString() {
        return "Person{" +
               "name='" + name + '\'' +
               ", birthDate=" + birth +
               '}';
    }
}
