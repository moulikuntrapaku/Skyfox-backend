package com.booking.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.event.ListDataEvent;
import java.util.List;

@Repository
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {

    List<PasswordHistory> findAll();

    PasswordHistory findTopByUserIdOrderByEntrytimeDesc(Long userid);

    List<PasswordHistory> findTop3ByUserIdOrderByEntrytimeDesc(Long userid);

}