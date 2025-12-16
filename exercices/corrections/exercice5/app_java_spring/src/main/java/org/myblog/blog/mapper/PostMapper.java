package org.myblog.blog.mapper;


import org.myblog.blog.entity.PostEntity;
import org.myblog.blog.model.PostDto;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDto postEntityToPostDto(PostEntity postEntity){
        return PostDto.builder()
                .id(postEntity.getId())
                .content(postEntity.getContent())
                .description(postEntity.getDescription())
                .title(postEntity.getTitle())
                .build();
    }


    public PostEntity  postDtoToPostEntity(PostDto postDto){
        return PostEntity.builder()
                .content(postDto.getContent())
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .id(postDto.getId())
                .build();
    }
}
