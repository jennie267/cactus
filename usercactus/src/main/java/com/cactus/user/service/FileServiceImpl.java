package com.cactus.user.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.cactus.user.domain.FileRepository;
import com.cactus.user.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CactusUserApi3
 * Class: FileServiceImpl
 * Created by hapo on 2019-11-29.
 * Description:
 */

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    FileRepository fileRepository;

    // S3 client
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(Const.NAVER_CLOUD_STORAGE_END_POINT, Const.NAVER_CLOUD_STORAGE_REGION))
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(Const.NAVER_CLOUD_STORAGE_ACCESS_KEY, Const.NAVER_CLOUD_STORAGE_SECRET_KEY)))
            .build();

    @Override
    public String profileUpload(int tableId, String fileName, MultipartFile multipartFile) {
        File conFile;
        String ext;
        try{
            String multipartFileName = multipartFile.getOriginalFilename();
            ext = multipartFile.getOriginalFilename().substring(multipartFileName.lastIndexOf(".")+1);
            fileName = fileName+"."+ext;

            conFile = new File("/tmp/"+fileName);
            conFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(conFile);
            fos.write(multipartFile.getBytes());
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("ERROR:"+e);
            return null;
        }

        File file = conFile;
        long contentLength = file.length();
        long partSize = 10 * 1024 * 1024;

        try {
            // initialize and get upload ID
            InitiateMultipartUploadResult initiateMultipartUploadResult = s3.initiateMultipartUpload(new InitiateMultipartUploadRequest(Const.NAVER_CLOUD_STORAGE_USER_BUCKET_NAME+Const.NAVER_CLOUD_STORAGE_USER_PROFILE_PATH, fileName));
            String uploadId = initiateMultipartUploadResult.getUploadId();

            // upload parts
            List<PartETag> partETagList = new ArrayList<PartETag>();

            long fileOffset = 0;
            for (int i = 1; fileOffset < contentLength; i++) {
                partSize = Math.min(partSize, (contentLength - fileOffset));

                UploadPartRequest uploadPartRequest = new UploadPartRequest()
                        .withBucketName(Const.NAVER_CLOUD_STORAGE_USER_BUCKET_NAME+Const.NAVER_CLOUD_STORAGE_USER_PROFILE_PATH)
                        .withKey(fileName)
                        .withUploadId(uploadId)
                        .withPartNumber(i)
                        .withFile(file)
                        .withFileOffset(fileOffset)
                        .withPartSize(partSize);

                UploadPartResult uploadPartResult = s3.uploadPart(uploadPartRequest);
                partETagList.add(uploadPartResult.getPartETag());

                fileOffset += partSize;
            }

            // abort
            // s3.abortMultipartUpload(new AbortMultipartUploadRequest(bucketName, objectName, uploadId));

            // complete
            CompleteMultipartUploadResult completeMultipartUploadResult = s3.completeMultipartUpload(new CompleteMultipartUploadRequest(Const.NAVER_CLOUD_STORAGE_USER_BUCKET_NAME+Const.NAVER_CLOUD_STORAGE_USER_PROFILE_PATH, fileName, uploadId, partETagList));

            AccessControlList accessControlList = s3.getObjectAcl(Const.NAVER_CLOUD_STORAGE_USER_BUCKET_NAME+Const.NAVER_CLOUD_STORAGE_USER_PROFILE_PATH, fileName);
            accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);

            s3.setObjectAcl(Const.NAVER_CLOUD_STORAGE_USER_BUCKET_NAME+Const.NAVER_CLOUD_STORAGE_USER_PROFILE_PATH, fileName,accessControlList);

        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch(SdkClientException e) {
            e.printStackTrace();
        }

        String profileUrl = Const.NAVER_CLOUD_STORAGE_END_POINT+"/"+
                Const.NAVER_CLOUD_STORAGE_USER_BUCKET_NAME+""+
                Const.NAVER_CLOUD_STORAGE_USER_PROFILE_PATH+"/"+ fileName;

        com.cactus.user.domain.File profileFile = com.cactus.user.domain.File.builder()
                .name(fileName)
                .ext(ext)
                .table_name("user")
                .table_id(tableId)
                .file_url(profileUrl)
                .build();

        fileRepository.save(profileFile);


        return Const.NAVER_CLOUD_STORAGE_END_POINT+"/"+
                Const.NAVER_CLOUD_STORAGE_USER_BUCKET_NAME+""+
                Const.NAVER_CLOUD_STORAGE_USER_PROFILE_PATH+"/"+ fileName;
    }
}
