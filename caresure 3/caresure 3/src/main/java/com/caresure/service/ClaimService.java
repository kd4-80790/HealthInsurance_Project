package com.caresure.service;

import com.caresure.dto.SubmitClaimDTO;
import com.caresure.pojo.Claims;
import com.caresure.pojo.Transactions;
import com.caresure.pojo.UploadFiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ClaimService {
    List<Transactions> getAllPolicies();

    String createTransaction(Transactions transaction);

    List<Claims> getAllClaims();

    String addClaim(Claims claim);

    String submitClaim(SubmitClaimDTO claim, MultipartFile file) throws IOException;

    List<Claims> getAllClaimsByUsername(String username);

    UploadFiles getClaimDocuments(Long claimId);
}
