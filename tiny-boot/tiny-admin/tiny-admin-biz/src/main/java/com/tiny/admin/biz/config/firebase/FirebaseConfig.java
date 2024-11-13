package com.tiny.admin.biz.config.firebase;

import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Policy;
import com.google.cloud.ReadChannel;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {
  @Bean
  public Storage storage() throws IOException {
    try {
    InputStream resourceAsStream;
      Credentials credentials = GoogleCredentials.fromStream(getClass().getClassLoader().getResourceAsStream("firebase.json"));
      return StorageOptions.newBuilder().setCredentials(credentials).build().getService();

    } catch (Exception e) {
      return new Storage() {
        @Override
        public Bucket create(BucketInfo bucketInfo, BucketTargetOption... bucketTargetOptions) {
          return null;
        }

        @Override
        public Blob create(BlobInfo blobInfo, BlobTargetOption... blobTargetOptions) {
          return null;
        }

        @Override
        public Blob create(BlobInfo blobInfo, byte[] bytes, BlobTargetOption... blobTargetOptions) {
          return null;
        }

        @Override
        public Blob create(BlobInfo blobInfo, byte[] bytes, int i, int i1, BlobTargetOption... blobTargetOptions) {
          return null;
        }

        @Override
        public Blob create(BlobInfo blobInfo, InputStream inputStream, BlobWriteOption... blobWriteOptions) {
          return null;
        }

        @Override
        public Blob createFrom(BlobInfo blobInfo, Path path, BlobWriteOption... blobWriteOptions) throws IOException {
          return null;
        }

        @Override
        public Blob createFrom(BlobInfo blobInfo, Path path, int i, BlobWriteOption... blobWriteOptions) throws IOException {
          return null;
        }

        @Override
        public Blob createFrom(BlobInfo blobInfo, InputStream inputStream, BlobWriteOption... blobWriteOptions) throws IOException {
          return null;
        }

        @Override
        public Blob createFrom(BlobInfo blobInfo, InputStream inputStream, int i, BlobWriteOption... blobWriteOptions) throws IOException {
          return null;
        }

        @Override
        public Bucket get(String s, BucketGetOption... bucketGetOptions) {
          return null;
        }

        @Override
        public Bucket lockRetentionPolicy(BucketInfo bucketInfo, BucketTargetOption... bucketTargetOptions) {
          return null;
        }

        @Override
        public Blob get(String s, String s1, BlobGetOption... blobGetOptions) {
          return null;
        }

        @Override
        public Blob get(BlobId blobId, BlobGetOption... blobGetOptions) {
          return null;
        }

        @Override
        public Blob get(BlobId blobId) {
          return null;
        }

        @Override
        public Blob restore(BlobId blobId, BlobRestoreOption... blobRestoreOptions) {
          return null;
        }

        @Override
        public Page<Bucket> list(BucketListOption... bucketListOptions) {
          return null;
        }

        @Override
        public Page<Blob> list(String s, BlobListOption... blobListOptions) {
          return null;
        }

        @Override
        public Bucket update(BucketInfo bucketInfo, BucketTargetOption... bucketTargetOptions) {
          return null;
        }

        @Override
        public Blob update(BlobInfo blobInfo, BlobTargetOption... blobTargetOptions) {
          return null;
        }

        @Override
        public Blob update(BlobInfo blobInfo) {
          return null;
        }

        @Override
        public boolean delete(String s, BucketSourceOption... bucketSourceOptions) {
          return false;
        }

        @Override
        public boolean delete(String s, String s1, BlobSourceOption... blobSourceOptions) {
          return false;
        }

        @Override
        public boolean delete(BlobId blobId, BlobSourceOption... blobSourceOptions) {
          return false;
        }

        @Override
        public boolean delete(BlobId blobId) {
          return false;
        }

        @Override
        public Blob compose(ComposeRequest composeRequest) {
          return null;
        }

        @Override
        public CopyWriter copy(CopyRequest copyRequest) {
          return null;
        }

        @Override
        public byte[] readAllBytes(String s, String s1, BlobSourceOption... blobSourceOptions) {
          return new byte[0];
        }

        @Override
        public byte[] readAllBytes(BlobId blobId, BlobSourceOption... blobSourceOptions) {
          return new byte[0];
        }

        @Override
        public StorageBatch batch() {
          return null;
        }

        @Override
        public ReadChannel reader(String s, String s1, BlobSourceOption... blobSourceOptions) {
          return null;
        }

        @Override
        public ReadChannel reader(BlobId blobId, BlobSourceOption... blobSourceOptions) {
          return null;
        }

        @Override
        public void downloadTo(BlobId blobId, Path path, BlobSourceOption... blobSourceOptions) {

        }

        @Override
        public void downloadTo(BlobId blobId, OutputStream outputStream, BlobSourceOption... blobSourceOptions) {

        }

        @Override
        public WriteChannel writer(BlobInfo blobInfo, BlobWriteOption... blobWriteOptions) {
          return null;
        }

        @Override
        public WriteChannel writer(URL url) {
          return null;
        }

        @Override
        public URL signUrl(BlobInfo blobInfo, long l, TimeUnit timeUnit, SignUrlOption... signUrlOptions) {
          return null;
        }

        @Override
        public PostPolicyV4 generateSignedPostPolicyV4(BlobInfo blobInfo, long l, TimeUnit timeUnit, PostPolicyV4.PostFieldsV4 postFieldsV4, PostPolicyV4.PostConditionsV4 postConditionsV4, PostPolicyV4Option... postPolicyV4Options) {
          return null;
        }

        @Override
        public PostPolicyV4 generateSignedPostPolicyV4(BlobInfo blobInfo, long l, TimeUnit timeUnit, PostPolicyV4.PostFieldsV4 postFieldsV4, PostPolicyV4Option... postPolicyV4Options) {
          return null;
        }

        @Override
        public PostPolicyV4 generateSignedPostPolicyV4(BlobInfo blobInfo, long l, TimeUnit timeUnit, PostPolicyV4.PostConditionsV4 postConditionsV4, PostPolicyV4Option... postPolicyV4Options) {
          return null;
        }

        @Override
        public PostPolicyV4 generateSignedPostPolicyV4(BlobInfo blobInfo, long l, TimeUnit timeUnit, PostPolicyV4Option... postPolicyV4Options) {
          return null;
        }

        @Override
        public List<Blob> get(BlobId... blobIds) {
          return List.of();
        }

        @Override
        public List<Blob> get(Iterable<BlobId> iterable) {
          return List.of();
        }

        @Override
        public List<Blob> update(BlobInfo... blobInfos) {
          return List.of();
        }

        @Override
        public List<Blob> update(Iterable<BlobInfo> iterable) {
          return List.of();
        }

        @Override
        public List<Boolean> delete(BlobId... blobIds) {
          return List.of();
        }

        @Override
        public List<Boolean> delete(Iterable<BlobId> iterable) {
          return List.of();
        }

        @Override
        public Acl getAcl(String s, Acl.Entity entity, BucketSourceOption... bucketSourceOptions) {
          return null;
        }

        @Override
        public Acl getAcl(String s, Acl.Entity entity) {
          return null;
        }

        @Override
        public boolean deleteAcl(String s, Acl.Entity entity, BucketSourceOption... bucketSourceOptions) {
          return false;
        }

        @Override
        public boolean deleteAcl(String s, Acl.Entity entity) {
          return false;
        }

        @Override
        public Acl createAcl(String s, Acl acl, BucketSourceOption... bucketSourceOptions) {
          return null;
        }

        @Override
        public Acl createAcl(String s, Acl acl) {
          return null;
        }

        @Override
        public Acl updateAcl(String s, Acl acl, BucketSourceOption... bucketSourceOptions) {
          return null;
        }

        @Override
        public Acl updateAcl(String s, Acl acl) {
          return null;
        }

        @Override
        public List<Acl> listAcls(String s, BucketSourceOption... bucketSourceOptions) {
          return List.of();
        }

        @Override
        public List<Acl> listAcls(String s) {
          return List.of();
        }

        @Override
        public Acl getDefaultAcl(String s, Acl.Entity entity) {
          return null;
        }

        @Override
        public boolean deleteDefaultAcl(String s, Acl.Entity entity) {
          return false;
        }

        @Override
        public Acl createDefaultAcl(String s, Acl acl) {
          return null;
        }

        @Override
        public Acl updateDefaultAcl(String s, Acl acl) {
          return null;
        }

        @Override
        public List<Acl> listDefaultAcls(String s) {
          return List.of();
        }

        @Override
        public Acl getAcl(BlobId blobId, Acl.Entity entity) {
          return null;
        }

        @Override
        public boolean deleteAcl(BlobId blobId, Acl.Entity entity) {
          return false;
        }

        @Override
        public Acl createAcl(BlobId blobId, Acl acl) {
          return null;
        }

        @Override
        public Acl updateAcl(BlobId blobId, Acl acl) {
          return null;
        }

        @Override
        public List<Acl> listAcls(BlobId blobId) {
          return List.of();
        }

        @Override
        public HmacKey createHmacKey(ServiceAccount serviceAccount, CreateHmacKeyOption... createHmacKeyOptions) {
          return null;
        }

        @Override
        public Page<HmacKey.HmacKeyMetadata> listHmacKeys(ListHmacKeysOption... listHmacKeysOptions) {
          return null;
        }

        @Override
        public HmacKey.HmacKeyMetadata getHmacKey(String s, GetHmacKeyOption... getHmacKeyOptions) {
          return null;
        }

        @Override
        public void deleteHmacKey(HmacKey.HmacKeyMetadata hmacKeyMetadata, DeleteHmacKeyOption... deleteHmacKeyOptions) {

        }

        @Override
        public HmacKey.HmacKeyMetadata updateHmacKeyState(HmacKey.HmacKeyMetadata hmacKeyMetadata, HmacKey.HmacKeyState hmacKeyState, UpdateHmacKeyOption... updateHmacKeyOptions) {
          return null;
        }

        @Override
        public Policy getIamPolicy(String s, BucketSourceOption... bucketSourceOptions) {
          return null;
        }

        @Override
        public Policy setIamPolicy(String s, Policy policy, BucketSourceOption... bucketSourceOptions) {
          return null;
        }

        @Override
        public List<Boolean> testIamPermissions(String s, List<String> list, BucketSourceOption... bucketSourceOptions) {
          return List.of();
        }

        @Override
        public ServiceAccount getServiceAccount(String s) {
          return null;
        }

        @Override
        public Notification createNotification(String s, NotificationInfo notificationInfo) {
          return null;
        }

        @Override
        public Notification getNotification(String s, String s1) {
          return null;
        }

        @Override
        public List<Notification> listNotifications(String s) {
          return List.of();
        }

        @Override
        public boolean deleteNotification(String s, String s1) {
          return false;
        }

        @Override
        public StorageOptions getOptions() {
          return null;
        }
      };
    }

  }
}
