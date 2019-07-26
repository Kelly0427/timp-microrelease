package com.timp.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HeadlinesRepository extends JpaRepository<Headlines, Long>,HeadlinesRepositoryCustom{
	
}
