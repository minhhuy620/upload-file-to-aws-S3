package blog.be.service.impl;

import blog.be.configuration.security.JwtUserDetailsService;
import blog.be.dtos.ApiResponse;
import blog.be.dtos.PagedResponse;
import blog.be.dtos.PostDto;
import blog.be.dtos.PostsByUsernameDto;
import blog.be.entity.Category;
import blog.be.entity.Post;
import blog.be.entity.User;
import blog.be.repository.CategoryRepository;
import blog.be.repository.PostRepository;
import blog.be.repository.UserRepository;
import blog.be.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Boolean.parseBoolean;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    private ApiResponse res;

    @Override
    public PagedResponse<PostDto> getPosts(int page, int size, String sortBy, String sortOrder, int userID) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Post> post = postRepository.findAllPostsByUserId(userID, pageable);
        List<Post> content = post.getNumberOfElements() == 0 ? Collections.emptyList() : post.getContent();
        return new PagedResponse(post.getNumber(), post.getSize(), post.getTotalElements(),
                post.getTotalPages(), post.isLast(),content);
    }

    @Override
    public PagedResponse<PostsByUsernameDto> getPostsByUserName(int page, int size, String username) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findAllPostsByUserName(username,pageable);
        List<Post> data = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();
        return new PagedResponse(posts.getNumber(), posts.getSize(), posts.getTotalElements(),
                posts.getTotalPages(), posts.isLast(),data);
    }

    @Override
    public PagedResponse<PostDto> getTitleFromPosts(String title, int page, int size){
        List<Post> posts = new ArrayList<Post>();
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> filterPost = null;
        if(title == null){
            filterPost = postRepository.findAll(pageable);
        }else{
            filterPost = postRepository.findByTitleContaining(title,pageable);
        }
        posts = filterPost.getContent();
        return new PagedResponse(filterPost.getNumber(), filterPost.getSize(), filterPost.getTotalElements(),
                filterPost.getTotalPages(), filterPost.isLast(),posts);
    }

    @Override
    public PagedResponse<PostDto> searchTitleFromStartDateToEndDate(Date startDate, Date endDate, int page, int size, String sort) {
        List<Post> posts = new ArrayList<>();
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction,sortParams[0]));
        Page<Post> filterPost = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String startDateFormatter = formatter.format(startDate);
        String endDateFormatter = formatter.format(endDate);
        if(startDate != null && endDate != null){
                filterPost = postRepository.findAllPostsByTimeBetween(startDateFormatter,endDateFormatter,pageable);
        }else{
            filterPost = postRepository.findAll(pageable);
        }
        posts = filterPost.getContent();
        return new PagedResponse(filterPost.getNumber(), filterPost.getSize(), filterPost.getTotalElements(),
                filterPost.getTotalPages(), filterPost.isLast(),posts);
    }

    @Override
    public ApiResponse createPost(PostDto postDto) {
        String currentUser = JwtUserDetailsService.getCurrentUser();
        Post posts = new Post();
        try{
            if(currentUser != null){
                Optional<User> userTest = userRepository.findByUserName(currentUser);
                if(userTest.isPresent()){
                    User user = userTest.get();
                    posts.setTitle(postDto.getTitle());
                    posts.setMetaTitle(postDto.getMetaTitle());
                    posts.setSlug(postDto.getSlug());
                    posts.setPublished(postDto.getPublished());
                    posts.setPublishedAt(new Date());
                    posts.setCreatedAt(new Date());
                    posts.setContent(postDto.getContent());
                    user.getPosts().add(posts);
                    posts.setUser(user);
                    Set<Category> categorySet = new HashSet<>(postDto.getCategories().size());
                    for (String title : postDto.getCategories()) {
                        Category categoryData = categoryRepository.findTitleByName(title);
                        categoryData = (categoryData == null) ? categoryRepository.save(new Category(title)) : categoryData;
                        categorySet.add(categoryData);
                    }
                    posts.setCategories(categorySet);
                    postRepository.save(posts);
                }
            }
            simpMessagingTemplate.convertAndSend("/topic/notification", posts.getCreatedAt());
            if (posts == null) {
                res = new ApiResponse(false, "Add false", 0, null);
            }
            return new ApiResponse(true, "Add success", 1, posts);
        }catch(Exception e) {
            e.printStackTrace();
            return new ApiResponse(false, e.getMessage(), 0, null);
        }
    }

    @Override
    @Transactional
    public ApiResponse deletePost(int id) {
        Optional<Post> postData = postRepository.findById(id);
        if(postData.isPresent()){
            postRepository.deleteById(id);
            return new ApiResponse(true, "Delete Success", 1 ,null);
        }

        return new ApiResponse(false, "Delete False", 0 ,null);
    }

    @Override
    public ApiResponse countPosts() {
        int count = postRepository.countPostByPublished();
        if(count < 0){
            return new ApiResponse(false, "Error Number of Posts: ", 0, null);
        }
        return new ApiResponse(true, "Number of Posts: "+ count, 1, count);
    }


}
