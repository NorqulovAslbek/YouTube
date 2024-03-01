package com.example.repository;

import com.example.entity.ReportEntity;
import com.example.mapper.ReportInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReportRepository extends CrudRepository<ReportEntity, Integer>, PagingAndSortingRepository<ReportEntity, Integer> {

    @Query(value = """
            select r.id as reportId,
                   p.id as profileId,
                   p.name,
                   p.surname,
                   a.id as photoId,
                   a.url,
                   r.content,
                   r.channel_id as channelId,
                   r.type,
                   r.created_date as createdDate           
            from report as r
                     inner join profile as p on r.profile_id = p.id
                     left join attach as a on p.photo_id = a.id
            order by r.created_date desc ;
            """, nativeQuery = true)
    Page<ReportInfoMapper> getReportInfo(Pageable pageable);

    @Query(value = " select r.id as reportId, " +
            "                   p.id as profileId, " +
            "                   p.name, " +
            "                   p.surname, " +
            "                   a.id as photoId, " +
            "                   a.url, " +
            "                   r.content, " +
            "                   r.channel_id as channelId, " +
            "                   r.type, " +
            "                   r.created_date as createdDate            " +
            "            from report as r " +
            "                     inner join profile as p on r.profile_id = p.id " +
            "                     left join attach as a on p.photo_id = a.id " +
            "            order by r.created_date desc ", nativeQuery = true)
    List<ReportInfoMapper> getReportListByUserId(Integer id);
}
