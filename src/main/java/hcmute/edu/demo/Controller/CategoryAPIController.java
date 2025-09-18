package hcmute.edu.demo.Controller;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hcmute.edu.demo.Entity.Category;
import hcmute.edu.demo.Entity.Response;
import hcmute.edu.demo.services.Interfaces.ICategoryService;
import hcmute.edu.demo.services.Interfaces.IStorageService;

public class CategoryAPIController {
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	IStorageService storageService;

	@GetMapping
	public ResponseEntity<?> getAllCategory() {// return ResponseEntity.ok().body(categoryService.findAll());
		return new ResponseEntity<Response>(new Response(true, "Thành công", categoryService.findAll()), HttpStatus.OK);
	}

	@PostMapping(path = "/getCategory")
	public ResponseEntity<?> getCategory(@Validated @RequestParam("id") Long id) {
		Optional<Category> category = categoryService.findById(id);
		if (category.isPresent()) {// return ResponseEntity.ok().body(category.get());
			return new ResponseEntity<Response>(new Response(true, "Thànhcông", category.get()), HttpStatus.OK);
		} else {// return ResponseEntity.notFound().build();
			return new ResponseEntity<Response>(new Response(false, "Thấtbại", null), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/addCategory")
	public ResponseEntity<?> addCategory(@Validated @RequestParam("categoryName") String categoryName,
			@Validated @RequestParam("icon") MultipartFile icon) {
		Optional<Category> optCategory = categoryService.findByCategoryName(categoryName);
		if (optCategory.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category đã tồn tại trong hệthống");
		} else {
			Category category = new Category();// kiểm tra tồn tại file, lưu file
			if (!icon.isEmpty()) {
				UUID uuid = UUID.randomUUID();
				String uuString = uuid.toString();// lưu file vào trường Images
				category.setIcon(storageService.getSorageFilename(icon, uuString));
				storageService.store(icon, category.getIcon());
			}
			category.setName(categoryName);
			categoryService.save(category);// return ResponseEntity.ok().body(category);
			return new ResponseEntity<Response>(new Response(true, "ThêmThành công", category), HttpStatus.OK);
		}
	}

	@PutMapping(path = "/updateCategory")
	public ResponseEntity<?> updateCategory(@Validated @RequestParam("categoryId") Long categoryId,
			@Validated @RequestParam("categoryName") String categoryName,
			@Validated @RequestParam("icon") MultipartFile icon) {
		Optional<Category> optCategory = categoryService.findById(categoryId);
		if (optCategory.isEmpty()) {
			return new ResponseEntity<Response>(new Response(false, "Khôngtìm thấy Category", null),
					HttpStatus.BAD_REQUEST);
		} else if (optCategory.isPresent()) {// kiểm tra tồn tại file, lưu file
			if (!icon.isEmpty()) {
				UUID uuid = UUID.randomUUID();
				String uuString = uuid.toString();// lưu file vào trường Images
				optCategory.get().setIcon(storageService.getSorageFilename(icon, uuString));
				storageService.store(icon, optCategory.get().getIcon());
			}
			optCategory.get().setName(categoryName);
			categoryService.save(optCategory.get());// return ResponseEntity.ok().body(category);
			return new ResponseEntity<Response>(new Response(true, "Cập nhật Thành công", optCategory.get()),
					HttpStatus.OK);
		}
		return null;
	}

	@DeleteMapping(path = "/deleteCategory")
	public ResponseEntity<?> deleteCategory(@Validated @RequestParam("categoryId") Long categoryId) {
		Optional<Category> optCategory = categoryService.findById(categoryId);
		if (optCategory.isEmpty()) {
			return new ResponseEntity<Response>(new Response(false, "Khôngtìm thấy Category", null),
					HttpStatus.BAD_REQUEST);
		} else if (optCategory.isPresent()) {
			categoryService.delete(optCategory.get());// return ResponseEntity.ok().body(category);
			return new ResponseEntity<Response>(new Response(true, "Xóa Thànhcông", optCategory.get()), HttpStatus.OK);
		}
		return null;
	}
}
