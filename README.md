# Getting starterd

## How to start the application
### Setting Google Cloud credentials
- Google Cloud client library can authenticate automatically inside the Google Cloud environment (see: Compute Engine).
- However, running outside of it, you are required to take care of authentication.
   - This can be done by setting ENV variable (_GOOGLE_APPLICATION_CREDENTIALS_) with the path of a KEY file. Which is JSON file you can obtain for service accounts.
   - 📄 **Detailed instructions**: https://cloud.google.com/docs/authentication/production#passing_variable

### Setting up testing environment
- Run `gcloud components install cloud-firestore-emulator`
to install required emulator for tests into your Google Cloud SDK.