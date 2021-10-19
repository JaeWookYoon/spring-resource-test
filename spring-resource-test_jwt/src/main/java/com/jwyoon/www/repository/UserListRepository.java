package com.jwyoon.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwyoon.www.model.UserList;

import java.util.List;

@Repository("userListRepository")
public interface UserListRepository extends JpaRepository<UserList, String>{

    List<UserList> findByIdx(String idx);
    UserList findByUserId(String userId);
	/*
	 * boolean existsByUserEmail(String userEmail); boolean
	 * existsByUserNickname(String userNickname); boolean existsByUserPhone(String
	 * userPhone); boolean existsByUserMobile(String userMobile); boolean
	 * existsByRecommendCode(String recommendCode);
	 */

}
