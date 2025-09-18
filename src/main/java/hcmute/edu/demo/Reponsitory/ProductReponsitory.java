package hcmute.edu.demo.Reponsitory;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.edu.demo.Entity.Product;

public interface  ProductReponsitory extends JpaRepository<Product, Integer>  {
	//Tìm Kiếm theo nội dung tên
	List<Product> findByProductNameContaining(String name);
	//Tìm kiếm và Phân trang
	Page<Product> findByProductNameContaining(String name,Pageable pageable);
	Optional<Product> findByProductName(String name);
	Optional<Product> findByCreateDate(Date createAt);

}
