package org.myblog.blog.controller;


import lombok.AllArgsConstructor;
import org.myblog.blog.model.CommentDto;
import org.myblog.blog.model.PostDto;
import org.myblog.blog.service.PostServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {

    private final PostServiceImpl postService;

    @GetMapping("/all")
    public String showAllPost(Model model){
        model.addAttribute("listPost",postService.getAllPost());
        return "blog/posts";
    }

    @GetMapping("/add")
    public String formPost(Model model){
        model.addAttribute("post",new PostDto());
        return "blog/form_post";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute("post") PostDto post){
        postService.addPost(post);
        return "redirect:/post/all";
    }

    @GetMapping("/delete")
    public String deletePost(@RequestParam UUID id){
        postService.removePost(postService.getPost(id));
        return "redirect:/post/all";
    }

    @GetMapping("/update")
    public String updatePost(Model model,@RequestParam UUID id){
        model.addAttribute("post",postService.getPost(id));
        return "blog/form_post";
    }

    @GetMapping("/{id}")
    public String detailPost(@PathVariable UUID id,Model model){
        model.addAttribute("post",postService.getPost(id));
        model.addAttribute("comment",new CommentDto());
        return "blog/post";
    }

    @GetMapping("/add/comment")
    public String formComment(@RequestParam UUID id, Model model){
        model.addAttribute("id",id);
        model.addAttribute("comment",new CommentDto());
        return "blog/form_comment";
    }

    @PostMapping("/comment/add")
    public String addComment(@RequestParam UUID id, @ModelAttribute("comment") CommentDto comment){
        postService.addComment(id,comment);
        return "redirect:/post/"+id;
    }

    @GetMapping("/comment/delete")
    public String deleteComment(@RequestParam UUID idPost, @RequestParam UUID idComment){
        postService.deleteComment(idPost,idComment);
        return "redirect:/post/"+idPost;

    }



}
