<p align="center">
  <img src="https://github.com/user-attachments/assets/f4437b4c-f7ea-47f0-8ee2-605127b27aa4" alt="himamobanner" width="600">
</p>

<p align="center">
  <img src="https://img.shields.io/badge/status-completed-brightgreen"> <a href="./LICENSE"><img src="https://img.shields.io/badge/license-MIT-blue"></a>
</p>

# Point of Sale

This project is created for the **Mobile Computing 2** course. It includes learning materials about **Kotlin in Android Studio** and **Express JS**, focusing on fundamental concepts such as **CRUD API**, **authentication**, and **role-based access control**.

## **Technologies Used**
- **Android Studio**: For developing and structuring Android applications.
- **Kotlin**: The primary programming language used for building Android apps.
- **Express JS**: For building the backend API.
- **MySQL**: For the database layer.
- **Retrofit2**: For consuming REST APIs from Android.
- **Glide**: For image loading.
- **JWT (JSON Web Token)**: For authentication and authorization.
- **MVVM Architecture**: For Android app design pattern.
- **SharedPreferences**: For storing session information in Android.

## **Demo**

### **Splash Screen**
<img src="https://github.com/user-attachments/assets/36814dce-be8f-4f53-a4c3-720863b1e6e5" alt="Splash Screen" style="height: 300px; width: auto;">

### **Login**
<img src="https://github.com/user-attachments/assets/75871522-f191-4e28-acf2-6119342acaca" alt="Login" style="height: 300px; width: auto;">

### **Dashboard Admin**
<img src="https://github.com/user-attachments/assets/b32db18d-e599-42d9-8ee1-160484e3182b" alt="Dashboard Admin" style="height: 300px; width: auto;">

### **Dashboard Gudang**
<img src="https://github.com/user-attachments/assets/1341dc84-aa5d-4b64-88c8-113b666bcdcb" alt="Dashboard Gudang" style="height: 300px; width: auto;">

### **Dashboard Cashier**
<img src="https://github.com/user-attachments/assets/325f0f94-60ef-4ac6-a388-921aa25b53a0" alt="Dashboard Kasir" style="height: 300px; width: auto;">

### **User**
<img src="https://github.com/user-attachments/assets/098ab265-e762-4b60-a254-495bbfe596d0" alt="User 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/027599d9-48dc-4855-aaf7-5c0df9a674a7" alt="User 2" style="height: 300px; width: auto;">

### **Category**
<img src="https://github.com/user-attachments/assets/5f61d64a-d991-4b93-b822-a1f67a6473dc" alt="Category 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/daaaabea-271a-4e8b-81bf-48432f9a231c" alt="Category 2" style="height: 300px; width: auto;">

### **Product**
<img src="https://github.com/user-attachments/assets/ee0ea97c-5099-48b7-a654-1195622352ef" alt="Product 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/eca4979a-962d-4ee8-8b6b-c997fe4b7a05" alt="Product 2" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/8d189b19-84ab-4a43-910a-4adab3c8984d" alt="Product 3" style="height: 300px; width: auto;">

### **Customer**
<img src="https://github.com/user-attachments/assets/c0d50545-f7a9-40ae-86e6-ce4445f4df8c" alt="Customer 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/4dbfef3c-1685-4032-98a3-7f4226fa544a" alt="Customer 2" style="height: 300px; width: auto;">

### **Supplier**
<img src="https://github.com/user-attachments/assets/e69bdf83-afdd-427b-848c-8216c9a6ab71" alt="Supplier 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/2330716f-9adc-44da-af31-cc098037434b" alt="Supplier 2" style="height: 300px; width: auto;">

### **Sale**
<img src="https://github.com/user-attachments/assets/8ff8d8a0-219d-4225-b04c-240ee5603a54" alt="Sale 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/edf860c0-df32-4d14-aedf-036567f9f06a" alt="Sale 2" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/1d2375a8-e985-4aed-8f63-e20f9096eb68" alt="Sale 3" style="height: 300px; width: auto;">

### **Purchase**
<img src="https://github.com/user-attachments/assets/8ff8d8a0-219d-4225-b04c-240ee5603a54" alt="Purchase 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/edf860c0-df32-4d14-aedf-036567f9f06a" alt="Purchase 2" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/1d2375a8-e985-4aed-8f63-e20f9096eb68" alt="Purchase 3" style="height: 300px; width: auto;">

### **Report by Product**
<img src="https://github.com/user-attachments/assets/95e8b469-3336-49d8-bc47-c83278daedd8" alt="Report Product" style="height: 300px; width: auto;">

### **Report by Month**
<img src="https://github.com/user-attachments/assets/2574d443-127c-40f1-8493-2d6d28a44b64" alt="Report Month" style="height: 300px; width: auto;">

### **Logout**
<img src="https://github.com/user-attachments/assets/42707d97-a82f-4e93-b203-c46884e886cb" alt="Logout" style="height: 300px; width: auto;">

### Backend Setup (Express.js)
1. Clone the repository and navigate to the backend folder.
2. Create a file named `.env` in the root directory of the backend project with the following content:

   ```env
   PORT=3000
   DB_HOST=localhost
   DB_USER=root
   DB_PASSWORD=
   DB_NAME=pos_db
   JWT_SECRET=rahasia_super_aman
   JWT_EXPIRES_IN=1d
  Adjust the database credentials based on your local MySQL configuration.

3. Install the required backend dependencies: `npm install`

4. Start the backend server: `npm run start`

  The backend server will run at http://localhost:3000/api/. If accessing from an Android emulator or physical device, replace localhost with your local IP address.

### Android App Setup
1. Open the Android project folder in Android Studio.
2. Open the file ApiConfig.kt, then update the base URL to match your backend server address:
   ```
   private const val BASE_URL = "http://172.16.7.133:3000/api/"
  Replace `172.16.7.133` with your actual IP address. If you are using an Android emulator, use `10.0.2.2` instead of localhost.
3. Make sure all required SDKs and Gradle dependencies are installed.
4. Build and run the project on an emulator or a physical Android device.
5. APK files are also provided in the repository for direct installation if needed.

## **Usage**
1. Run backend server with `npm run start`.
2. Open the Android application and log in with available roles (admin, gudang, kasir).
3. Explore all modules such as User, Product, Sale, Purchase, and Reports.

## **Project Status**
This project is **completed** and was developed for educational purposes as part of the **Mobile Computing (Komputasi Bergerak)** course.

## **Contributions**
Contributions are welcome! Feel free to submit issues or create pull requests to improve the project.
