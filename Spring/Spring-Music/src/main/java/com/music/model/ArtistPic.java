package com.music.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ArtistPic {
    @Column(name = "profile_key")
    private String key;

    @Column(name = "profile_url")
    private String url;
}
