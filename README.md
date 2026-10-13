# Lab 3a — Danh Sách Động Tối Ưu Với RecyclerView

## 1. Thông Tin Sinh Viên & Bài Lab
- **Môn học:** Phát triển Ứng dụng Di động (Mobile App) tích hợp AI
- **Buổi học:** Buổi 03 — Danh sách động tối ưu với RecyclerView
- **Bài thực hành:** Lab 3a — Ứng dụng hiển thị danh sách thẻ động
- **Họ và tên sinh viên:** Vũ Nguyễn Đức Trí
- **Mã số sinh viên (MSSV):** 2500115657
- **Đơn vị đào tạo:** Trường Cao đẳng Văn Lang Sài Gòn (VLSC)

---

## 2. Giới Thiệu Ứng Dụng
Ứng dụng hiển thị danh sách sinh viên gồm 10 thẻ bo góc theo chuẩn giao diện Material Design 3 (`MaterialCardView`). 
- Khi người dùng chạm vào một thẻ sinh viên bất kỳ, ứng dụng hiển thị thông báo `Toast` kèm tên và MSSV của sinh viên đó.
- Danh sách được quản lý và cập nhật tối ưu bằng cơ chế **`ListAdapter` kết hợp `DiffUtil`**.
- Hỗ trợ giao diện tràn màn hình chuẩn hiện đại (**Edge-to-Edge**) xử lý `WindowInsets` tránh bị che bởi thanh trạng thái (Status Bar) hay thanh điều hướng (Navigation Bar).
- Tương thích tốt với cả hai chế độ **Sáng (Light Mode)** và **Tối (Dark Mode)** nhờ việc sử dụng triệt để các Theme Attributes (`?attr/...`).

---

## 3. Kiến Trúc & Cấu Trúc Mã Nguồn

```
app/src/main/
├── java/vn/edu/vlsc/lab03a_vunguyenductri_2500115657/
│   ├── model/
│   │   └── Student.kt                   # Data class biểu diễn sinh viên (id, name, mssv, avatarRes)
│   ├── adapter/
│   │   └── StudentAdapter.kt            # ListAdapter + ViewHolder + StudentDiffCallback
│   └── MainActivity.kt                  # Activity chính: ViewBinding, Edge-to-edge, LayoutManager & Toast
└── res/
    ├── layout/
    │   ├── activity_main.xml            # Giao diện chính chứa TextView và RecyclerView
    │   └── item_student.xml             # Giao diện từng thẻ sinh viên (MaterialCardView)
    └── values/
        └── strings.xml                  # Quản lý chuỗi không hardcode, hỗ trợ đa ngôn ngữ
```

### Điểm nổi bật về kỹ thuật:
1. **View Recycling & ViewHolder:** Tái sử dụng view khi cuộn danh sách, lưu trữ `binding` con giúp tránh gọi lại `findViewById()` tốn tài nguyên. Gắn `setOnClickListener` bên trong `init` của `ViewHolder` kết hợp kiểm tra `bindingAdapterPosition != RecyclerView.NO_POSITION`.
2. **ListAdapter + DiffUtil:** Thực hiện tính toán so sánh danh sách cũ và mới ở luồng nền (Background thread), tự động kích hoạt animation thêm/xóa/đổi vị trí mượt mà, hạn chế vẽ lại toàn bộ danh sách như `notifyDataSetChanged()`.
3. **Phân tách trách nhiệm (Separation of Concerns):** Callback sự kiện click item `(Student) -> Unit` được truyền từ `MainActivity` vào `StudentAdapter`, giúp Adapter chỉ đảm nhận nhiệm vụ hiển thị, còn xử lý logic thuộc về Activity/ViewModel.
4. **Chuẩn mã nguồn sạch:** Tên biến/hàm theo camelCase, class theo PascalCase; toàn bộ chuỗi hiển thị được đặt trong `strings.xml`.

---

## 4. Hướng Dẫn Chạy Ứng Dụng

1. Mở dự án bằng **Android Studio Ladybug / Meerkat / Quail** hoặc mới hơn.
2. Đợi quá trình Gradle Sync hoàn tất (dự án sử dụng AGP và Gradle Kotlin DSL chuẩn).
3. Kết nối thiết bị Android thật (đã bật USB Debugging) hoặc khởi chạy máy ảo Android (AVD) chạy Android 7.0 (API 24) trở lên.
4. Nhấn nút **Run 'app'** (`Shift + F10`) trên thanh công cụ để cài đặt và khởi chạy.

---

## 5. Sử Dụng AI Có Trách Nhiệm (AI Transparency)

Theo chuẩn đạo đức và quy định môn học (Mục 5.1 trong đề bài):
- **Công cụ AI sử dụng:** Antigravity AI Assistant (dựa trên mô hình Google DeepMind).
- **Phạm vi hỗ trợ:**
  - Hỗ trợ rà soát cấu hình Version Catalog (`libs.versions.toml`) và Gradle KTS để tích hợp `androidx.recyclerview:recyclerview:1.4.0` cùng tính năng `viewBinding = true`.
  - Hỗ trợ kiểm tra cú pháp Kotlin, xử lý `WindowInsets` đúng chuẩn cho targetSdk 35+ và tối ưu hóa xử lý sự kiện trong ViewHolder.
- **Các prompt chính đã sử dụng:**
  1. *"Kiểm tra và cấu hình dependencies RecyclerView và ViewBinding trong build.gradle.kts theo đúng chuẩn template hiện hành của Android Studio."*
  2. *"Viết StudentAdapter kế thừa ListAdapter với DiffUtil.ItemCallback và gán sự kiện click bằng bindingAdapterPosition chuẩn Material 3."*
- **Cam kết:** Sinh viên tự hiểu rõ cơ chế hoạt động của từng dòng code, cơ chế View Recycling, `DiffUtil`, luồng dữ liệu và sẵn sàng giải thích chi tiết khi được giảng viên vấn đáp.

---

## 6. Hướng Dẫn Các Lệnh Git Nộp Bài (Terminal)

```bash
# 1. Khởi tạo repository
git init

# 2. Kiểm tra các file (đảm bảo local.properties, build/ không bị đưa vào git)
git status

# 3. Thêm file và commit
git add .
git commit -m "Lab 3a: RecyclerView with ListAdapter and DiffUtil"

# 4. Đặt tên nhánh chính
git branch -M main

# 5. Liên kết repository trên GitHub (thay <username> bằng tài khoản cá nhân)
git remote add origin https://github.com/<username>/lab03a_VuNguyenDucTri_2500115657.git

# 6. Đẩy mã nguồn lên GitHub
git push -u origin main
```
