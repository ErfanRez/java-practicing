package com.music.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Artist extends BaseEntity {
    String name;
    String nickname;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    List<Album> albums = new ArrayList<>();

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    List<Song> songs = new ArrayList<>();

}
