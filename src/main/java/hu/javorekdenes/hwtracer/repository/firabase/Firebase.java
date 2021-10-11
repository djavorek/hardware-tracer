package hu.javorekdenes.hwtracer.repository.firabase;

import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Firebase {
    protected Firestore firestore;

    @Autowired
    public final void setFirestore(Firestore firestore) {
        this.firestore = firestore;
    }
}
