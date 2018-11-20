package com.gail.responseData;

public class VendorUploadData {
 private String vendorId;
 private String materialCode;
 private String adminId;
 public String getVendorId() {
  return vendorId;
 }
 public void setVendorId(String vendorId) {
  this.vendorId = vendorId;
 }
 public String getMaterialCode() {
  return materialCode;
 }
 public void setMaterialCode(String materialCode) {
  this.materialCode = materialCode;
 }
 
 
 public String getAdminId() {
  return adminId;
 }
 public void setAdminId(String adminId) {
  this.adminId = adminId;
 }
 @Override
 public String toString() {
  return "VendorUploadData [vendorId=" + vendorId + ", materialCode=" + materialCode + ", adminId=" + adminId
    + "]";
 }
 

}