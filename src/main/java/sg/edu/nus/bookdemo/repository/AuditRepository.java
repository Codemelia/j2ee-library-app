package sg.edu.nus.bookdemo.repository;

import org.springframework.stereotype.Repository;

import sg.edu.nus.bookdemo.model.AuditEntry;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntry, Long> {
	
}
