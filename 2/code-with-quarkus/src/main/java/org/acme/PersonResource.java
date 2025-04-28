package org.acme;

import java.net.URI;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {
    
    @Inject
    PersonRepository personRepository;
    
    @GET
    public List<Person> list() {
        return personRepository.getAll();
    }

    @POST
    @Transactional
    public Response create(Person p) {
        Person p1 = personRepository.createPerson(p.getName(), p.getBirth());
        return Response.created(URI.create("/persons/" + p1.getId())).build();
    }

    @GET
    @Path("/search/{name}")
    public Person search(String name) {
        return personRepository.findByName(name);
    }
}
