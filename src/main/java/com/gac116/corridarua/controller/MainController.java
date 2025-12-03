package com.gac116.corridarua.controller;

import com.gac116.corridarua.model.Equipe; // O símbolo Equipe agora deve ser encontrado
import com.gac116.corridarua.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller principal para rotas web (ex: home, login).
 */
@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private EquipeRepository equipeRepository; // Injetando o repositório

    /**
     * Rota para a página inicial.
     */
    @GetMapping
    public String home(Model model) {
        // Exemplo de como usar o repositório Equipe:
        List<Equipe> equipes = equipeRepository.findAll();
        model.addAttribute("equipes", equipes);
        model.addAttribute("titulo", "Bem-vindo ao CorridaRua!");
        return "index"; // Assume que você tem um template Thymeleaf/JSP chamado index.html
    }

    /**
     * Rota para o formulário de login.
     */
    @GetMapping("/login")
    public String login() {
        return "login"; // Assume um template login.html
    }

    @Autowired
    private PilotoRepository pilotoRepository;
    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String registroForm(Model model) {
        model.addAttribute("registroDto", new com.gac116.corridarua.dto.RegistroDTO());
        model.addAttribute("equipes", equipeRepository.findAll());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarPiloto(@ModelAttribute com.gac116.corridarua.dto.RegistroDTO dto) {
        // 1. Criar ou buscar Equipe (Lógica simplificada)
        Equipe equipe = null;
        if (dto.getEquipeId() != null) {
            equipe = equipeRepository.findById(dto.getEquipeId()).orElse(null);
        } else if (dto.getNovaEquipeNome() != null && !dto.getNovaEquipeNome().isEmpty()) {
            equipe = new Equipe();
            equipe.setNome(dto.getNovaEquipeNome());
            equipe.setCidade(dto.getNovaEquipeCidade());
            equipeRepository.save(equipe);
        }

        // 2. Criar Piloto
        Piloto novoPiloto = new Piloto();
        novoPiloto.setNome(dto.getNomePiloto());
        novoPiloto.setUsername(dto.getUsername());
        // Criptografar senha antes de salvar!
        novoPiloto.setPassword(passwordEncoder.encode(dto.getPassword())); 
        novoPiloto.setEquipe(equipe);
        
        pilotoRepository.save(novoPiloto);

        return "redirect:/login?registrado=true";
    }
}