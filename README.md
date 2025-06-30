![Sale Detail](https://github.com/user-attachments/assets/7db72430-502e-4c3d-877d-6f8444d335ab)# Point of Sale

![Project Status](https://img.shields.io/badge/status-completed-brightgreen)

This project is created for the **Mobile Computing 2** course. It includes learning materials about **Kotlin in Android Studio**, Express JS, focusing on fundamental concepts such as CRUD Api.

## **Technologies Used**
- **Android Studio**: For developing and structuring Android applications.
- **Kotlin**: The primary programming language used for building Android apps.
Express Js

## **Demo**

### **Splash Screen**
This project

<img src="https://github.com/user-attachments/assets/36814dce-be8f-4f53-a4c3-720863b1e6e5" alt="Screenshot 1" style="height: 300px; width: auto;">

### **Login**
This project

<img src="https://github.com/user-attachments/assets/75871522-f191-4e28-acf2-6119342acaca" alt="Screenshot 1" style="height: 300px; width: auto;">

### **Dashboard Admin**
This project

<img src="https://github.com/user-attachments/assets/b32db18d-e599-42d9-8ee1-160484e3182b" alt="Screenshot 1" style="height: 300px; width: auto;">

### **Dashboard Gudang**
This project

<img src="https://github.com/user-attachments/assets/1341dc84-aa5d-4b64-88c8-113b666bcdcb" alt="Screenshot 1" style="height: 300px; width: auto;">

### **Dashboard Cashier**
This project

<img src="https://github.com/user-attachments/assets/325f0f94-60ef-4ac6-a388-921aa25b53a0" alt="Screenshot 1" style="height: 300px; width: auto;">

### **User**
This project

<img src="https://github.com/user-attachments/assets/098ab265-e762-4b60-a254-495bbfe596d0" alt="Screenshot 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/027599d9-48dc-4855-aaf7-5c0df9a674a7" alt="Screenshot 1" style="height: 300px; width: auto;">

### **Category**
This project

<img src="https://github.com/user-attachments/assets/5f61d64a-d991-4b93-b822-a1f67a6473dc" alt="Screenshot 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/daaaabea-271a-4e8b-81bf-48432f9a231c" alt="Screenshot 1" style="height: 300px; width: auto;">

### **Product**
This project

<img src="https://github.com/user-attachments/assets/ee0ea97c-5099-48b7-a654-1195622352ef" alt="Screenshot 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/eca4979a-962d-4ee8-8b6b-c997fe4b7a05" alt="Screenshot 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/8d189b19-84ab-4a43-910a-4adab3c8984d" alt="Screenshot 1" style="height: 300px; width: auto;">

### **Customer**
This project

<img src="https://github.com/user-attachments/assets/c0d50545-f7a9-40ae-86e6-ce4445f4df8c" alt="Screenshot 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/4dbfef3c-1685-4032-98a3-7f4226fa544a" alt="Screenshot 1" style="height: 300px; width: auto;">


### **Supplier**
This project

<img src="https://github.com/user-attachments/assets/e69bdf83-afdd-427b-848c-8216c9a6ab71" alt="Screenshot 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/2330716f-9adc-44da-af31-cc098037434b" alt="Screenshot 1" style="height: 300px; width: auto;">

### **Sale**
This project

<img src="https://github.com/user-attachments/assets/8ff8d8a0-219d-4225-b04c-240ee5603a54" alt="Screenshot 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/edf860c0-df32-4d14-aedf-036567f9f06a" alt="Screenshot 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/1d2375a8-e985-4aed-8f63-e20f9096eb68" alt="Screenshot 1" style="height: 300px; width: auto;">

### **Purchase**
This project

<img src="https://github.com/user-attachments/assets/8ff8d8a0-219d-4225-b04c-240ee5603a54" alt="Screenshot 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/edf860c0-df32-4d14-aedf-036567f9f06a" alt="Screenshot 1" style="height: 300px; width: auto;">
<img src="https://github.com/user-attachments/assets/1d2375a8-e985-4aed-8f63-e20f9096eb68" alt="Screenshot 1" style="height: 300px; width: auto;">

### **Report by Product**
This project

<img src="https://github.com/user-attachments/assets/95e8b469-3336-49d8-bc47-c83278daedd8" alt="Screenshot 1" style="height: 300px; width: auto;">

### **Report by Month**
This project

<img src="https://github.com/user-attachments/assets/2574d443-127c-40f1-8493-2d6d28a44b64" alt="Screenshot 1" style="height: 300px; width: auto;">

### **Logout**
This project

<img src="https://github.com/user-attachments/assets/42707d97-a82f-4e93-b203-c46884e886cb" alt="Screenshot 1" style="height: 300px; width: auto;">

## **Setup**

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

3. Install the required backend dependencies:
   ```
   npm install

4. Start the backend server:
   ```
   npm run start

  The backend server will run at http://localhost:3000/api/. If accessing from an Android emulator or physical device, replace localhost with your local IP address.

### Android App Setup
1. Open the Android project folder in Android Studio.
2. Open the file ApiConfig.kt, then update the base URL to match your backend server address:
   ```
   private const val BASE_URL = "http://172.16.7.133:3000/api/"
   
  Replace 172.16.7.133 with your actual IP address. If you are using an Android emulator, use 10.0.2.2 instead of localhost.
3. Make sure all required SDKs and Gradle dependencies are installed.
4. Build and run the project on an emulator or a physical Android device.
5. APK files are also provided in the repository for direct installation if needed.

## **Usage**
1. Start the backend with
   ```
   npm run start.
2. Open the Android app and log in with the appropriate role (admin, warehouse, cashier).
3. Explore modules such as User, Product, Sale, Purchase, and Reports to observe CRUD operations and role‑based access control.

## **Project Status**
This project is **completed** and was developed for educational purposes as part of the **Mobile Computing (Komputasi Bergerak)** course.


## **Contributions**
Contributions are welcome! Feel free to submit issues or create pull requests to improve the project.
