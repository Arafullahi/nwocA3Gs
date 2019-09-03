package com.nwoc.a3gs.group.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.nwoc.a3gs.group.app.model.Candidates;

public interface CandidatesRepository extends JpaRepository<Candidates, Long> ,PagingAndSortingRepository<Candidates, Long> {

}
