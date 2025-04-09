package com.sangha.connect.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hide_details")
public class HideDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "entity_id", nullable = false)
    private Point entity;

    @Column(name = "hide_name")
    private Boolean hideName;

    @Column(name = "hide_email")
    private Boolean hideEmail;

    @Column(name = "hide_phone")
    private Boolean hidePhone;

    @Column(name = "hide_address")
    private Boolean hideAddress;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
