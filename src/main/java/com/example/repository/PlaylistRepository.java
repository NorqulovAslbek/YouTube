package com.example.repository;

import com.example.entity.PlaylistEntity;
import com.example.enums.PlaylistStatus;
import com.example.mapper.PlayListInfoMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends CrudRepository<PlaylistEntity, Integer>, PagingAndSortingRepository<PlaylistEntity, Integer> {
    Optional<PlaylistEntity> findByIdAndVisible(Integer id, Boolean visible);

    @Transactional
    @Modifying
    @Query("UPDATE PlaylistEntity SET status=?2 WHERE id=?1")
    void changeStatus(Integer id, PlaylistStatus status);

    @Transactional
    @Modifying
    @Query("UPDATE PlaylistEntity SET visible=false WHERE id=?1")
    void delete(Integer id);

    @Query("from PlaylistEntity where channelId=?1")
    List<PlaylistEntity> getByChannelId(Integer id);

    @Query(value = "SELECT p.id, p.name FROM Playlist p WHERE p.id IN (SELECT plv.playlist_id FROM Video v INNER JOIN play_list_video plv ON v.id = plv.video_id)", nativeQuery = true)
    List<PlaylistEntity> getPlayList(String id);


    @Query(value = """
            SELECT p.id,
                   p.name,
                   p.description,
                   p.status,
                   p.order_num,
                   c.id         as channelId,
                   c.name       as channelName,
                   a.id         as channelphotoId,
                   a.url        as channelphotoUrl,
                   prof.id      as profileId,
                   prof.name    as profileName,
                   prof.surname as profileSurname,
                   attach.id    as profileAttachId,
                   attach.url   as profileAttachUrl
            FROM playlist as p
                     inner join channel as c on p.channel_id = c.id
                     inner join attach as a on a.id = c.photo_id
                     inner join profile as prof on prof.id = c.profile_id
                     inner join attach on prof.photo_id = attach.id
            where prof.id = ?1
            order by p.order_num desc;
            """, nativeQuery = true)
    List<PlayListInfoMapper> getListByUserId(Integer id);

    @Query(value = """
            SELECT p.id,
                   p.name,
                   p.description,
                   p.status,
                   p.order_num,
                   c.id         as channelId,
                   c.name       as channelName,
                   a.id         as channelphotoId,
                   a.url        as channelphotoUrl,
                   prof.id      as profileId,
                   prof.name    as profileName,
                   prof.surname as profileSurname,
                   attach.id    as profileAttachId,
                   attach.url   as profileAttachUrl
            FROM playlist as p
                     inner join channel as c on p.channel_id = c.id
                     inner join attach as a on a.id = c.photo_id
                     inner join profile as prof on prof.id = c.profile_id
                     inner join attach on prof.photo_id = attach.id
            order by p.created_date desc;
            """, nativeQuery = true)
    List<PlayListInfoMapper> pagination();

    @Query("SELECT count(*) FROM PlaylistEntity")
    Long totalElements();
}
