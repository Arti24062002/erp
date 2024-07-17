package com.erp.erp.EmployeeBankInformation;

public class BankInfoModelMapper {
    public static BankInfo mapToBankInfo(BankInfoRequest request) {
        if (request == null) {
            return null;
        }

        BankInfo bankInfo = new BankInfo();
        bankInfo.setBank(request.getBank());
        bankInfo.setIfsc(request.getIfsc());
        bankInfo.setNumber(request.getNumber());
        bankInfo.setUserId(request.getUserId());

        return bankInfo;
    }

    public static BankInfoResponse mapToBankInfoResponse(BankInfo bankInfo) {
        if (bankInfo == null) {
            return null;
        }

        BankInfoResponse response = new BankInfoResponse();
        response.setBank(bankInfo.getBank());
        response.setIfsc(bankInfo.getIfsc());
        response.setNumber(bankInfo.getNumber());
        response.setUserId(bankInfo.getUserId());

        return response;
    }
    public static BankInfo updateBankInfo(BankInfo existingBankInfo, BankInfoRequest request) {
        if (existingBankInfo == null || request == null) {
            return null;
        }

        existingBankInfo.setBank(request.getBank());
        existingBankInfo.setIfsc(request.getIfsc());
        existingBankInfo.setNumber(request.getNumber());
        existingBankInfo.setUserId(request.getUserId());

        return existingBankInfo;
    }
}
