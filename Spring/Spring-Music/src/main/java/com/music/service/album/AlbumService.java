package com.music.service.album;

//public class AlbumService implements IAlbumService {
//    private final AlbumRepository albumRepository;
//    private final SongRepository songRepository;
//
//    public void saveAlbumWithSongs(CreateAlbumDto albumDto, List<String> songTitles, List<String> songFileUrls) {
//        Album album = new Album();
//        album.setName(albumDto.getName());
//        album.setReleaseDate(albumDto.getReleaseDate());
//        album.setPrice(albumDto.getPrice());
//        album.setCoverUrl(albumDto.getCoverUrl());
//
//        for (int i = 0; i < songTitles.size(); i++) {
//            Song song = new Song();
//            song.setTitle(songTitles.get(i));
//            song.setFileUrl(songFileUrls.get(i));
//            song.setAlbum(album);
//            album.getSongs().add(song);
//        }
//
//        albumRepository.save(album);
//    }
//}
