package com.booking.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {

    @Query(value =
            "SELECT * FROM PASSWORDHISTORY" +
            " WHERE USERID = :userid " +
            "ORDER BY ENTRYTIME DESC limit(3);", nativeQuery=true)
    List<PasswordHistory> findPasswordByUserid(@Param("userid")  Long userid);
}