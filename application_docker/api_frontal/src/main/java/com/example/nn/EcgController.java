package com.example.nn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
public class EcgController {

    @Value("${ia.service.url}")
    private String iaUrl;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/analyze")
    public String analyze(@RequestParam("file") MultipartFile file, 
                          @RequestParam("type") String type, Model model) {
        try {
            // 1. Lire le contenu du fichier uploadé
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);

            // 2. Nettoyer et transformer en liste de Double
            // On utilise \\s+ pour séparer par n'importe quel espace/tabulation
            List<Double> signal = Arrays.stream(content.trim().split("\\s+"))
                                        .map(Double::parseDouble)
                                        .toList();

            // Vérification de sécurité pour le rapport
            if (signal.size() != 96) {
                model.addAttribute("error", "Erreur : Le fichier contient " + signal.size() + " valeurs au lieu de 96.");
                return "index";
            }

            // 3. Envoi au service Python
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("signal", signal);
            requestBody.put("type", type);

            Map<String, Object> response = restTemplate.postForObject(iaUrl, requestBody, Map.class);

            // 4. Affichage des résultats
            model.addAttribute("prediction", response.get("prediction"));
            model.addAttribute("probability", response.get("probability"));
            model.addAttribute("modelUsed", response.get("model_used"));
            
            return "result";

        } catch (Exception e) {
            model.addAttribute("error", "Fichier invalide ou corrompu.");
            return "index";
        }
    }
}