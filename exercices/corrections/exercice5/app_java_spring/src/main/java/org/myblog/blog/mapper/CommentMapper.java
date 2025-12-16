package org.myblog.blog.mapper;

import org.myblog.blog.entity.CommentEntity;
import org.myblog.blog.entity.PostEntity;
import org.myblog.blog.model.CommentDto;
import org.myblog.blog.model.PostDto;
import org.springframework.stereotype.Component;


@Component
public class CommentMapper {

    public CommentDto commentEntityToCommentDto(CommentEntity commentEntity){
        return CommentDto.builder()
                .id(commentEntity.getId())
                .name(commentEntity.getName())
                .email(commentEntity.getEmail())
                .content(commentEntity.getContent())
                .build();
    }


    public CommentEntity  commentDtoToCommentEntity(CommentDto commentDto){
        return CommentEntity.builder()
                .id(commentDto.getId())
                .name(commentDto.getName())
                .email(commentDto.getEmail())
                .content(commentDto.getContent())
                .build();
    }
}
