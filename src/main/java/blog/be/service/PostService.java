package blog.be.service;


import blog.be.dtos.ApiResponse;
import blog.be.dtos.PagedResponse;
import blog.be.dtos.PostDto;
import blog.be.dtos.PostsByUsernameDto;
import blog.be.entity.User;

import java.util.Date;


public interface PostService {
    PagedResponse<PostDto> getPosts(int page, int size, String sortBy, String sortOrder, int userID);
    PagedResponse<PostsByUsernameDto> getPostsByUserName(int page, int size, String username);
    ApiResponse createPost(PostDto postDto);
    ApiResponse deletePost(int id);
    ApiResponse countPosts();
    PagedResponse<PostDto> getTitleFromPosts(String title, int page, int size);
    PagedResponse<PostDto> searchTitleFromStartDateToEndDate(Date startDate, Date endDate, int page, int size, String sort);

}
