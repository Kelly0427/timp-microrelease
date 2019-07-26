package com.timp.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WBTokenStateRepository extends JpaRepository<WBTokenState, Long>,WBTokenStateRepositoryCustom{

}
