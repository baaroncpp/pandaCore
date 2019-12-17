package com.fredastone.pandacore.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fredastone.pandacore.exception.StorageException;
import com.fredastone.pandacore.exception.StorageFileNotFoundException;
import com.fredastone.pandacore.service.StorageService;


@Service
public class FileSystemStorageService implements StorageService {

    private Path rootLocation;
    

	@Value("${fileuploadfolder}")
	private String fileuploadfolder;
	
	@Value("${equipmentphotosfolder}")
	private String equipmentPhotoFolder;
	
	@Value("${productsphotosfolder}")
	private String productsThumbnailFolder;

	@Value("${employeephotosfolder}")
	private String employeePhotosFolder;
	
	@Value("${customerUploadfolder}")
	private String customerUploadFolder;
	
	@Value("${agentUploadFolder}")
	private String agentUploadFolder;
		
	@Value("${homePhotosFolder}")
	private String homePhotosUploadFolder;
	
    @Override
    public void store(MultipartFile file,String filename) {
    	this.rootLocation = Paths.get(fileuploadfolder);
        //String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (file.getOriginalFilename().contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
    	this.rootLocation = Paths.get(fileuploadfolder);
        try {
            return Files.walk(this.rootLocation, 1)
                .filter(path -> !path.equals(this.rootLocation))
                .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
    	this.rootLocation = Paths.get(fileuploadfolder);
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
    	
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
        
    }

    @Override
    public void deleteAll() {
    	this.rootLocation = Paths.get(fileuploadfolder);
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
    	this.rootLocation = Paths.get(fileuploadfolder);
    	
        try {
            Files.createDirectories(rootLocation);
            Files.createDirectories(Paths.get(fileuploadfolder, equipmentPhotoFolder));
            Files.createDirectories(Paths.get(fileuploadfolder, productsThumbnailFolder));
            Files.createDirectories(Paths.get(fileuploadfolder, employeePhotosFolder));
            Files.createDirectories(Paths.get(fileuploadfolder, customerUploadFolder));
            Files.createDirectories(Paths.get(fileuploadfolder, agentUploadFolder));
            Files.createDirectories(Paths.get(fileuploadfolder, homePhotosUploadFolder));
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
    
    public boolean deleteFile(String filename) {
    	
    	boolean result = false;
    	
	
		if(loadAsResource(filename) != null) {
			try {
				result = loadAsResource(filename).getFile().delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
    	return result;
    }
    
    public void replaceFile(MultipartFile file, String filename) {
    	
    	if(!deleteFile(filename)) {
    		throw new RuntimeException("File not deleted: "+filename);
    	}
    	
    	store(file, filename);
    }
}
