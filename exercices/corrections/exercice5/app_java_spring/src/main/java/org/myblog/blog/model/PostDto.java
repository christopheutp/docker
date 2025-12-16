package org.myblog.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostDto {

    private UUID id;

    private String title;

    private String description;

    private String content;

    private List<CommentDto> commentDtoList;
}
