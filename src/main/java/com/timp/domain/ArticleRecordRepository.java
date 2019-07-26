package com.timp.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRecordRepository extends JpaRepository<ArticleRecord, Long>,ArticleRecordRepositoryCustom{
	ArticleRecord findByTitle(String title);
}
