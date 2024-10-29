


# Hệ thống giám sát hoạt động máy trạm
---

## Thành viên
|  STT  | Họ và tên             | Mã sinh viên |
| :---: | --------------------- | ------------ |
|   1   | Nguyễn Hoàng Nam      | B21DCCN095   |
|   2   | Trần Hoàng Tống Giang | B21DCCN040   |
|   3   | Nguyễn Văn Hiếu       | B21DCCN366   |

## Mục lục
- [Giới thiệu](#giới-thiệu)
- [Tính năng](#tính-năng)
- [Công nghệ sử dụng](#công-nghệ-sử-dụng)
- [Hướng dẫn cài đặt](#hướng-dẫn-cài-đặt)
- [Cách sử dụng](#cách-sử-dụng)
- [Các cải tiến trong tương lai](#các-cải-tiến-trong-tương-lai)

---
## Giới thiệu
Dự án này là một **Hệ thống giám sát hoạt động ** được phát triển bằng **Java**. Hệ thống được thiết kế để giám sát cách hoạt động của máy trạm trong thời gian thực. Nó cung cấp các thông tin quan trọng về những hoạt động của máy trạm bao gồm : thời gian sử dụng , cửa sổ đang mở , thao tác trên cửa số đó cùng các giao thức được sử dụng. Điều này giúp các quản trị viên mạng giám sát được các hoạt động trên máy trạm và nhận diện các vấn đề hoặc bất thường có thể xảy ra. Dự án này được phục vụ trong các kì thi quan trọng trên máy tính để máy chủ có thể giám sát được các máy trạm đang có những hoạt động gì .

## Tính năng
- Gíam sát hoạt động trên máy khách
- Theo dõi từng hoạt động và thời gian sử dung
- Ping địa chỉ IP, domain
- Kiểm tra port

## Công nghệ sử dụng
- **Ngôn ngữ chính:** Java
- **Thư viện dùng thêm:** JNA ,JNA PLATFORM
- **Giao diện đồ họa:** Swing (hoặc JavaFX nếu cần thiết)
- **Thao tác mạng:** Sockets API, InetAddress (Ping), TCP/UDP port scanning
- **Xử lý đa luồng:** Sử dụng multithreading nếu cần thực hiện nhiều thao tác đồng thời
- **Quản lý dự án và dependency:** Maven hoặc Gradle (nếu cần)
- **Kiểm thử**: JUnit (nếu cần kiểm thử tự động)

## Hướng dẫn cài đặt
- Hướng dẫn cài đặt thư viên JNA:
  + Tìm kiếm jna-platform-5.14.0.jar(phiên bản mới nhất) từ Maven Repository.
  + Copy đường dẫn maven
  + Vào đường dẫn https://jar-download.com
  + Chọn maven online tool
  + Paste đường dẫn maven vào và submit để tải xuống
  + Trong NetBeans, nhấp chuột phải vào Libraries của dự án, chọn Add JAR/Folder..., sau đó chọn cả file jna-5.14.0.jar.
  + Nhấn Open để thêm chúng vào dự án.
- Hướng dẫn cài đặt thư viên JNA-platform-5.14.0.jar ( Tương tự các bước như JNA)
## Cách sử dụng
- Khởi chạy hệ thống
- Chạy Server để lấy IP và Port
- Chạy Client , nhập đúng IP và Port của Server để kết nối
- Sau khi giao diện hiển thị đã kết nối thành công , client sẽ giám sát các hoạt động và gửi thông báo về cho server

## Các cải tiến trong tương lai
- Tối ưu hóa Hiệu suất và Sử dụng Tài nguyên: Vì chương trình đang giám sát liên tục, cần có các tối ưu hóa để giảm thiểu việc sử dụng CPU và bộ nhớ trên client. Điều này có thể thực hiện bằng cách điều chỉnh tần suất giám sát hoặc sử dụng các kỹ thuật xử lý sự kiện thay vì kiểm tra liên tục.
- Cải thiện Bảo mật và Mã hóa Dữ liệu: Để đảm bảo tính bảo mật, có thể thêm tính năng mã hóa dữ liệu truyền giữa client và server nhằm bảo vệ thông tin nhạy cảm. Việc sử dụng các giao thức an toàn như TLS/SSL có thể là một lựa chọn.
- Giao diện Quản lý Trực quan trên Server: Xây dựng giao diện quản lý trực quan cho server (web hoặc ứng dụng desktop) giúp người dùng có thể dễ dàng theo dõi hoạt động từ nhiều client cùng lúc, sắp xếp, lọc, và lưu trữ lịch sử giám sát.
- Hỗ trợ Đa Nền Tảng: Hiện tại, dự án chủ yếu hỗ trợ hệ điều hành Windows. Trong tương lai, có thể mở rộng để hỗ trợ các hệ điều hành khác như macOS và Linux nhằm tăng tính ứng dụng đa nền tảng.
- Phát triển Hệ thống Cảnh báo: Thêm hệ thống cảnh báo tự động khi phát hiện các hoạt động bất thường hoặc trái phép. Server có thể gửi thông báo qua email hoặc các ứng dụng nhắn tin khi có hoạt động nghi vấn.
- Tích hợp Lưu Trữ và Phân Tích Dữ Liệu: Triển khai hệ thống lưu trữ dữ liệu trên cơ sở dữ liệu hoặc dịch vụ đám mây, từ đó có thể phân tích dữ liệu giám sát để cung cấp thông tin chi tiết, báo cáo và đánh giá về hành vi người dùng.









 

