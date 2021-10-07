package hu.javorekdenes.hwtracer.gcloud;

import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GCloud {
	private static final Logger logger = LoggerFactory.getLogger(GCloud.class);

	private String projectId;
	private Firestore firestoreDB;

	public GCloud(@Value("${hu.javorekdenes.gcloud.projectname}") String projectId) {
		this.projectId = projectId;
	}

	@Bean
	public Firestore getFirestoreDB() throws GCloudAuthException {
		if (this.firestoreDB != null) {
			return this.firestoreDB;
		}

		FirestoreOptions firestoreOptions = null;
		try {
			firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId(this.projectId)
					.setCredentials(GoogleCredentials.getApplicationDefault()).build();
		} catch (IOException e) {
			logger.warn("Cannot find application default service key.");
			throw new GCloudAuthException(e);
		}

		this.firestoreDB = firestoreOptions.getService();
		return this.firestoreDB;
	}
}