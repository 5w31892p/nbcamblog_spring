package com.sparta.nbcamblog.service;

import com.sparta.nbcamblog.dto.BlogRequestDto;
import com.sparta.nbcamblog.dto.BlogResponseDto;
import com.sparta.nbcamblog.dto.DeleteResponseDto;
import com.sparta.nbcamblog.entity.Blog;
import com.sparta.nbcamblog.entity.User;
import com.sparta.nbcamblog.entity.UserRoleEnum;
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
        Blog blog = new Blog();
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

            blog = new Blog(blogRequestDto, user);
            blogRepository.save(blog);
            return new BlogResponseDto(blog);
        } else {
            return new BlogResponseDto(blog);
        }
    }

    @Transactional(readOnly = true)
    public List<BlogResponseDto> getContents(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 사용자 권한 가져와서 ADMIN 이면 전체 조회, USER 면 본인이 추가한 부분 조회
            UserRoleEnum userRoleEnum = user.getRole();

            List<Blog> blogList;
            if (userRoleEnum == UserRoleEnum.USER) {
                // 사용자 권한이 USER일 경우
                blogList = blogRepository.findAllByUserId(user.getId());
            } else {
                blogList = blogRepository.findAllByOrderByModifiedAtDesc();
            }
            List<BlogResponseDto> blogResponseDto = new ArrayList<>();
            for (Blog blog : blogList) {
                BlogResponseDto blogResponseDto1 = new BlogResponseDto(blog);
                blogResponseDto.add(blogResponseDto1);
            }
            return blogResponseDto;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public BlogResponseDto getContent(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );
        return new BlogResponseDto(blog);
    }


    @Transactional
    public BlogResponseDto updateContent(Long id, BlogRequestDto requestDto, HttpServletRequest request) {

        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("본인이 작성한 게시글만 수정할 수 있습니다.")
            );
            blog.update(requestDto);
        }
        return new BlogResponseDto(blog);
    }

    @Transactional
    public DeleteResponseDto deletePost(Long id, HttpServletRequest request) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        String token = jwtUtil.resolveToken(request);
        Claims claims;


        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("본인의 게시글만 삭제할 수 있습니다.")
            );
            blogRepository.deleteById(id);
        }
        return new DeleteResponseDto();
    }
}

