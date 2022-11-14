package com.currencyexchange.repository;

import com.currencyexchange.domain.AccountDomain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<AccountDomain, String> {

    Optional<AccountDomain> findByAccountId(String accountId);

    Optional<AccountDomain>findFirstByOrderByOwnerDesc();
}