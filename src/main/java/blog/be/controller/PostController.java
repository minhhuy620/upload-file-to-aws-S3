package blog.be.controller;


import blog.be.dtos.ApiResponse;
import blog.be.dtos.PagedResponse;
import blog.be.dtos.PostDto;
import blog.be.dtos.PostsByUsernameDto;
import blog.be.entity.User;
import blog.be.repository.UserRepository;
import blog.be.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@CrossOrigin(value = "http://localhost:3000",allowCredentials = "true")
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    private ApiResponse res;

    @GetMapping("/get/{userID}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ApiResponse getPosts(@PathVariable int userID,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "created_at") String sort,
                                @RequestParam(defaultValue = "desc") String sortOrder){

        PagedResponse<PostDto> data = postService.getPosts(page, size, sort, sortOrder, userID);
        if (data != null) {
            res = new ApiResponse(true, null, 1, data);
        } else {
            res.setSuccess(false);
        }
        return res;
    }

    @GetMapping("/get_post")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse getPostForEachUser(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size,@RequestParam("username") String username){
        PagedResponse<PostsByUsernameDto> data = postService.getPostsByUserName(page, size, username);
        if(data != null){
            res = new ApiResponse(true, null, 0, data);
        }else{
            res.setSuccess(false);
        }
        return res;
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse> getTitleFromPosts(@RequestParam(required = false) String title,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size){
        try{
            PagedResponse<PostDto> data = postService.getTitleFromPosts(title, page, size);
            if(data != null){
                res = new ApiResponse(true, null, 1, data);
            }else{
                res.setSuccess(false);
            }
            return new ResponseEntity<>(res, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/post-time-report")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse> getAllPostsFromTimeBetween(@RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                                                                  @RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size,
                                                                  @RequestParam(defaultValue = "createdDate,desc") String sort){
        try{
            PagedResponse<PostDto> data = postService.searchTitleFromStartDateToEndDate(startDate,endDate, page, size, sort);
            if(data != null){
                res = new ApiResponse(true, null, 1, data);
            }else{
                res.setSuccess(false);
            }
            return new ResponseEntity<>(res, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/count_posts")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public  ResponseEntity<ApiResponse> countPosts() {
        ApiResponse data =  postService.countPosts();
        return ResponseEntity.ok(data);
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse> createPost(@RequestBody PostDto dto) {
        ApiResponse data =  postService.createPost(dto);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable(value = "id") int id){
        ApiResponse data = postService.deletePost(id);
        return ResponseEntity.ok(data);
    }
}
