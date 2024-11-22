# Notes Content Provider & Resolver

This repository showcases an Android **ContentProvider** and a corresponding **ContentResolver** for managing and sharing notes data securely between applications.

---

## 📌 Overview

### Notes Content Provider
- Implements a `ContentProvider` using the Room database.
- Secures access via a custom-defined permission.
- Supports CRUD operations:
  - **Query**: Retrieve all or specific notes.
  - **Insert**: Add new notes.
  - **Update**: Modify existing notes.
  - **Delete**: Remove notes.

### Notes Content Resolver
- Demonstrates interaction with the `ContentProvider` to:
  - Query, insert, update, and delete notes.
  - Observe changes in the data via `ContentObserver`.

---

## ⚙️ Setup

### 1. Content Provider Setup
- Add the `NotesContentProvider` to your `AndroidManifest.xml`:
  ```xml
  <provider
      android:name=".data.provider.NotesContentProvider"
      android:authorities="com.degel.my_custom_content_provider.provider"
      android:exported="true"
      android:permission="com.degel.my_custom_content_provider.READ_NOTES"
      android:readPermission="com.degel.my_custom_content_provider.READ_NOTES" />
