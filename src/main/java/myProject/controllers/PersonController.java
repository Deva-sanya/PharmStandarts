package myProject.controllers;


import myProject.models.Standard;
import myProject.services.StandardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import myProject.models.Person;
import myProject.services.PeopleService;
import myProject.util.PersonValidator;


import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/people")
public class PersonController {

    private PeopleService peopleService;
    private StandardsService standardsService;
    private PersonValidator personValidator;

    @Autowired
    public PersonController(PeopleService peopleService, PersonValidator personValidator, StandardsService standardsService) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.standardsService = standardsService;
    }


    @GetMapping
    public String index(Model model) {
        List<Person> people = peopleService.findAllPeople();
        model.addAttribute("people", people);
        return "/people/index";
    }

    /*@GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findPersonById(id));
        List<Standard> personsStandards = peopleService.getBooksByPersonId(id);
        model.addAttribute("standards", personsStandards);
        return "/people/show";
    }*/

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "/people/new";
    }

    @PostMapping("/createPerson")
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "/people/new";

        peopleService.savePerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findPersonById(id));
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "/people/edit";

        peopleService.updatePerson(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
