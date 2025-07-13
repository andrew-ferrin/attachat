package dev.andrew.attachat.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.WordUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dev.andrew.attachat.domain.Button;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    @GetMapping("/")
    public String home(Model model) throws IOException {
        Resource[] resources = resolver.getResources("classpath:prompts/*");
        List<Button> buttons = new ArrayList<>();

        for (Resource resource : resources) {
            String fileName = resource.getFilename();
            if (fileName != null) {
                String label = fileName.replaceFirst("[.][^.]+$", "")
                        .replace('_', ' ')
                        .trim();
                label = WordUtils.capitalizeFully(label);
                String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                buttons.add(new Button(label, content));
            }
        }

        model.addAttribute("buttons", buttons);
        return "home";
    }

}