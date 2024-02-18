package com.caresure.repository;

import com.caresure.pojo.UploadFiles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UploadFilesRepo extends JpaRepository<UploadFiles, Long> {

    UploadFiles findByClaimId(Long claimId);
}
