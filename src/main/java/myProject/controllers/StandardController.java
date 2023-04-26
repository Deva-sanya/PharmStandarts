package myProject.controllers;

import myProject.models.Standard;
import myProject.services.StandardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import myProject.services.PeopleService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/standards")
public class StandardController {

    private final StandardsService standardsService;
    private final PeopleService peopleService;

    @Autowired
    public StandardController(StandardsService standardsService, PeopleService peopleService) {
       this.standardsService = standardsService;
        this.peopleService = peopleService;
    }


    @GetMapping
    public String index(Model model) {
        List<Standard> standards = standardsService.findAllStandards();
        model.addAttribute("standards",standards);
        return "/standards/allStandards";
    }

    /*@GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("standard", standardsService.findStandarById(id));
        Optional<Person> standardOwner = Optional.ofNullable(standardsService.getstandardOwner(id));

        if (standardOwner.isPresent())
            model.addAttribute("owner", standardOwner.get());
        else
            model.addAttribute("people", peopleService.findAllPeople());

        return "/standards/showstandard";
    }*/

    @GetMapping("/new")
    public String newStandard(@ModelAttribute("standard") Standard standard) {
        return "/standards/addStandard";
    }

    @PostMapping("/createStandard")
    public String create(@ModelAttribute("standard") @Valid Standard standard,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/standards/addStandard";
        standardsService.saveStandard(standard);
        return "redirect:/standards";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("standard", standardsService.findStandardById(id));
        return "/standards/editStandard";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("standard") @Valid Standard standard, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "/standards/editStandard";
        standardsService.updateStandard(id, standard);
        return "redirect:/standards";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        standardsService.delete(id);
        return "redirect:/standards";
    }

    /*@PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        standardsService.release(id);
        return "redirect:/standards/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        standardsService.assign(id, selectedPerson);
        return "redirect:/standards/" + id;
    }*/

    @GetMapping("/search")
    public String searchStandard() {
        return "/standards/search";
    }

    @PostMapping("/searchStandard")
    public String search(@RequestParam(value = "name", required = false) String name, Model model) {
            List<Standard> standards = standardsService.findStandard(name);
            model.addAttribute("standards", standards);
        return "/standards/search";
    }
}
