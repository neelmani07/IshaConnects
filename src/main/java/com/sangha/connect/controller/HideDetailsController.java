package com.sangha.connect.controller;

import com.sangha.connect.entity.HideDetails;
import com.sangha.connect.service.HideDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hide-details")
public class HideDetailsController {

    @Autowired
    private HideDetailsService hideDetailsService;

    @GetMapping
    public List<HideDetails> getAllHideDetails() {
        return hideDetailsService.getAllHideDetails();
    }

    @GetMapping("/{id}")
    public HideDetails getHideDetailsById(@PathVariable Long id) {
        return hideDetailsService.getHideDetailsById(id);
    }

    @PostMapping
    public HideDetails createHideDetails(@RequestBody HideDetails hideDetails) {
        return hideDetailsService.createHideDetails(hideDetails);
    }

    @PutMapping("/{id}")
    public HideDetails updateHideDetails(@PathVariable Long id, @RequestBody HideDetails hideDetails) {
        return hideDetailsService.updateHideDetails(id, hideDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHideDetails(@PathVariable Long id) {
        hideDetailsService.deleteHideDetails(id);
        return ResponseEntity.ok().build();
    }
} 