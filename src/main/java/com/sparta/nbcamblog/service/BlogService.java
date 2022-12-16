package com.sparta.nbcamblog.service;

import com.sparta.nbcamblog.dto.BlogRequestDto;
import com.sparta.nbcamblog.dto.BlogResponseDto;
import com.sparta.nbcamblog.entity.Blog;
import com.sparta.nbcamblog.entity.User;
import com.sparta.nbcamblog.jwt.JwtUtil;
import com.sparta.nbcamblog.repository.BlogRepository;
import com.sparta.nbcamblog.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public BlogResponseDto createContent(BlogRequestDto blogRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Blog blog = new Blog(blogRequestDto);
            blogRepository.save(blog);
            return new BlogResponseDto(blog);

        } else {
            return null;
        }
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
    public BlogResponseDto getContent(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );
        return new BlogResponseDto(blog);
    }

    @Transactional(readOnly = true)
    public List<BlogResponseDto> getByUsername(String username) {
        List<Blog> blogList = blogRepository.getPostingByUsername(username);
        List<BlogResponseDto> responseDtoList = blogList.stream().map(posting -> new BlogResponseDto(posting)).collect(Collectors.toList());
        return responseDtoList;
    }

    @Transactional
    public String updateContent(Long id, BlogRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );

            Blog blog = blogRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 글입니다.")
            );

            if (!user.getUsername().equals(blog.getUsername())) {
                throw new IllegalArgumentException("본인이 작성한 게시글만 수정할 수 있습니다.");
            }

            blog.update(requestDto);
            return "수정 완료";
        } else {
            return null;
        }
    }

    @Transactional
    public String deletePost(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );

            Blog blog = blogRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 글입니다.")
            );

            if (!user.getUsername().equals(blog.getUsername())) {
                throw new IllegalArgumentException("본인이 작성한 게시글만 삭제할 수 있습니다.");
            }

            blogRepository.deleteById(id);
            return "삭제 완료";
        } else {
            return null;
        }
    }
}
