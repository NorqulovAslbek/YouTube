package com.example.repository;

import com.example.entity.ProfileEntity;
import com.example.enums.ProfileStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByEmail(String username);

    @Transactional
    @Modifying
    @Query("Update ProfileEntity  set status =?2 where id = ?1")
    void updateStatus(Integer id, ProfileStatus active);


    @Query(value = "SELECT * FROM profile WHERE email=?1 AND password=?2 AND visible=true AND status='ACTIVE' ", nativeQuery = true)
    Optional<ProfileEntity> getProfile(String email, String password);

    @Query("FROM ProfileEntity WHERE id=?1 AND  visible=true ")
    Optional<ProfileEntity> getById(Integer id);

    @Query("FROM ProfileEntity WHERE email=?1 AND visible=true ")
    Optional<ProfileEntity> getByEmail(String email);

    @Transactional
    @Modifying
    @Query("Update ProfileEntity  set updatedDate =?2, photoId =?3 where id = ?1")
    void updatePhoto(Integer profileId, LocalDateTime updateDate, String photoId);


}
