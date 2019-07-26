package com.timp.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WechatTextMessageRepository extends JpaRepository<WechatTextMessage, Long>,WechatTextMessageRepositoryCustom{

	List<WechatTextMessage> findById(Long id);
}
