package com.caresure.service;

import com.caresure.dto.SubmitClaimDTO;
import com.caresure.pojo.Claims;
import com.caresure.pojo.Transactions;
import com.caresure.pojo.UploadFiles;
import com.caresure.repository.ClaimsRepo;
import com.caresure.repository.TransactionRepo;
import com.caresure.repository.UploadFilesRepo;
import com.caresure.util.Fileutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ClaimServiceImpl implements ClaimService{
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private UploadFilesRepo uploadFilesRepo;
    @Autowired
    private ClaimsRepo claimsRepo;
    @Override
    public List<Transactions> getAllPolicies() {
        return transactionRepo.findAll();
    }

    @Override
    public String createTransaction(Transactions transaction) {
        transaction.setTransactionDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        transactionRepo.save(transaction);
        return "Transaction saved";
    }

    @Override
    public List<Claims> getAllClaims() {
        return claimsRepo.findAll();
    }

    @Override
    public String addClaim(Claims claim) {
        claimsRepo.save(claim);
        return "Claim saved";
    }

    @Override
    public String submitClaim(SubmitClaimDTO submitClaimDTO, MultipartFile file) throws IOException {
        Claims claim =new Claims();
        claim.setClaimType(submitClaimDTO.getClaimType());
        claim.setAmount(submitClaimDTO.getTotalAmount());
        claim.setDiagnosis(submitClaimDTO.getDiagnosis());
        claim.setDateOfService(submitClaimDTO.getDateOfService());
        claim.setPolicyHolderName(submitClaimDTO.getPolicyHolderName());
        claim.setPolicyNumber(submitClaimDTO.getPolicyId());
        claim.setStatus("Pending");
       Claims savedClaim = claimsRepo.save(claim);
        uploadFilesRepo.save(UploadFiles.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .claimId(savedClaim.getId())
                .file(Fileutil.compressImage(file.getBytes())).build());

        return "Claim submit successfully.";
    }

    @Override
    public List<Claims> getAllClaimsByUsername(String username) {
        return claimsRepo.findByPolicyHolderUsername(username);
    }
    @Override
    public UploadFiles getClaimDocuments(Long claimId) {
        return uploadFilesRepo.findByClaimId(claimId);

    }
}
