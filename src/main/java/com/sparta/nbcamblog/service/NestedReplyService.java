package com.sparta.nbcamblog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.nbcamblog.dto.CommentRequestDto;
import com.sparta.nbcamblog.dto.CommentResponseDto;
import com.sparta.nbcamblog.dto.NestedReplyRequestDto;
import com.sparta.nbcamblog.dto.NestedReplyResponseDto;
import com.sparta.nbcamblog.entity.Blog;
import com.sparta.nbcamblog.entity.BlogUser;
import com.sparta.nbcamblog.entity.Comment;
import com.sparta.nbcamblog.entity.NestedReply;
import com.sparta.nbcamblog.entity.UserRoleEnum;
import com.sparta.nbcamblog.exception.CustomStatus;
import com.sparta.nbcamblog.exception.StatusEnum;
import com.sparta.nbcamblog.exception.StatusResponse;
import com.sparta.nbcamblog.repository.CommentRepository;
import com.sparta.nbcamblog.repository.NestedReplyRepository;
import com.sparta.nbcamblog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NestedReplyService {

	private final NestedReplyRepository replyRepository;
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;

	@Transactional
	public NestedReplyResponseDto createReply(Long commentId, NestedReplyRequestDto requestDto, String username) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(
			() -> new CustomStatus(StatusEnum.NO_POST)
		);

		BlogUser user = userRepository.findByUsername(username).orElseThrow(
			() -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
		);

		NestedReply reply = new NestedReply(requestDto, user, comment);
		replyRepository.save(reply);
		return new NestedReplyResponseDto(reply);
	}

	@Transactional(readOnly = true)
	public List<NestedReplyResponseDto> getReply(Long commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(
			() -> new CustomStatus(StatusEnum.NO_POST)
		);
		List<NestedReply> replyList = replyRepository.findAllByOrderByModifiedAtDesc();
		List<NestedReplyResponseDto> replyResponseDtoList = new ArrayList<>();
		for (NestedReply reply : replyList) {
			replyResponseDtoList.add(new NestedReplyResponseDto(reply));
		}
		return replyResponseDtoList;
	}


	@Transactional
	public NestedReplyResponseDto updateReply(Long commentId, Long id, NestedReplyRequestDto requestDto, String username) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(
			() -> new CustomStatus(StatusEnum.NO_POST)
		);
		NestedReply reply = replyRepository.findById(id).orElseThrow(
			() -> new CustomStatus(StatusEnum.NO_COMMENT)
		);

		BlogUser user = userRepository.findByUsername(username).orElseThrow(
			() -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
		);

		UserRoleEnum role = user.getRole();
		if (role == UserRoleEnum.ADMIN || user.getId().equals(reply.getUser().getId())) {
			reply.update(requestDto);
		} else {
			reply = replyRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
				() -> new CustomStatus(StatusEnum.UNAUTHENTICATED_TOKEN)
			);
		}
		return new NestedReplyResponseDto(reply);
	}

	@Transactional
	public StatusResponse deleteReply(Long commentId, Long id, String username) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(
			() -> new CustomStatus(StatusEnum.NO_POST)
		);
		NestedReply reply = replyRepository.findById(id).orElseThrow(
			() -> new CustomStatus(StatusEnum.NO_COMMENT)
		);

		BlogUser user = userRepository.findByUsername(username).orElseThrow(
			() -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
		);

		UserRoleEnum role = user.getRole();
		if (role == UserRoleEnum.ADMIN || user.getId().equals(reply.getUser().getId())) {
			replyRepository.deleteById(id);
		} else {
			reply = replyRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
				() -> new CustomStatus(StatusEnum.UNAUTHENTICATED_TOKEN)
			);
		}
		return new StatusResponse(StatusEnum.DELETE_OK);
	}
}
