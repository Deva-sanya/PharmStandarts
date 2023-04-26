package myProject.repositories;

import myProject.models.Standard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StandardRepository extends JpaRepository<Standard, Integer> {
    List<Standard> findStandardByNameStartsWith(String name);
}
