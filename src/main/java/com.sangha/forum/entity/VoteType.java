package com.sangha.forum.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

@Entity
@Table(name = "vote_type")
public enum VoteType {
    UP,
    DOWN;
}