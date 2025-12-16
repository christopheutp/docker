package org.myblog.blog.repository;

import org.myblog.blog.entity.CommentEntity;
import org.myblog.blog.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {

    List<CommentEntity> getAllByPostEntity(PostEntity postEntity);
    List<CommentEntity> getAllByPostEntity_Id(UUID id);

}
