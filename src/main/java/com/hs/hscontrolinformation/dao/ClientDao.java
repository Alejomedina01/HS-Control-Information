package com.hs.hscontrolinformation.dao;

import com.hs.hscontrolinformation.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDao extends JpaRepository<Client, Long> {

}
