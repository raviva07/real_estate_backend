package com.realestate.repository;
import com.realestate.entity.enums.PropertyType;
import com.realestate.entity.Property;
import com.realestate.entity.User;
import com.realestate.entity.enums.PropertyStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Dashboard
	List<Property> findByStatus(PropertyStatus status);

    List<Property> findByOwnerEmail(String email);

    // 🔥 FIXED SEARCH
    @Query("SELECT p FROM Property p WHERE " +
            "p.status = 'APPROVED' AND " +
            "(:location IS NULL OR p.location LIKE %:location%) AND " +
            "(:type IS NULL OR p.type = :type) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Property> searchProperties(
            @Param("location") String location,
            @Param("type") PropertyType type, // ✅ FIXED
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );

    // Search (filters)
    List<Property> findByLocationContainingAndPriceBetweenAndTypeAndStatus(
            String location,
            Double minPrice,
            Double maxPrice,
            String type,
            String status
    );
    

    // Keyword search
    List<Property> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndStatus(
            String title,
            String description,
            String status
    );
    
   
}
