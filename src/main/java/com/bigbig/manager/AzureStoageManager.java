package com.bigbig.manager;


import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.file.CloudFile;
import com.microsoft.azure.storage.file.CloudFileClient;
import com.microsoft.azure.storage.file.CloudFileDirectory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

@RestController
@Slf4j
public class AzureStoageManager {

    @Value("${storage.connection.string}")
    private String StorageConnectionString;

    @GetMapping(value = "/clearOldFile")
    public void clearOldFile(){
        log.info("clearOldFile start ...");
        CloudStorageAccount storageAccount = null;
        CloudFileClient fileClient = null;
        try {
            storageAccount = CloudStorageAccount.parse(StorageConnectionString);
            fileClient = storageAccount.createCloudFileClient();
            fileClient.getShareReference("sampleshare").getRootDirectoryReference().listFilesAndDirectories()
                    .forEach(fileItem -> {
                        if (fileItem instanceof  CloudFile){
                            CloudFile file  =(CloudFile)fileItem;
                            try {
                                file.downloadAttributes();
                                log.info("file {},last modified date {}",file.getName(),file.getProperties().getLastModified());
                            } catch (StorageException e) {
                                e.printStackTrace();
                                log.error("download attributes error {}" ,e);
                            }
                        }else if(fileItem instanceof CloudFileDirectory){
                            log.info("Directory: {}",((CloudFileDirectory) fileItem).getName());
                        }

                    });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }

    }
}