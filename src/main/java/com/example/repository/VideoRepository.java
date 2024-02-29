package com.example.repository;

import com.example.entity.VideoEntity;
import com.example.mapper.VideoFullInfoMapper;
import com.example.mapper.VideoPlayListInfoMapper;
import com.example.mapper.VideoShortInfoMapper;
import com.example.mapper.VideoShortInfoPaginationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends CrudRepository<VideoEntity, String>, PagingAndSortingRepository<VideoEntity, String> {
    @Query("FROM VideoEntity WHERE id=?1 AND categoryId=?2")
    Optional<VideoEntity> getVideoByVideoIdAndCategoryId(String videoId, Integer categoryId);

    @Query(value = " SELECT * FROM video WHERE category_id=?1 AND status= 'PUBLIC' ", nativeQuery = true)
    List<VideoEntity> getByCategoryId(Integer id);

    @Query(value = "select v.id as videoId, " +
            "       v.title, " +
            "       v.preview_attach_id, " +
            "       v.published_date, " +
            "       v.view_count, " +
            "       c.id as channelId, " +
            "       c.name, " +
            "       c.photo_id " +
            " from video as v " +
            "         inner join channel as c on c.id = v.channel_id " +
            "         inner join profile as p on p.id = c.profile_id;", nativeQuery = true)
    Page<VideoShortInfoMapper> getVideoShortInfo(Pageable pageable);
    //   VidePlayListInfo(id,title, preview_attach(id,url), view_count,published_date,duration)

    @Query(value = "select v.id as videoId, " +
            "       v.title, " +
            "       v.preview_attach_id, " +
            "       v.published_date, " +
            "       v.view_count, " +
            "       v.duration, " +
            "       c.id as channelId, " +
            "       c.name, " +
            "       c.photo_id, " +
            "       p.id as profileId, " +
            "       p.name as profileName, " +
            "       p.surname, " +
            "       (select cast(json_agg(temp_t) as text) " +
            "        from (select json_build_object('id', pl.id, 'name', pl.name) " +
            "              from playlist as pl " +
            "                       inner join play_list_video as plv on plv.playlist_id = pl.id " +
            "                       inner join video vv on vv.id = plv.video_id " +
            "              where vv.id = v.id) as temp_t) as playListJson " +
            "from video as v " +
            "         inner join channel as c on c.id = v.channel_id " +
            "         inner join profile as p on p.id = c.profile_id " +
            " order by v.created_date desc ", nativeQuery = true)
    Page<VideoShortInfoPaginationMapper> getVideoListForAdmin(Pageable pageable);
    //  (VideShortInfo + owner (is,name,surname) + [playlist (id,name))]

    @Query(value = "select v.id as videoId, v.title, a.id as attachId, a.url, v.view_count, v.published_date, v.description " +
            " from video as v " +
            "         inner join attach as a on v.id = a.id " +
            " order by v.created_date desc", nativeQuery = true)
    Page<VideoPlayListInfoMapper> getChannelVideoList(Pageable pageable);
    //    VidePlayListInfo(id,title, preview_attach(id,url), view_count, published_date,duration)

    @Query(value = """
            SELECT v.id,
                   v.title,
                   v.description,
                   v.preview_attach_id,
                   v.published_date,
                   v.view_count,
                   v.shared_count,
                   v.like_count,
                   v.dislike_count,
                   v.duration,
                   a.id                               AS attachId,
                   a.url,
                   a.duration                         AS attachDuration,
                   c.id                               AS categoryId,
                   c.name_en                          AS categoryName,
                   ch.id                              AS channelId,
                   ch.name                            AS channelName,
                   ch.photo_id                        AS photoId,
                  (SELECT cast(json_agg(temp_t.name) as text)
                   FROM (SELECT t.name
                          FROM video as v1
                                  INNER JOIN video_tag AS vt ON v1.id = vt.video_id
                                  INNER JOIN tag AS t ON t.id = vt.tag_id
                       WHERE v1.id = v.id) AS temp_t) AS tagListJson
            FROM video AS v
                     INNER JOIN attach AS a ON v.attach_id = a.id
                     INNER JOIN category AS c ON v.category_id = c.id
                     INNER JOIN channel AS ch ON v.channel_id = ch.id
            WHERE v.id = ?1  
             AND ((SELECT profile_id = ?2
                   FROM channel AS ch
                   WHERE ch.profile_id = (SELECT channel_id
                                          FROM video AS v
                                          WHERE v.id = ?1))
                  OR (SELECT role = 'ROLE_ADMIN'
                      FROM profile
                      WHERE id = ?2))""", nativeQuery = true)
    Optional<VideoFullInfoMapper> getVideoFullInfo(String videoId, Integer profileId);

}