package com.wtd.assistant.frontend.dao;

import com.wtd.assistant.frontend.domain.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CertificateDao extends JpaRepository<Certificate, Integer> {
}
