package com.fredastone.pandacore.azure;

import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.time.OffsetDateTime;
import java.util.Locale;

import com.microsoft.azure.storage.blob.BlobSASPermission;
import com.microsoft.azure.storage.blob.SASProtocol;
import com.microsoft.azure.storage.blob.SASQueryParameters;
import com.microsoft.azure.storage.blob.ServiceSASSignatureValues;
import com.microsoft.azure.storage.blob.SharedKeyCredentials;

//class build from microsoft github testcases
public class AzureOperations {

	private final String accountName;
	private final String accountKey;
	private final int accessMinutes;

	public AzureOperations(String accountName, String accountKey,int accessMinutes) {
		this.accountName = accountName;
		this.accountKey = accountKey;
		this.accessMinutes = accessMinutes;
	}

	private SharedKeyCredentials getSharedAccessCredential() throws InvalidKeyException {

		return new SharedKeyCredentials(accountName, accountKey);

	}

	public String createAzureAccessToken(String containerName, String blobName, boolean isReadAccessOnly)
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

}
