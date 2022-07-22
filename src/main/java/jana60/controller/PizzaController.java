package jana60.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jana60.model.Pizza;
import jana60.repository.PizzaRepository;

@Controller
@RequestMapping("/")
public class PizzaController {

	@Autowired
	private PizzaRepository repo;
	
	@GetMapping
	public String pizzaList(Model model) {
		model.addAttribute("pizzaList",repo.findAll());
		return "/pizza/pizzalist";
	}
	
	@GetMapping("/form")
	public String pizzaForm(Model model) {
		model.addAttribute("pizzaForm", new Pizza());
		return "/pizza/pizzaform";
	}
	
	@PostMapping("/form")
	public String savePizza (@Valid @ModelAttribute("pizzaForm") Pizza pizzaForm, BindingResult br) {
		if (br.hasErrors()) {
			return "/pizza/pizzaform";}
		else {
			repo.save(pizzaForm);
			return "redirect:/";
		}
		
	}
}