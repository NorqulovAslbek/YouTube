package com.example.repository;

import com.example.entity.SubscriptionEntity;
import com.example.enums.SubscriptionNotificationType;
import com.example.mapper.SubscriptionInfoMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Integer> {

    @Query("from SubscriptionEntity as s where s.channelId=?1 and s.status='ACTIVE'")
     Optional<SubscriptionEntity> getByChannelId(Integer id);

    @Transactional
    @Modifying
    @Query("update SubscriptionEntity as s set s.notificationType=?2 where s.channelId=?1 and s.status='ACTIVE' ")
    void getByIdChangeStatus(Integer channelId, SubscriptionNotificationType type);

    @Query(value = "select s.id, " +
            "       ch.id as channelId, " +
            "       ch.name, " +
            "       a.id as photoId, " +
            "       a.url, " +
            "       s.notification_type as notificationType " +
            "    from subscription as s " +
            " inner join channel as ch on s.channel_id= ch.id " +
            " left join attach as a on ch.photo_id=a.id " +
            " where s.status='ACTIVE' ", nativeQuery = true)
    List<SubscriptionInfoMapper> getSubscriptionList();

    @Query(value = "select s.id, " +
            "       ch.id as channelId, " +
            "       ch.name, " +
            "       a.id as photoId, " +
            "       a.url, " +
            "       s.notification_type," +
            "       s.created_date " +
            "    from subscription as s " +
            " inner join channel as ch on s.channel_id= ch.id " +
            " left join attach as a on ch.photo_id=a.id " +
            " where s.profile_id=?1 and s.status='ACTIVE' ", nativeQuery = true)
    List<SubscriptionInfoMapper> getByUserIdSubscriptionList(Integer id);

}

