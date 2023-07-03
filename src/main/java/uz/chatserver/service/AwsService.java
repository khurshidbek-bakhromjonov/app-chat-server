package uz.chatserver.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

@Service
public class AwsService {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    public AwsService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) {
        try {
            File fileObj = convertMultiPartFileToFile(file);
            String fileName = generateFilePath(file.getOriginalFilename());
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
//            fileObj.delete();
            return "File uploaded : " + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename());
        }
    }

    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }
        return convertedFile;
    }

    private String generateFilePath(String fileName) {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();
        String uniqueFilename = System.currentTimeMillis() + "_" + fileName;
        return "uploads/" + year + "/" + month + "/" + day + "/" + uniqueFilename;
    }
}
