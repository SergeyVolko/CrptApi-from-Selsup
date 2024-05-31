package org.example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CrptApi {
    private static class Delay {
        private final TimeUnit timeUnit;
        private final int requestLimit;
        private long time;
        private int countRequest;

        public Delay(TimeUnit timeUnit, int requestLimit) {
            this.timeUnit = timeUnit;
            this.requestLimit = requestLimit;
        }

        public void delay() throws InterruptedException {
            if (countRequest == 0) {
                time = System.nanoTime();
            }
            if (countRequest == requestLimit) {
                long diff = time + timeUnit.toChronoUnit().getDuration().toNanos() - System.nanoTime();
                countRequest = 0;
                time = 0;
                TimeUnit.NANOSECONDS.sleep(diff);
                return;
            }
            countRequest++;
        }
    }

    @Generated("jsonschema2pojo")
    static class Description {

        @SerializedName("participantInn")
        @Expose
        private String participantInn;

        public String getParticipantInn() {
            return participantInn;
        }

        public void setParticipantInn(String participantInn) {
            this.participantInn = participantInn;
        }

    }

    @Generated("jsonschema2pojo")
    static class Product {

        @SerializedName("certificate_document")
        @Expose
        private String certificateDocument;
        @SerializedName("certificate_document_date")
        @Expose
        private String certificateDocumentDate;
        @SerializedName("certificate_document_number")
        @Expose
        private String certificateDocumentNumber;
        @SerializedName("owner_inn")
        @Expose
        private String ownerInn;
        @SerializedName("producer_inn")
        @Expose
        private String producerInn;
        @SerializedName("production_date")
        @Expose
        private String productionDate;
        @SerializedName("tnved_code")
        @Expose
        private String tnvedCode;
        @SerializedName("uit_code")
        @Expose
        private String uitCode;
        @SerializedName("uitu_code")
        @Expose
        private String uituCode;

        public String getCertificateDocument() {
            return certificateDocument;
        }

        public void setCertificateDocument(String certificateDocument) {
            this.certificateDocument = certificateDocument;
        }

        public String getCertificateDocumentDate() {
            return certificateDocumentDate;
        }

        public void setCertificateDocumentDate(String certificateDocumentDate) {
            this.certificateDocumentDate = certificateDocumentDate;
        }

        public String getCertificateDocumentNumber() {
            return certificateDocumentNumber;
        }

        public void setCertificateDocumentNumber(String certificateDocumentNumber) {
            this.certificateDocumentNumber = certificateDocumentNumber;
        }

        public String getOwnerInn() {
            return ownerInn;
        }

        public void setOwnerInn(String ownerInn) {
            this.ownerInn = ownerInn;
        }

        public String getProducerInn() {
            return producerInn;
        }

        public void setProducerInn(String producerInn) {
            this.producerInn = producerInn;
        }

        public String getProductionDate() {
            return productionDate;
        }

        public void setProductionDate(String productionDate) {
            this.productionDate = productionDate;
        }

        public String getTnvedCode() {
            return tnvedCode;
        }

        public void setTnvedCode(String tnvedCode) {
            this.tnvedCode = tnvedCode;
        }

        public String getUitCode() {
            return uitCode;
        }

        public void setUitCode(String uitCode) {
            this.uitCode = uitCode;
        }

        public String getUituCode() {
            return uituCode;
        }

        public void setUituCode(String uituCode) {
            this.uituCode = uituCode;
        }

    }

    @Generated("jsonschema2pojo")
    private static class Document {

        @SerializedName("description")
        @Expose
        private Description description;
        @SerializedName("doc_id")
        @Expose
        private String docId;
        @SerializedName("doc_status")
        @Expose
        private String docStatus;
        @SerializedName("doc_type")
        @Expose
        private String docType;
        @SerializedName("importRequest")
        @Expose
        private Boolean importRequest;
        @SerializedName("owner_inn")
        @Expose
        private String ownerInn;
        @SerializedName("participant_inn")
        @Expose
        private String participantInn;
        @SerializedName("producer_inn")
        @Expose
        private String producerInn;
        @SerializedName("production_date")
        @Expose
        private String productionDate;
        @SerializedName("production_type")
        @Expose
        private String productionType;
        @SerializedName("products")
        @Expose
        private List<Product> products;
        @SerializedName("reg_date")
        @Expose
        private String regDate;
        @SerializedName("reg_number")
        @Expose
        private String regNumber;

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public String getDocId() {
            return docId;
        }

        public void setDocId(String docId) {
            this.docId = docId;
        }

        public String getDocStatus() {
            return docStatus;
        }

        public void setDocStatus(String docStatus) {
            this.docStatus = docStatus;
        }

        public String getDocType() {
            return docType;
        }

        public void setDocType(String docType) {
            this.docType = docType;
        }

        public Boolean getImportRequest() {
            return importRequest;
        }

        public void setImportRequest(Boolean importRequest) {
            this.importRequest = importRequest;
        }

        public String getOwnerInn() {
            return ownerInn;
        }

        public void setOwnerInn(String ownerInn) {
            this.ownerInn = ownerInn;
        }

        public String getParticipantInn() {
            return participantInn;
        }

        public void setParticipantInn(String participantInn) {
            this.participantInn = participantInn;
        }

        public String getProducerInn() {
            return producerInn;
        }

        public void setProducerInn(String producerInn) {
            this.producerInn = producerInn;
        }

        public String getProductionDate() {
            return productionDate;
        }

        public void setProductionDate(String productionDate) {
            this.productionDate = productionDate;
        }

        public String getProductionType() {
            return productionType;
        }

        public void setProductionType(String productionType) {
            this.productionType = productionType;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        public String getRegNumber() {
            return regNumber;
        }

        public void setRegNumber(String regNumber) {
            this.regNumber = regNumber;
        }

    }
}
