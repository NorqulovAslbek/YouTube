package com.example.repository;

import com.example.entity.VideoEntity;
import com.example.mapper.VideoFullInfoMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends CrudRepository<VideoEntity, String>, PagingAndSortingRepository<VideoEntity, String> {
    @Query("FROM VideoEntity WHERE id=?1 AND categoryId=?2")
    Optional<VideoEntity> getVideoByVideoIdAndCategoryId(String videoId, Integer categoryId);

    @Query(value = " SELECT * FROM video WHERE category_id=?1 AND status= 'PUBLIC' ", nativeQuery = true)
    List<VideoEntity> getByCategoryId(Integer id);
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
                   (SELECT cast(json_agg(temp_t) as text)
                    FROM (SELECT json_build_object('id', v1.id, 'title', v1.title)
                          FROM video as v1
                                   INNER JOIN video_tag AS vt ON v1.id = vt.video_id
                                   INNER JOIN tag AS t ON t.id = vt.tag_id
                          WHERE v1.id = v.id) AS temp_t) AS tagListJson

            FROM video AS v
                     INNER JOIN attach AS a on v.attach_id = a.id
                     INNER JOIN category AS c ON v.category_id = c.id
                     INNER JOIN channel AS ch ON v.channel_id = ch.id
            WHERE v.id =?1  
             AND ((SELECT profile_id = ?2
                           FROM channel AS ch
                           WHERE ch.profile_id = (SELECT channel_id
                           FROM video AS v
                           WHERE v.id = ?1)) OR (SELECT role = 'ROLE_ADMIN'
                           FROM profile
                           WHERE id = ?2));""", nativeQuery = true)
    Optional<VideoFullInfoMapper> getVideoFullInfo(String videoId ,Integer profileId);

}