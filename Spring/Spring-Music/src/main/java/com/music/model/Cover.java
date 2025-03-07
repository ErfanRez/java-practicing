package com.music.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Cover extends BaseEntity {
    @Column(name = "`key`")
    String key;
    String url;
}
