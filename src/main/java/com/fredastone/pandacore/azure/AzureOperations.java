package com.fredastone.pandacore.azure;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.time.OffsetDateTime;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fredastone.pandacore.exception.StorageException;
import com.fredastone.pandacore.models.FileResponse;
import com.microsoft.azure.storage.blob.BlobSASPermission;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.SASProtocol;
import com.microsoft.azure.storage.blob.SASQueryParameters;
import com.microsoft.azure.storage.blob.ServiceSASSignatureValues;
import com.microsoft.azure.storage.blob.SharedKeyCredentials;



//class build from microsoft github testcases
@Service
public class AzureOperations implements IAzureOperations{


	@Value("${pandacore.azure.account.name}")
	private  String accountName;
	
	@Value("${pandacore.azure.account.key}")
	private  String accountKey;
	
	@Value("${pandacore.azure.access.minutes}")
	private  int accessMinutes;
		
	/*
	 * @Value("${fileuploadfolder}") private String fileuploadfolder;
	 */
	
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
	
	@Value("${profilephotosFolder}")
	private String profileUploadPath;
	
	@Value("${idcopyphotofolder}")
	private String idcopyUploadFolder;
	
	@Value("${reportfilesfolder}")
	private String reportUploadFolder;
	
	private static final String IMAGE_SUFFIX = ".png";
	private static final String PDF_SUFFIX = ".pdf";

	
	public AzureOperations() {
		
	}

	private SharedKeyCredentials getSharedAccessCredential() throws InvalidKeyException {

		return new SharedKeyCredentials(accountName, accountKey);

	}

	private String createAzureAccessToken(String containerName, String blobName, boolean isReadAccessOnly)
			throws MalformedURLException, InvalidKeyException {
		/*
		 * Set the desired SAS signature values and sign them with the shared key
		 * credentials to get the SAS query parameters.
		 */

		ServiceSASSignatureValues values = new ServiceSASSignatureValues().withProtocol(SASProtocol.HTTPS_ONLY) 
				.withStartTime(OffsetDateTime.now().minusMinutes(10))
				.withExpiryTime(OffsetDateTime.now().plusMinutes(this.accessMinutes))
				.withContainerName(containerName).withBlobName(blobName);
		// .withSnapshotId(snapshotId);

		/*
		 * To produce a container SAS (as opposed to a blob SAS), assign to Permissions
		 * using ContainerSASPermissions, and make sure the blobName and snapshotId
		 * fields are null (the default).
		 */
		BlobSASPermission permission = new BlobSASPermission();

		if (isReadAccessOnly) {
			permission.read();
		} else {
			permission.withRead(true).withAdd(true).withWrite(true).withCreate(Boolean.TRUE);
		}

		values.withPermissions(permission.toString());

		final SASQueryParameters params = values.generateSASQueryParameters(getSharedAccessCredential());

		// Calling encode will generate the query string.
		final String encodedParams = params.encode();
		// Colons are not safe characters in a URL; they must be properly encoded.

		return String.format(Locale.ROOT, "https://%s.blob.core.windows.net/%s/%s%s", accountName,
				containerName, blobName, encodedParams);

	}

	private String getImagePath(String prefix,String imageName) {
		
		return prefix+"_"+imageName+IMAGE_SUFFIX;
	}
	
	private String getPdfPath(String prefix,String pdfName) {
		return prefix+"_"+pdfName+PDF_SUFFIX;
		
	}
	
	@Override
	public String uploadProfile(String userId) throws InvalidKeyException, MalformedURLException {
		return createAzureAccessToken(profileUploadPath, getImagePath("profile",userId), Boolean.FALSE);
	}
	
	@Override
	public String getProfile(String userId) {
		// TODO Auto-generated method stub
		try {
			
			return createAzureAccessToken(profileUploadPath, getImagePath("profile",userId), Boolean.TRUE);
			
		} catch (InvalidKeyException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Log this error
		}
		
		return "";
	}

	@Override
	public String uploadAgentContract(String agentId) throws InvalidKeyException, MalformedURLException {
		return createAzureAccessToken(agentUploadFolder, getPdfPath("contract",agentId), Boolean.FALSE);
	}

	@Override
	public String getAgentContract(String agentId) {
		try {
			return createAzureAccessToken(agentUploadFolder, getPdfPath("contract",agentId), Boolean.TRUE);
		} catch (InvalidKeyException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return "";
	}

	@Override
	public String uploadAgentCertIncorp(String agentId) throws InvalidKeyException, MalformedURLException{
		return createAzureAccessToken(agentUploadFolder, getPdfPath("coi",agentId), Boolean.FALSE);
	}

	@Override
	public String getAgentCertIncorp(String agentId) {
		try {
			return createAzureAccessToken(agentUploadFolder, getPdfPath("coi",agentId), Boolean.TRUE);
		} catch (InvalidKeyException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}

	@Override
	public String uploadIdCopy(String userId) throws InvalidKeyException, MalformedURLException {
		return createAzureAccessToken(idcopyUploadFolder, getImagePath("idcopy",userId), Boolean.FALSE);
	}
	
	/*
	 * public String uploadCustConsent(String customerId) throws
	 * InvalidKeyException, MalformedURLException {
	 * 
	 * return createAzureAccessToken(customerUploadFolder,
	 * getImagePath("consent",customerId), Boolean.FALSE); }
	 */


	@Override
	public String getIdCopy(String userId) {
		try {
			return createAzureAccessToken(idcopyUploadFolder, getImagePath("idcopy",userId), Boolean.TRUE);
		} catch (InvalidKeyException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}

	@Override
	public String uploadCustConsent(String customerId) throws InvalidKeyException, MalformedURLException {
		
		return createAzureAccessToken(customerUploadFolder, getImagePath("consent",customerId), Boolean.FALSE);
	}

	@Override
	public String getCustConsent(String customerId) {
		try {
			return createAzureAccessToken(customerUploadFolder, getImagePath("consent",customerId), Boolean.TRUE);
		} catch (InvalidKeyException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}

	@Override
	public String uploadHousePhotoPath(String customerId) throws InvalidKeyException, MalformedURLException {
		return createAzureAccessToken(homePhotosUploadFolder, getImagePath("house",customerId), Boolean.FALSE);
	}

	@Override
	public String getHousePhotoPath(String customerId) {
		try {
			return createAzureAccessToken(homePhotosUploadFolder, getImagePath("house",customerId), Boolean.TRUE);
		} catch (InvalidKeyException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}

	@Override
	public String uploadProuctPicture(String productId) throws InvalidKeyException, MalformedURLException {
		return createAzureAccessToken(productsThumbnailFolder, getImagePath("prod",productId), Boolean.FALSE);
	}

	@Override
	public String uploadEquipmentPicture(String equipmentId) throws InvalidKeyException, MalformedURLException {
		return createAzureAccessToken(equipmentPhotoFolder, getImagePath("equip",equipmentId), Boolean.FALSE);
	}
	
	public FileResponse uploadToAzure(MultipartFile file,String urlString) throws URISyntaxException, StorageException, IOException, com.microsoft.azure.storage.StorageException {
		
		URI url = new URI(urlString);
		CloudBlockBlob blob = null;
		
		blob = new CloudBlockBlob(url);
		
		blob.uploadFromFile(convertFile(file).getAbsolutePath());
		
		FileResponse response = new FileResponse();
		response.setName(file.getOriginalFilename());
		response.setSize(file.getSize());
		response.setType(file.getContentType());
		response.setUri(urlString);
		
		return response;
	}
	
	public static File convertFile(MultipartFile file) throws IOException {
		
		 File convFile = new File(file.getOriginalFilename());
		 
		 convFile.createNewFile();
		 FileOutputStream fos = new FileOutputStream(convFile);
		 
		 fos.write(file.getBytes());
		 fos.close();
		 
		 return convFile;
		 
	}

	@Override
	public String getProductPicture(String productId) throws InvalidKeyException, MalformedURLException {
		return createAzureAccessToken(productsThumbnailFolder, getImagePath("prod",productId), Boolean.TRUE);
	}

	@Override
	public String getEquipmentPicture(String equipmentId) throws InvalidKeyException, MalformedURLException {
		return createAzureAccessToken(equipmentPhotoFolder, getImagePath("equip",equipmentId), Boolean.TRUE);
	}

	@Override
	public String uploadReportFile(String reportId) throws InvalidKeyException, MalformedURLException {
		// TODO Auto-generated method stub
		return createAzureAccessToken(reportUploadFolder, getPdfPath("report",reportId), Boolean.FALSE);
	}

	@Override
	public String getReportFile(String reportId) throws InvalidKeyException, MalformedURLException {
		// TODO Auto-generated method stub
		return createAzureAccessToken(reportUploadFolder, getPdfPath("report",reportId), Boolean.TRUE);
	}

}
