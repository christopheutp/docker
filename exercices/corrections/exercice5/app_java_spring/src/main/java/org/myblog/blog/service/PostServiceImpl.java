package org.myblog.blog.service;

import lombok.AllArgsConstructor;
import org.myblog.blog.entity.CommentEntity;
import org.myblog.blog.entity.PostEntity;
import org.myblog.blog.mapper.CommentMapper;
import org.myblog.blog.mapper.PostMapper;
import org.myblog.blog.model.CommentDto;
import org.myblog.blog.model.PostDto;
import org.myblog.blog.repository.CommentRepository;
import org.myblog.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class PostServiceImpl implements IPostService{


    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private PostMapper postMapper;
    private CommentMapper commentMapper;


    @Override
    public List<PostDto> getAllPost() {
        return postRepository.findAll().stream().map(postEntity -> postMapper.postEntityToPostDto(postEntity)).toList();
    }

    @Override
    public PostDto getPost(UUID id) {
        PostEntity postEntity = postRepository.getById(id);
        PostDto postDto =postMapper.postEntityToPostDto(postEntity);
        postDto.setCommentDtoList(postEntity.getCommentEntityList().stream().map(commentEntity -> commentMapper.commentEntityToCommentDto(commentEntity)).toList());
        return postDto;
    }

    @Override
    public PostDto addPost(PostDto postDto) {
        return postMapper.postEntityToPostDto(postRepository.save(postMapper.postDtoToPostEntity(postDto)));
    }

    @Override
    public PostDto updatePost(PostDto postDto) {
        PostEntity postEntity = postMapper.postDtoToPostEntity(postDto);
        return postMapper.postEntityToPostDto(postRepository.save(postEntity));
    }

    @Override
    public boolean removePost(PostDto postDto) {
        PostEntity postEntity = postRepository.getById(postDto.getId());
        postRepository.delete(postEntity);
        return true;
    }

    @Override
    public PostDto addComment(UUID idPost, CommentDto commentDto) {
        PostEntity postEntity = postRepository.getById(idPost);
        CommentEntity commentEntity = commentMapper.commentDtoToCommentEntity(commentDto);
        postEntity.getCommentEntityList().add(commentEntity);
        commentEntity.setPostEntity(postEntity);
        postRepository.save(postEntity);
        return postMapper.postEntityToPostDto(postRepository.getById(idPost));
    }

    @Override
    public boolean deleteComment(UUID idPost, UUID idComment) {
        commentRepository.delete(commentRepository.getById(idComment));
        return true;
    }


}
