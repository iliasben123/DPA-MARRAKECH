package com.PFA.DPA_MARRAKECH.Repository;
import com.PFA.DPA_MARRAKECH.Model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    Optional<Farmer> findByCin(String cin);

}

