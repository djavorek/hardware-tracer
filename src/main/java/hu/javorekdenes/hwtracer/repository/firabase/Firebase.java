package hu.javorekdenes.hwtracer.repository.firabase;

import hu.javorekdenes.hwtracer.repository.firabase.adapter.FirestoreAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Firebase {
    protected FirestoreAdapter firestore;

    public Firebase(FirestoreAdapter firestore) {
        this.firestore = firestore;
    }

    @Autowired
    public final void setFirestore() {
        this.firestore = firestore;
    }
}
