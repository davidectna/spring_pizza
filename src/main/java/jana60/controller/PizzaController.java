package jana60.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
		model.addAttribute("PizzaList",repo.findAll());
		return "/pizza/pizzalist";
	}
	
	@GetMapping("/form")
	public String pizzaForm(Model model) {
		model.addAttribute("Pizza-Form", new Pizza());
		return "/pizza/pizzaform";
	}
}