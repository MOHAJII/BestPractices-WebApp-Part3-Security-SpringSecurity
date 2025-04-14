package net.haji.springmvcthymeleafsecured.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.haji.springmvcthymeleafsecured.dao.entities.Patient;
import net.haji.springmvcthymeleafsecured.dao.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("")
    public String index(Model model) {
        return "redirect:/user/index";
    }

    @GetMapping("/user/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "4") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword
                        ) {
        Page<Patient> pagePatients = patientRepository.findByNameContainsIgnoreCase(keyword, PageRequest.of(page, size));
        model.addAttribute("patientsList", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "patients";
    }

    @GetMapping("/admin/delete")
    public String delete(@RequestParam(name = "id") Long id,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "4") int size,
                         @RequestParam(name = "keyword", defaultValue = "") String keyword) {
       patientRepository.deleteById(id);
       return "redirect:/user/index?page="+page+"&size="+size+"&keyword="+keyword;
    }

    @GetMapping("/admin/formPatient")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatient";
    }

    @PostMapping("/admin/save")
    public String save(Model model,
                       @Valid Patient patient,
                       BindingResult bindingResult,
                       @RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        if (bindingResult.hasErrors()) {
            return "formPatient";
        }
        patientRepository.save(patient);
        System.out.println(patient);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/editPatient")
    public String editPatient(Model model,
                              @RequestParam(name = "id") Long id,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) throw new RuntimeException("Patient not found");
        model.addAttribute("patient", patient);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "editPatient";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized(Model model) {
        return "notAuthorized";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
