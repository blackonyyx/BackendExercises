package org.acme;

import java.time.LocalDate;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {
    

    public Person createPerson(String name, LocalDate date) {
        Person p = new Person();
        p.setName(name);
        p.setBirth(date);
        persist(p);
        return p;
    }

    public Person findByName(String name) {
        return find("name", name).firstResult();
    }

    public List<Person> getAll() {
        return this.listAll();
    }
    
}
