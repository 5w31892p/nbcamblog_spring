package com.sparta.nbcamblog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.nbcamblog.dto.BlogRequestDto;
import com.sparta.nbcamblog.dto.BlogResponseDto;
import com.sparta.nbcamblog.entity.Blog;
import com.sparta.nbcamblog.entity.BlogUser;
import com.sparta.nbcamblog.entity.UserRoleEnum;
import com.sparta.nbcamblog.exception.CustomStatus;
import com.sparta.nbcamblog.exception.StatusEnum;
import com.sparta.nbcamblog.exception.StatusResponse;
import com.sparta.nbcamblog.repository.BlogRepository;
import com.sparta.nbcamblog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {

	private final BlogRepository blogRepository;
	private final UserRepository userRepository;

	@Transactional
	public BlogResponseDto createContent(BlogRequestDto blogRequestDto, String username) {
		// 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
		BlogUser user = userRepository.findByUsername(username).orElseThrow(
			() -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
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
		);
		return new BlogResponseDto(blog);
	}

	@Transactional
	public BlogResponseDto updateContent(Long id, BlogRequestDto requestDto, String username) {

		Blog blog = blogRepository.findById(id).orElseThrow(
			() -> new CustomStatus(StatusEnum.NO_POST)
		);
		BlogUser user = userRepository.findByUsername(username).orElseThrow(
			() -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
		);
		UserRoleEnum role = user.getRole();
		if (role == UserRoleEnum.ADMIN || user.getId().equals(blog.getUser().getId())) {
			blog.update(requestDto);
		} else {
			blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
				() -> new CustomStatus(StatusEnum.UNAUTHENTICATED_TOKEN)
			);
		}
		return new BlogResponseDto(blog);
	}

	@Transactional
	public StatusResponse deletePost(Long id, String username) {
		Blog blog = blogRepository.findById(id).orElseThrow(
			() -> new CustomStatus(StatusEnum.NO_POST)
		);
		BlogUser user = userRepository.findByUsername(username).orElseThrow(
			() -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
		);
		UserRoleEnum role = user.getRole();
		if (role == UserRoleEnum.ADMIN || user.getId().equals(blog.getUser().getId())) {
			blogRepository.deleteById(id);
		} else {
			blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
				() -> new CustomStatus(StatusEnum.UNAUTHENTICATED_TOKEN)
			);
		}
		return new StatusResponse(StatusEnum.DELETE_OK);
	}

	@Transactional
	public StatusResponse addLike(Long postId, String username) {
		Blog blog = blogRepository.findById(postId).orElseThrow(
			() -> new CustomStatus(StatusEnum.NO_POST)
		);
		BlogUser user = userRepository.findByUsername(username).orElseThrow(
			() -> new CustomStatus(StatusEnum.UNINFORMED_USERNAME)
		);
		if (!blog.getLikes().contains(user)) {
			blog.getLikes().add(user);
			blog.getLikes().size();
			this.blogRepository.save(blog);
			return new StatusResponse(StatusEnum.Like_OK);
		} else {
			blog.getLikes().remove(user);
			blog.getLikes().size();
			this.blogRepository.save(blog);
			return new StatusResponse(StatusEnum.Like_Cancellation_OK);

		}
	}
}


