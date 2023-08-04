package com.engeto.ProjektRegistracnisystem.service;

import com.engeto.ProjektRegistracnisystem.model.Person;
import java.util.List;

public interface PersonRepository {

    int createPerson(Person person);

    Person getPersonsDetailedInfo(Long ID);

    Person getPersonsUnDetailedInfo(Long ID);

    List<Person> getAllPersonsDetailedInfo();

    List<Person> getAllPersonsUnDetailedInfo();

    public int updatePerson(Object updatedPerson);

    int deletePersonByID(Long ID);
}


