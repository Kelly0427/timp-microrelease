package com.timp.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenIdRepository extends JpaRepository<OpenId, Long>,OpenIdRepositoryCustom {

}
