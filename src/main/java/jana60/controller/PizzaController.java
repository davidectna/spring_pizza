package jana60.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@PostMapping("/save")
	public String savePizza (@Valid @ModelAttribute("pizzaForm") Pizza pizzaForm, BindingResult br) {
		if (br.hasErrors()) {
			return "/pizza/pizzaform";}
		else {
			repo.save(pizzaForm);
			return "redirect:/";
		}
		
	}
	
	//delete 
	@GetMapping("/delete/{id}")
	public String deletePizza (@PathVariable ("id") Integer pizzaId,RedirectAttributes reAt) {
		Optional<Pizza> pizzaToDelete = repo.findById(pizzaId);
		if (pizzaToDelete.isPresent()) {
			repo.delete(pizzaToDelete.get());
			return "redirect:/";	
		}else {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND,
			 "Pizza with id " + pizzaId + " not present");
		}
	}
	
	//edit 
	@GetMapping ("/edit/{id}")
	public String editPizza (@PathVariable ("id") Integer pizzaId,Model model) {
		Optional<Pizza> pizzaToEdit = repo.findById(pizzaId);
		if (pizzaToEdit.isPresent()) {
			model.addAttribute("pizzaForm",pizzaToEdit.get());
			return "/pizza/pizzaform";
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					 "Pizza with id " + pizzaId + " not present");
		}
	}
}