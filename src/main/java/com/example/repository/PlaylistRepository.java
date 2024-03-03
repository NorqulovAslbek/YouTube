package com.example.repository;

import com.example.dto.PlayListShortInfoDTO;
import com.example.entity.PlaylistEntity;
import com.example.enums.PlaylistStatus;
import com.example.mapper.PlayListInfoMapper;
import com.example.mapper.PlayListShortInfoMapper;
import com.example.mapper.PlayListShortMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                     left join attach as a on a.id = c.photo_id
                     inner join profile as prof on prof.id = c.profile_id
                     left join attach on prof.photo_id = attach.id
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
                     left join attach as a on a.id = c.photo_id
                     inner join profile as prof on prof.id = c.profile_id
                     left join attach on prof.photo_id = attach.id
            order by p.created_date desc;
            """, nativeQuery = true)
    Page<PlayListInfoMapper> pagination(Pageable pageable);

    @Query("SELECT count(*) FROM PlaylistEntity")
    Long totalElements();

    @Query(value = """
            select p.id,
                   p.name,
                   p.created_date,
                   c.id       as channelId,
                   c.name     as channelName,
                   v.id       as videoId,
                   v.title    as videoTitle,
                   v.duration as videoDuration
            from playlist p
                     inner join channel c on c.id = p.channel_id
                     inner join play_list_video plv on p.id = plv.playlist_id
                     inner join video v on v.id = plv.video_id
                     where c.visible = true
                       and p.visible = true
                    order by p.order_num;
            """, nativeQuery = true)
    List<PlayListShortInfoMapper> getAll(Integer profileId);

    @Query(value = """
            select p.id,
                   p.name,
                   v.view_count,
                   (select count(view_count)
                    from video
                             inner join play_list_video plv
                                        on video.id = plv.video_id
                    where plv.playlist_id = ?1) as totalViewCount,
                   p.updated_date
            from playlist p
                     inner join public.video v
                                on p.channel_id = v.channel_id
                                 where p.id=?1;
            """, nativeQuery = true)
    Optional<PlayListShortMapper> getById(Integer id);
}
