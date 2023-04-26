package myProject.services;

import myProject.models.Standard;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import myProject.models.Person;
import myProject.repositories.StandardRepository;
import myProject.repositories.PersonRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class StandardsService {

    private final StandardRepository standardRepository;
    private final PersonRepository personRepository;

    @Autowired
    public StandardsService(StandardRepository standardRepository, PersonRepository personRepository) {
        this.personRepository = personRepository;
        this.standardRepository = standardRepository;
    }

    public List<Standard> findAllStandards() {
        return standardRepository.findAll(PageRequest.of(0, 50, Sort.by("name"))).getContent();
    }

    public Standard findStandardById(int id) {
        Optional<Standard> foundStandard = standardRepository.findById(id);
        return foundStandard.orElse(null);
    }

    @Transactional
    public void saveStandard(Standard Standard) {
        standardRepository.save(Standard);
    }

    @Transactional
    public void updateStandard(int id, Standard updatedStandard) {
        updatedStandard.setId(id);
        standardRepository.save(updatedStandard);
    }

    @Transactional
    public void delete(int id) {
        standardRepository.deleteById(id);
    }

    /*public Person getStandardOwner(int id) {
        Optional<Standard> Standard = standardRepository.findById(id);
        if (Standard.isPresent()) {
            Hibernate.initialize(Standard.get().getOwner());
            return Standard.get().getOwner();
        } else {
            return (Person) Collections.emptyList();
        }
    }*/

    /*@Transactional
    public void release(int id) {
        Optional<Standard> foundStandard = standardRepository.findById(id);
        if (foundStandard.isPresent()) {
            foundStandard.get().setOwner(null);
            foundStandard.get().setTakenAt(null);
        }
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        Optional<Standard> currentStandard = standardRepository.findById(id);
        if (currentStandard.isPresent()) {
            currentStandard.get().setOwner(selectedPerson);
            currentStandard.get().setTakenAt(new Date());
        }
    }*/

    public List<Standard> findStandard(String name) {
        return standardRepository.findStandardByNameStartsWith(name);
    }
}
