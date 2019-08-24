package com.nwoc.a3gs.group.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nwoc.a3gs.group.app.model.MailService;

public interface MailRepository extends JpaRepository<MailService, Long>  {

}
