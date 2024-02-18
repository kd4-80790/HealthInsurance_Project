package com.caresure.controller;

import com.caresure.dto.SubmitClaimDTO;
import com.caresure.pojo.Claims;
import com.caresure.pojo.Transactions;
import com.caresure.pojo.UploadFiles;
import com.caresure.service.ClaimService;
import com.caresure.util.Fileutil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/claim")
public class ClaimController {
    @Autowired
    private ClaimService claimService;
    @Autowired
    private ObjectMapper mapper;
    @PostMapping("/add/transaction")
    public String createTransaction(@RequestBody Transactions transaction){
        return claimService.createTransaction(transaction);
    }
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<Transactions> getAllTransactions(){
        return claimService.getAllPolicies();
    }

    @PostMapping("/create")
    public String addClaim(@RequestBody Claims claim){
        return claimService.addClaim(claim);
    }
    @PostMapping("/submit")
    @PreAuthorize("hasAnyRole('NORMAL')")
    public String submitClaim(@RequestParam("claim") String claimData, @RequestParam("file") MultipartFile file) throws IOException {
        SubmitClaimDTO submitClaimDTO=mapper.readValue(claimData, SubmitClaimDTO.class);
        return claimService.submitClaim(submitClaimDTO, file);
    }
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
    public List<Claims> getAllClaimsByUsername(@RequestParam("username") String username){
        return claimService.getAllClaimsByUsername(username);
    }
    @GetMapping("/getAllClaims")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<Claims> getAllClaims(){
        return claimService.getAllClaims();
    }
    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String updateClaimStatus(@RequestBody Claims claim){
        return claimService.addClaim(claim);
    }

    @GetMapping("/download/claim/file")
    @PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
    public ResponseEntity<?> getClaimDocuments(@RequestParam("claimid") Long claimId){
        UploadFiles files = claimService.getClaimDocuments(claimId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + files.getName() + "\"")
                .body(files.getFile());
    }
}
