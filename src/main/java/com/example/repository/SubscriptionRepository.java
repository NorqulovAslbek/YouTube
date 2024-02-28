package com.example.repository;

import com.example.entity.SubscriptionEntity;
import com.example.enums.SubscriptionNotificationType;
import com.example.mapper.SubscriptionInfoMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Integer> {

    @Query("update SubscriptionEntity as s set s.notificationType=?2 where s.id=?1 ")
    Optional<SubscriptionEntity> getByIdChangeStatus(Integer id, SubscriptionNotificationType type);

    @Query(value = "select s.id, " +
            "       ch.id as channelId, " +
            "       ch.name, " +
            "       a.id as photoId, " +
            "       a.url, " +
            "       s.notification_type " +
            "    from subscription as s " +
            " inner join channel as ch on s.channel_id= ch.id " +
            " inner join attach as a on ch.photo_id=a.id " +
            " where s.status='ACTIVE' ", nativeQuery = true)
    List<SubscriptionInfoMapper> getUserSubscriptionList();

}

