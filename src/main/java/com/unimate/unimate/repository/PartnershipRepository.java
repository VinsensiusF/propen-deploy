package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Partnership;


import org.springframework.data.jpa.repository.JpaRepository;


public interface PartnershipRepository extends JpaRepository<Partnership, Long> {
    Partnership findPartnershipById(Long id);
    Partnership findPartnershipByUser(Account user);
}
