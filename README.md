


# Hệ thống giám sát hoạt động (Activity Monitoring)
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
- Ping địa chỉ IP, domain
- Kiểm tra port

## Công nghệ sử dụng
- **Ngôn ngữ chính:** Java
- **Giao diện đồ họa:** Swing (hoặc JavaFX nếu cần thiết)
- **Thao tác mạng:** Sockets API, InetAddress (Ping), TCP/UDP port scanning
- **Xử lý đa luồng:** Sử dụng multithreading nếu cần thực hiện nhiều thao tác đồng thời
- **Quản lý dự án và dependency:** Maven hoặc Gradle (nếu cần)
- **Kiểm thử**: JUnit (nếu cần kiểm thử tự động)

## Hướng dẫn cài đặt


## Cách sử dụng
- Khởi chạy hệ thống và chọn giao diện mạng cần giám sát.
- Xem lưu lượng mạng trực tiếp, có thể lọc theo giao thức hoặc địa chỉ IP.
- Tạo báo cáo về tổng dữ liệu truyền tải, các kết nối đang hoạt động, và nhiều hơn nữa.

## Các cải tiến trong tương lai
- Thu thập lưu lượng mạng theo thời gian thực.
- Phân tích các gói tin mạng bao gồm TCP, UDP và ICMP.
- Lọc lưu lượng dựa trên địa chỉ IP nguồn/đích hoặc giao thức.
- Ghi lại và báo cáo thống kê mạng.
- Cảnh báo tùy chỉnh cho các mẫu lưu lượng hoặc ngưỡng nhất định.


 

