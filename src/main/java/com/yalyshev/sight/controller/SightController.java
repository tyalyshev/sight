package com.yalyshev.sight.controller;

import com.yalyshev.sight.entity.Sight;
import com.yalyshev.sight.repo.SightRepo;
import com.yalyshev.sight.service.AnnealingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SightController {

    private SightRepo sightRepo;
    private AnnealingService annealingService;

    @Autowired
    public SightController(SightRepo sightRepo, AnnealingService annealingService) {
        this.sightRepo = sightRepo;
        this.annealingService = annealingService;
    }

    @GetMapping("/sight/{id}")
    public ResponseEntity<Optional<Sight>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(sightRepo.findById(id));
    }

    @GetMapping("/sight/all")
    public ResponseEntity<List<Sight>> getAll() {
        return ResponseEntity.ok(sightRepo.getAll());
    }

    @GetMapping("/sight/run")
    public ResponseEntity<List<Integer>> run(@RequestParam int dot1,
                                             @RequestParam int dot2) {
        return ResponseEntity.ok(annealingService.annealingRun(dot1, dot2));
    }

    @PostMapping("/sight")
    public Sight saveSight(@RequestBody Sight sight) {
        return sightRepo.save(sight);
    }

    @DeleteMapping("/sight/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        sightRepo.deleteById(id);
        return ResponseEntity.ok("Delete " + id + " succeful");
    }
}
