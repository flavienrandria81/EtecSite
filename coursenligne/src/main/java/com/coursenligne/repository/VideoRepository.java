package com.coursenligne.repository;

import com.coursenligne.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    
    Page<Video> findByLeçonId(Long leconId, Pageable pageable);

}
