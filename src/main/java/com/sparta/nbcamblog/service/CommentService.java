package com.sparta.nbcamblog.service;

import com.sparta.nbcamblog.dto.CommentRequestDto;
import com.sparta.nbcamblog.dto.CommentResponseDto;
import com.sparta.nbcamblog.entity.Blog;
import com.sparta.nbcamblog.entity.Comment;
import com.sparta.nbcamblog.entity.User;
import com.sparta.nbcamblog.entity.UserRoleEnum;
import com.sparta.nbcamblog.exception.CustomStatus;
import com.sparta.nbcamblog.exception.StatusEnum;
import com.sparta.nbcamblog.exception.StatusResponse;
import com.sparta.nbcamblog.jwt.JwtUtil;
import com.sparta.nbcamblog.repository.BlogRepository;
import com.sparta.nbcamblog.repository.CommentRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        Blog blog = blogRepository.findById(postId).orElseThrow(
                () -> new CustomStatus(StatusEnum.NO_POST)
//                        new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new CustomStatus(StatusEnum.INVALID_TOKEN);
//                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
//                            new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Comment comment = new Comment(commentRequestDto, user, blog);
            commentRepository.save(comment);
            return new CommentResponseDto(comment);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long postId) {
        Blog blog = blogRepository.findById(postId).orElseThrow(
                () -> new CustomStatus(StatusEnum.NO_POST)
//                        new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        List<Comment> commentList = commentRepository.findAllByOrderByModifiedAtDesc();
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponseDtoList.add(new CommentResponseDto(comment));
        }
        return commentResponseDtoList;
    }


    @Transactional
    public CommentResponseDto updateComment(Long postId, Long id, CommentRequestDto requestDto, HttpServletRequest request) {
        Blog blog = blogRepository.findById(postId).orElseThrow(
                () -> new CustomStatus(StatusEnum.NO_POST)
//                        new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomStatus(StatusEnum.NO_COMMENT)
//                        new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new CustomStatus(StatusEnum.INVALID_TOKEN);
//                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
//                            new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            UserRoleEnum role = user.getRole();
            if (role == UserRoleEnum.ADMIN || user.getId().equals(comment.getUser().getId())) {
                comment.update(requestDto);
            } else {
                comment = commentRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                        () -> new CustomStatus(StatusEnum.UNAUTHENTICATED_TOKEN)
//                                new IllegalArgumentException("본인이 작성한 댓글만 수정할 수 있습니다.")
                );
            }
        }
        return new CommentResponseDto(comment);
    }

    @Transactional
    public StatusResponse deleteComment(Long postId, Long id, HttpServletRequest request) {
        Blog blog = blogRepository.findById(postId).orElseThrow(
                () -> new CustomStatus(StatusEnum.NO_POST)
//                        new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomStatus(StatusEnum.NO_COMMENT)
//                        new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new CustomStatus(StatusEnum.INVALID_TOKEN);
//                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
//                            new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            UserRoleEnum role = user.getRole();
            if (role == UserRoleEnum.ADMIN || user.getId().equals(comment.getUser().getId())) {
            commentRepository.deleteById(id);
            } else {
                comment = commentRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                        () -> new CustomStatus(StatusEnum.UNAUTHENTICATED_TOKEN)
//                                new IllegalArgumentException("본인이 작성한 댓글만 수정할 수 있습니다.")
                );
            }
        }
        return new StatusResponse(StatusEnum.DELETE_OK);
    }
}

