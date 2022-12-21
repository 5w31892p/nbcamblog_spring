package com.sparta.nbcamblog.service;

import com.sparta.nbcamblog.dto.BlogRequestDto;
import com.sparta.nbcamblog.dto.BlogResponseDto;
import com.sparta.nbcamblog.entity.Blog;
import com.sparta.nbcamblog.entity.User;
import com.sparta.nbcamblog.entity.UserRoleEnum;
import com.sparta.nbcamblog.exception.CustomStatus;
import com.sparta.nbcamblog.exception.StatusEnum;
import com.sparta.nbcamblog.exception.StatusResponse;
import com.sparta.nbcamblog.jwt.JwtUtil;
import com.sparta.nbcamblog.repository.BlogRepository;
import com.sparta.nbcamblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    @Transactional
    public BlogResponseDto createContent(BlogRequestDto blogRequestDto, String username) {
//        String token = jwtUtil.resolveToken(request);
//        Claims claims;
//        Blog blog = new Blog();
//        if (token != null) {
//            // Token 검증
//            if (jwtUtil.validateToken(token)) {
//                // 토큰에서 사용자 정보 가져오기
//                claims = jwtUtil.getUserInfoFromToken(token);
//            } else {
//                throw new CustomStatus(StatusEnum.INVALID_TOKEN);
////                throw new IllegalArgumentException("Token Error");
//            }

        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
//                            new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        Blog blog = new Blog(blogRequestDto, user);
        blogRepository.save(blog);
        return new BlogResponseDto(blog);
    }

    @Transactional(readOnly = true)
    public List<BlogResponseDto> getContents() {
        List<Blog> blogList = blogRepository.findAllByOrderByModifiedAtDesc();
        List<BlogResponseDto> blogResponseDtoList = new ArrayList<>();
        for (Blog blog : blogList) {
            blogResponseDtoList.add(new BlogResponseDto(blog));
        }
        return blogResponseDtoList;
    }

    @Transactional(readOnly = true)
    public BlogResponseDto getContent(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new CustomStatus(StatusEnum.NO_POST)
//                        new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );
        return new BlogResponseDto(blog);
    }


    @Transactional
    public BlogResponseDto updateContent(Long id, BlogRequestDto requestDto, String username) {

        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new CustomStatus(StatusEnum.NO_POST)
//                        new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
//        String token = jwtUtil.resolveToken(request);
//        Claims claims;
//
//        if (token != null) {
//            if (jwtUtil.validateToken(token)) {
//                claims = jwtUtil.getUserInfoFromToken(token);
//            } else {
//                throw new CustomStatus(StatusEnum.INVALID_TOKEN);
////                throw new IllegalArgumentException("Token Error");
//            }
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
//                            new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        UserRoleEnum role = user.getRole();
        if (role == UserRoleEnum.ADMIN || user.getId().equals(blog.getUser().getId())) {
            blog.update(requestDto);
        } else {
            blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new CustomStatus(StatusEnum.UNAUTHENTICATED_TOKEN)
//                                new IllegalArgumentException("본인이 작성한 게시글만 수정할 수 있습니다.")
            );
        }
        return new BlogResponseDto(blog);
    }

    @Transactional
    public StatusResponse deletePost(Long id, String username) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new CustomStatus(StatusEnum.NO_POST)
//                        new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
//        String token = jwtUtil.resolveToken(request);
//        Claims claims;
//
//
//        if (token != null) {
//            if (jwtUtil.validateToken(token)) {
//                claims = jwtUtil.getUserInfoFromToken(token);
//            } else {
//                throw new CustomStatus(StatusEnum.INVALID_TOKEN);
////                throw new IllegalArgumentException("Token Error");
//            }

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
//                            new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        UserRoleEnum role = user.getRole();
        if (role == UserRoleEnum.ADMIN || user.getId().equals(blog.getUser().getId())) {
            blogRepository.deleteById(id);
        } else {
            blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new CustomStatus(StatusEnum.UNAUTHENTICATED_TOKEN)
//                                new IllegalArgumentException("본인이 작성한 게시글만 삭제할 수 있습니다.")
            );
        }
        return new StatusResponse(StatusEnum.DELETE_OK);
    }
}

