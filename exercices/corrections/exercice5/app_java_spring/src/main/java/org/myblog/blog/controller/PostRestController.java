package org.myblog.blog.controller;


import lombok.RequiredArgsConstructor;
import org.myblog.blog.model.CommentDto;
import org.myblog.blog.model.PostDto;
import org.myblog.blog.service.PostServiceImpl;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/blog")
public class PostRestController {

    private final PostServiceImpl postService;

    @GetMapping("/all")
    public List<PostDto> showAllPost(Model model){
        return postService.getAllPost();
    }

    @GetMapping("/{id}")
    public PostDto detailPost(@PathVariable UUID id){
        return postService.getPost(id);
    }

}
