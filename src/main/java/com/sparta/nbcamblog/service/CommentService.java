package com.sparta.nbcamblog.service;

import com.sparta.nbcamblog.dto.CommentRequestDto;
import com.sparta.nbcamblog.dto.CommentResponseDto;
import com.sparta.nbcamblog.dto.DeleteResponseDto;
import com.sparta.nbcamblog.entity.Blog;
import com.sparta.nbcamblog.entity.Comment;
import com.sparta.nbcamblog.entity.User;
import com.sparta.nbcamblog.entity.UserRoleEnum;
import com.sparta.nbcamblog.jwt.JwtUtil;
import com.sparta.nbcamblog.repository.BlogRepository;
import com.sparta.nbcamblog.repository.CommentRepository;
import com.sparta.nbcamblog.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
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

            Blog blog = blogRepository.findById(postId).orElseThrow(
                    () -> new IllegalArgumentException("해당 글이 존재하지 않습니다.")
            );
            Comment comment = new Comment(commentRequestDto, user, blog);
            commentRepository.save(comment);
            return new CommentResponseDto(comment);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public CommentResponseDto getComments(Long postId, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 글입니다.")
        );
        Blog blog = blogRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재하지 않습니다.")
        );
        return new CommentResponseDto(comment);
    }


    @Transactional
    public CommentResponseDto updateComment(Long postId, Long id, CommentRequestDto requestDto, HttpServletRequest request) {

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
            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 포스팅이 존재하지 않습니다.")
            );

            UserRoleEnum userRoleEnum = user.getRole();
            if (!user.getUsername().equals(comment.getUser().getUsername()) && userRoleEnum == UserRoleEnum.USER) {
                throw new IllegalArgumentException("본인이 작성한 댓글만 수정할 수 있습니다.");
            }
            Blog blog = blogRepository.findById(postId).orElseThrow(
                    () -> new IllegalArgumentException("해당 글이 존재하지 않습니다.")
            );
            comment.update(requestDto);
            return new CommentResponseDto(comment);
        }
        return null;
    }

    @Transactional
    public DeleteResponseDto deleteComment(Long postId, Long id, HttpServletRequest request) {
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
            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 포스팅이 존재하지 않습니다.")
            );
            Blog blog = blogRepository.findById(postId).orElseThrow(
                    () -> new IllegalArgumentException("해당 글이 존재하지 않습니다.")
            );

            UserRoleEnum userRoleEnum = user.getRole();
            if (!user.getUsername().equals(comment.getUser().getUsername()) && userRoleEnum == UserRoleEnum.USER) {
                throw new IllegalArgumentException("본인이 작성한 댓글만 삭제할 수 있습니다.");
            }
            blogRepository.deleteById(id);
        }
        return new DeleteResponseDto();
    }
}

