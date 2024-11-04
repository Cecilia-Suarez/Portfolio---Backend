package com.ceciliasuarez.project.controller;

import com.ceciliasuarez.project.model.Skill;
import com.ceciliasuarez.project.service.ISkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {
    private final ISkillService skillService;

    @Autowired
    public SkillController(ISkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("/all")
    public List<Skill> getAllSkill(){
        return skillService.getAllSkill();
    }

    @PostMapping("/new")
    public Skill createCategory(@RequestBody Skill skill){
        return skillService.createSkill(skill);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSkill(@PathVariable Long id){
        skillService.deleteSkill(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
