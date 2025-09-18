package hcmute.edu.demo.services.Implements;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import hcmute.edu.demo.Entity.Category;
import hcmute.edu.demo.Reponsitory.CategoryRepository;
import hcmute.edu.demo.services.Interfaces.ICategoryService;

@Service
public class CategoryServicesImpl implements ICategoryService {
	@Autowired
	CategoryRepository categoryRepository;


	public CategoryServicesImpl(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Category> findByCategoryNameContaining(String name) {
		// TODO Auto-generated method stub
		return categoryRepository.findByCategoryNameContaining(name);
	}

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public void delete(Category entity) {
		// TODO Auto-generated method stub
		categoryRepository.delete(entity);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		categoryRepository.deleteById(id);

	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return categoryRepository.count();
	}

	@Override
	public <S extends Category> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Category> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Category> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return categoryRepository.findAllById(ids);
	}

	@Override
	public List<Category> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return categoryRepository.findAll(sort);
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return categoryRepository.findAll(pageable);
	}

	@Override
	public Optional<Category> findByCategoryName(String name) {
		// TODO Auto-generated method stub
		return categoryRepository.findByCategoryName(name);
	}

	@Override
	public <S extends Category> S save(S entity) {
		// TODO Auto-generated method stub
		if (entity.getId() == null) {
			return categoryRepository.save(entity);
		} else {
			Optional<Category> opt = findById(entity.getId());
			if (opt.isPresent()) {
				if (StringUtils.isEmpty(entity.getIcon())) {
					entity.setIcon(opt.get().getIcon());
				} else {// lấy lại images cũ
					entity.setIcon(entity.getIcon());
				}
			}
			return categoryRepository.save(entity);
		}
	}

	@Override
	public Page<Category> findByCategoryNameContaining(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return categoryRepository.findByCategoryNameContaining(name, pageable);
	}
}
