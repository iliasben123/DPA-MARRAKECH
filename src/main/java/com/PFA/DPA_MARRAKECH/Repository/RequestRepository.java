package com.PFA.DPA_MARRAKECH.Repository;
import com.PFA.DPA_MARRAKECH.Model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, String> {
}
