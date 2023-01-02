package com.sparta.nbcamblog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.nbcamblog.dto.CommentRequestDto;
import com.sparta.nbcamblog.dto.CommentResponseDto;
import com.sparta.nbcamblog.entity.Blog;
import com.sparta.nbcamblog.entity.BlogUser;
import com.sparta.nbcamblog.entity.Comment;
import com.sparta.nbcamblog.entity.UserRoleEnum;
import com.sparta.nbcamblog.exception.CustomStatus;
import com.sparta.nbcamblog.exception.StatusEnum;
import com.sparta.nbcamblog.exception.StatusResponse;
import com.sparta.nbcamblog.repository.BlogRepository;
import com.sparta.nbcamblog.repository.CommentRepository;
import com.sparta.nbcamblog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;

    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, String username) {
        Blog blog = blogRepository.findById(postId).orElseThrow(
                () -> new CustomStatus(StatusEnum.NO_POST)
        );
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        BlogUser user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
        );

        Comment comment = new Comment(commentRequestDto, user, blog);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long postId) {
        Blog blog = blogRepository.findById(postId).orElseThrow(
                () -> new CustomStatus(StatusEnum.NO_POST)
        );
        List<Comment> commentList = commentRepository.findAllByOrderByModifiedAtDesc();
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponseDtoList.add(new CommentResponseDto(comment));
        }
        return commentResponseDtoList;
    }


    @Transactional
    public CommentResponseDto updateComment(Long postId, Long id, CommentRequestDto requestDto, String username) {
        Blog blog = blogRepository.findById(postId).orElseThrow(
                () -> new CustomStatus(StatusEnum.NO_POST)
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomStatus(StatusEnum.NO_COMMENT)
        );
        BlogUser user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
        );

        UserRoleEnum role = user.getRole();
        if (role == UserRoleEnum.ADMIN || user.getId().equals(comment.getUser().getId())) {
            comment.update(requestDto);
        } else {
            comment = commentRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new CustomStatus(StatusEnum.UNAUTHENTICATED_TOKEN)
            );
        }
        return new CommentResponseDto(comment);
    }

    @Transactional
    public StatusResponse deleteComment(Long postId, Long id, String username) {
        Blog blog = blogRepository.findById(postId).orElseThrow(
                () -> new CustomStatus(StatusEnum.NO_POST)
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomStatus(StatusEnum.NO_COMMENT)
        );

        BlogUser user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
        );
        UserRoleEnum role = user.getRole();
        if (role == UserRoleEnum.ADMIN || user.getId().equals(comment.getUser().getId())) {
            commentRepository.deleteById(id);
        } else {
            comment = commentRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new CustomStatus(StatusEnum.UNAUTHENTICATED_TOKEN)
            );
        }
        return new StatusResponse(StatusEnum.DELETE_OK);
    }

    public void voteComment(Comment comment, BlogUser user) {
        comment.getVoter().add(user);
        this.commentRepository.save(comment);
    }
}

