package org.myblog.blog.service;

import org.myblog.blog.model.CommentDto;
import org.myblog.blog.model.PostDto;

import java.util.List;
import java.util.UUID;

public interface IPostService {

    List<PostDto> getAllPost();

    PostDto getPost(UUID id);

    PostDto addPost(PostDto postDto);

    PostDto updatePost(PostDto postDto);

    boolean removePost(PostDto postDto);

    PostDto addComment(UUID idPost,CommentDto commentDto);

    boolean deleteComment(UUID idPost,UUID idComment);
}
