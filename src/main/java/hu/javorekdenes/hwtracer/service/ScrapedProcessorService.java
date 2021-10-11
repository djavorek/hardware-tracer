package hu.javorekdenes.hwtracer.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ScrapedProcessorService {
	private static final Logger logger = LoggerFactory.getLogger(ScrapedProcessorService.class);
	Firestore firestore;

	@Autowired
	public ScrapedProcessorService(Firestore firestore) {
		this.firestore = firestore;
	}

	public void testFetch() {
		logger.info("Fetching");
		ApiFuture<QuerySnapshot> future = this.firestore.collection("videocards").whereEqualTo("id", "4824372").get();

		 try {
		 	List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		 	for (DocumentSnapshot document : documents) {
		 		logger.info("Name for the id: " + document.getString("name"));
		 	}
		 } catch (InterruptedException e) {
		 	// TODO Auto-generated catch block
		 	e.printStackTrace();
		 } catch (ExecutionException e) {
		 	// TODO Auto-generated catch block
		 	e.printStackTrace();
		 }
	}
}
