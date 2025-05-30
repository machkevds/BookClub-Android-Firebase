# BookClub-Android-Firebase

An interactive Android app built with **Jetpack Compose**, **Firebase**, and the **Google Books API**, designed to provide a cozy digital reading space. Users can **search for books**, **add them to their personal reading lists**, and **engage with a community** through shared comment threads.

---

## App Features

-  **Google Sign-In** via Firebase Authentication.
-  **Search books** using the Google Books API.
-  **Personal Reading List**: Add and delete books unique to each user.
-  **Shared Comment Threads**: Comment with others on any book.
-  **Firebase Firestore Integration**: Handles personal reading lists and global comment storage.
-  **Modern UI**: Built entirely in Jetpack Compose with warm custom theming and animations.

---

## APIs Used

- **Google Books API**  
  Used to search and retrieve book details, authors, and thumbnails.

---

## Android & Jetpack Features

- **Jetpack Compose**  
  UI framework for building reactive, declarative UIs.
- **Navigation Compose**  
  Used for managing screen transitions.
- **Firebase Realtime SnapshotListener**  
  For real-time Firestore updates to reading lists and comments.

---

## Third-Party Services & Libraries

### Firebase Authentication
- **Use**: Google Sign-In
- **Pro**: Fast integration with Compose.
- **Challenge**: Logout didn't trigger account chooser UI due to Smart Lock.

### Firebase Firestore
- **Use**: Personal reading lists & global comment threads.
- **Pro**: Easy real-time syncing.
- **Challenge**: Needed custom security rules to split personal and shared data. Type mismatches (e.g., `authors`) caused data integrity issues.

###  Coil
- **Use**: Load book cover thumbnails from URLs.
- **Pro**: Clean Compose compatibility.
- **Challenge**: Occasional issues with loading in the Reading List screen due to caching.

### Material 3
- **Use**: UI components, styling, color theming.
- **Pro**: Clean UI components.
- **Challenge**: Customizing shadows and elevation styling was not as intuitive as expected.

---

## AI-Generated Components

Used **GitHub Copilot** for:
- Debugging Gradle and understanding Kotlin DSL.
- Designing UI color palettes.
- Generating log statements for comment/model debugging.
- Creating custom UI like the `FancyBackButton`.

---

## UX / Display Highlights

- Themed around a **warm, cozy** reading aesthetic.
- Personalized **"Welcome Back"** greeting using Google account info.
- Clean book cards for displaying title, authors, and action buttons.
- Navigation is smooth and responsive using Compose + ViewModels.

---

## Backend & Logic Architecture

- Three ViewModels:
  - `BookSearchViewModel`: Google Books API integration.
  - `ReadingListViewModel`: Firestore integration for user-specific lists.
  - `CommentViewModel`: Real-time comment stream per book.
- Data Model:
    Firestore
    - reading_list/{userId}/books/{bookId}
    - comments/{bookId}/entries/{commentId}

  ## Key Learnings & Challenges

- Gradle syncing and version management caused early roadblocks.
- Migrated from a failed fitness app idea to this polished book app.
- Overcame API rate limits and fallbacked to mock data when needed.
- Learned to debug API deserialization issues and Firebase console mismatches.
- Gained clarity on how to combine personalized vs shared Firebase structures.

