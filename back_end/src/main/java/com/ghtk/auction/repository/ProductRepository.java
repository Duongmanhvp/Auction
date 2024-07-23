package com.ghtk.auction.repository;

import com.ghtk.auction.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
	
	@Query(value = "SELECT \n" +
			"    u.full_name AS owner,\n" +
			"    p.name AS name,\n" +
			"    p.category AS category,\n" +
			"    p.description AS description,\n" +
			"    p.image AS image\n" +
			"FROM \n" +
			"    product p\n" +
			"JOIN \n" +
			"    User u ON p.owner_id = u.id\n" +
			"WHERE \n" +
			"    u.id = :o", nativeQuery = true)
	List<Object[]> findByOwnerId(@Param("o") Long ownerId);
	
}
