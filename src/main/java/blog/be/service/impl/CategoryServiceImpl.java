package blog.be.service.impl;

import blog.be.dtos.ApiResponse;
import blog.be.entity.Category;
import blog.be.repository.CategoryRepository;
import blog.be.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ApiResponse getCategories() {
        List<Category> list = categoryRepository.findAll();
        if(list.isEmpty()){
            return new ApiResponse(false , "Failed" , 0, null);
        }
        return new ApiResponse(true , "Success" , 1, list);
    }
}
