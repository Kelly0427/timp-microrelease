package com.timp.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WechatTokenRepository extends JpaRepository<WechatToken, Long>,WechatTokenRepositoryCustom{

}
