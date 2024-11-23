package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Attachment;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.repository.AttachmentRepository;
import uz.pdp.food_recipe_app.repository.CategoryRepository;
import uz.pdp.food_recipe_app.entity.Category;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.mapper.CategoryMapper;
import uz.pdp.food_recipe_app.payload.category.req.CategoryAddReq;
import uz.pdp.food_recipe_app.payload.category.req.CategoryUpdateReq;
import uz.pdp.food_recipe_app.payload.category.res.CategoryRes;

import java.util.List;
import java.util.UUID;

import static uz.pdp.food_recipe_app.util.CoreUtils.getIfExists;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;

    public CategoryRes add(CategoryAddReq req) {
        if (categoryRepository.existsByName(req.getName()))
            throw RestException.restThrow(ErrorTypeEnum.CATEGORY_ALREADY_EXISTS);

        String photoPath = attachmentRepository.findById(req.getPhotoId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.FILE_NOT_FOUND))
                .getFilePath();

        Category category = new Category(req.getName(), req.getPhotoId());
        categoryRepository.save(category);  //saving

        return CategoryMapper.entityToDto(category, photoPath);
    }


    public CategoryRes update(long id, CategoryUpdateReq req) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

        if (!category.getName().equals(req.getName()) && categoryRepository.existsByName(req.getName()))
            throw RestException.restThrow(ErrorTypeEnum.CATEGORY_ALREADY_EXISTS);

        category.setName(getIfExists(req.getName(), category.getName()));

        if (req.getPhotoId() != null && attachmentRepository.existsById(req.getPhotoId())) {
            category.setPhotoId(req.getPhotoId());
        }
        categoryRepository.save(category); //updating

        return CategoryMapper.entityToDto(category, getPhotoPath(category));
    }

    public CategoryRes getOne(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

        return CategoryMapper.entityToDto(category, getPhotoPath(category));
    }

    public List<CategoryRes> getAll(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));

        return categoryRepository.findAllByFilters(name, pageable)
                .stream()
                .map(category -> CategoryMapper.entityToDto(category, getPhotoPath(category)))
                .toList();
    }

    private String getPhotoPath(Category category) {
        if (category.getPhotoId() == null)
            return null;

        return attachmentRepository
                .findById(category.getPhotoId())
                .map(Attachment::getFilePath)
                .orElse(null);
    }
}
