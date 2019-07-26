package com.timp.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleMessageRepository  extends JpaRepository<ArticleMessage, Long>,ArticleMessageRepositoryCustom{

	List<ArticleMessage> findById(Long id);
}
