package org.example;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.annotation.Generated;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CrptApi {

    private final Delay delay;
    private final PostDocumentCreated documentCreated;

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.delay = new Delay(timeUnit, requestLimit);
        this.documentCreated = new PostDocumentCreated();
    }

    /**
     *
     * @param document - передаваемый документ
     * @param signature - подпись документа
     * @throws InterruptedException
     */
    public synchronized void createDocument(Document document, String signature) throws InterruptedException {
        delay.delay();
        CloseableHttpResponse response = documentCreated.requestDocument(document, signature);
        if (response == null) {
            return;
        }
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            System.out.println("Document successfully created");
        } else {
            System.out.println("Failed to create document. Status code: " + statusCode);
        }
        System.out.println("test");
    }
    public static void main(String[] args) throws InterruptedException {
        JSONDocumentConverter jsonDocumentConverter = new JSONDocumentConverter();
        String json = """
                {
                  "document" : {
                  "description": { "participantInn": "string" },\s
                  "doc_id": "string",\s
                  "doc_status": "string",\s
                  "doc_type": "LP_INTRODUCE_GOODS",\s
                  "importRequest": true,\s
                  "owner_inn": "string",\s
                  "participant_inn": "string",\s
                  "producer_inn": "string",\s
                  "production_date": "2020-01-23",\s
                  "production_type": "string",\s
                  "products": [\s
                    { "certificate_document": "string",\s
                      "certificate_document_date": "2020-01-23",\s
                      "certificate_document_number": "string",\s
                      "owner_inn": "string",\s
                      "producer_inn": "string",\s
                      "production_date": "2020-01-23",\s
                      "tnved_code": "string", "uit_code": "string",\s
                     "uitu_code": "string" }\s
                  ],\s
                  "reg_date": "2020-01-23",\s
                  "reg_number": "string"
                  },
                  "signature" : "string"
                }
                """;
        DocumentSender documentSender = jsonDocumentConverter.getDocumentSenderFromJson(json);
        System.out.println(documentSender);
        String jsonResult = jsonDocumentConverter.getJsonFromDocumentSender(documentSender);
        System.out.println(jsonResult);
        Document document = documentSender.getDocument();
        String signature = documentSender.getSignature();
        CrptApi crptApi = new CrptApi(TimeUnit.MINUTES, 1);
        for (int i = 0; i < 10; i++) {
            crptApi.createDocument(document, signature);
        }
    }


    /**
     * Класс позволяющий выполнить post запрос на добавление доумента
     */
    private static class PostDocumentCreated {
        private static final String API_URL = "https://ismp.crpt.ru/api/v3/lk/documents/create";
        private final JSONDocumentConverter converter;

        private PostDocumentCreated() {
            this.converter = new JSONDocumentConverter();
        }

        /**
         * Метод выполняющий post запрос и возвращающий ответ.
         * В нем формируется подписанный документ и отправляется методом post
         * @param document - не подписанный документ
         * @param signature - подпись документа
         * @return - ответ на post запрос
         */
        public CloseableHttpResponse requestDocument(Document document, String signature) {
            try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(API_URL);
                httpPost.setHeader("Content-Type", "application/json");
                DocumentSender documentSender = new DocumentSender();
                documentSender.setDocument(document);
                documentSender.setSignature(signature);
                String requestBody = converter.getJsonFromDocumentSender(documentSender);
                StringEntity entity = new StringEntity(requestBody);
                httpPost.setEntity(entity);
                try(CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    return response;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Класс, ограничивающий количество вызовов метода
     */
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
                TimeUnit.NANOSECONDS.sleep(diff);
                time = System.nanoTime();
            }
            countRequest++;
        }
    }

    /**
     * Конвертер объекта DocumentSender в Json и обратно
     */
    private static class JSONDocumentConverter {
        private final Gson gson = new Gson();

        public DocumentSender getDocumentSenderFromJson(String json) {
            return gson.fromJson(json, DocumentSender.class);
        }

        public String getJsonFromDocumentSender(DocumentSender document) {
            return gson.toJson(document, DocumentSender.class);
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


    private enum TypeDoc {
        LP_INTRODUCE_GOODS
    }
    @Generated("jsonschema2pojo")
    static class Document {

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
        private TypeDoc docType;
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

        public TypeDoc getDocType() {
            return docType;
        }

        public void setDocType(TypeDoc docType) {
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

    @Generated("jsonschema2pojo")
    private static class DocumentSender {

        @SerializedName("document")
        @Expose
        private Document document;
        @SerializedName("signature")
        @Expose
        private String signature;

        public Document getDocument() {
            return document;
        }

        public void setDocument(Document document) {
            this.document = document;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

    }
}
