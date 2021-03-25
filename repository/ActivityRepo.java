package ro.tuc.ds2020.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.model.Activity;

import java.util.UUID;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, UUID> {
}
