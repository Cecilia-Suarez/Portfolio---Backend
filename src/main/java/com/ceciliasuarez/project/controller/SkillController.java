package com.ceciliasuarez.project.controller;

import com.ceciliasuarez.project.Dto.SkillDto;
import com.ceciliasuarez.project.model.Skill;
import com.ceciliasuarez.project.model.translation.SkillTranslation;
import com.ceciliasuarez.project.service.ISkillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
@CrossOrigin(origins = "*")
public class SkillController {
    private final ISkillService skillService;

    @Autowired
    public SkillController(ISkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/new")
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody Skill skill) {
        Skill createdSkill = skillService.createSkill(skill);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSkill);
    }

    @GetMapping("/all")
    public List<Skill> getAllSkills() {
        return skillService.getAllSkill();
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<SkillDto> getSkillById(@PathVariable Long id, @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        SkillDto skillDTO = skillService.getSkillByLanguage(id, language);
        return ResponseEntity.ok(skillDTO);
    }

    @PostMapping("/{id}/translations")
    public SkillTranslation addTranslation(@PathVariable Long id, @RequestBody SkillTranslation translation) {
        return skillService.addTranslation(id, translation);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSkill(@PathVariable Long id, @RequestBody Skill skill) {
        skillService.updateSkill(id, skill);
        return ResponseEntity.ok("Skill and translation updated successfully!");
    }
}

