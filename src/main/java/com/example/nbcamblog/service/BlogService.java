package com.example.nbcamblog.service;

import com.example.nbcamblog.dto.BlogRequestDto;
import com.example.nbcamblog.dto.BlogResponseDto;
import com.example.nbcamblog.dto.DeletePostDto;
import com.example.nbcamblog.entity.Blog;
import com.example.nbcamblog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    @Transactional
    public BlogResponseDto createContent(BlogRequestDto blogRequestDto) {
        Blog blog = new Blog(blogRequestDto);
        blogRepository.save(blog);
        return new BlogResponseDto(blog);
    }

    @Transactional
    public List<BlogResponseDto> getContents() {
        List<Blog> blogList = blogRepository.findAllByOrderByModifiedAtDesc();
        List<BlogResponseDto> blogResponseDto = new ArrayList<>();
        for (Blog blog : blogList) {
            BlogResponseDto blogResponseDto1 = new BlogResponseDto(blog);
            blogResponseDto.add(blogResponseDto1);
        }
        return blogResponseDto;
    }

    @Transactional
    public BlogResponseDto getContent (Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );
        return new BlogResponseDto(blog);
    }

    @Transactional
    public BlogResponseDto updateContent(Long id, BlogRequestDto blogRequestDto) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);

        if (blogRequestDto.getPassword().equals(blog.getPassword())) {
            blog.update(blogRequestDto);
            return blogResponseDto;
        } else {
            return blogResponseDto;
        }
    }

    @Transactional
    public DeletePostDto deletePost (Long id, BlogRequestDto blogRequestDto) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );
        DeletePostDto deletePostDto = new DeletePostDto();

        if (blogRequestDto.getPassword().equals(blog.getPassword())) {
            blogRepository.deleteById(id);
            deletePostDto.setMsg("삭제가 완료되었습니다.");
        } else {
            deletePostDto.setMsg("비밀번호가 일치하지 않습니다.");
        }
        return deletePostDto;
    }
}
