package hu.javorekdenes.hwtracer.service;

import hu.javorekdenes.hwtracer.model.Hardware;
import hu.javorekdenes.hwtracer.model.Hardwares;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.FirestoreAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapedProcessorService {
	private static final Logger logger = LoggerFactory.getLogger(ScrapedProcessorService.class);
	private FirestoreAdapter firestore;

	@Autowired
	public ScrapedProcessorService(FirestoreAdapter firestoreAdapter) {
		this.firestore = firestoreAdapter;
	}

	public void testFetch() {
		logger.info("Fetching");
		Hardwares resultsWithId = firestore.getFromCollectionWhereFieldIs("videocards", "id", "4824372");

		System.out.println(resultsWithId.getAll().get(0));
	}
}
