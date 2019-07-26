package com.timp.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TempMessageRepository extends JpaRepository<TempMessage, Long>,TempMessageRepositoryCustom{

	List<TempMessage> findById(Long id);

}
