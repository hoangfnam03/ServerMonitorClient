


# Hệ thống giám sát mạng (Network Monitoring)
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
Dự án này là một **Hệ thống giám sát lưu lượng mạng** được phát triển bằng **Java**. Hệ thống được thiết kế để giám sát, thu thập và phân tích lưu lượng mạng trong thời gian thực. Nó cung cấp các thông tin quan trọng về lưu lượng, như kích thước gói tin, địa chỉ nguồn và đích, cùng các giao thức được sử dụng. Điều này giúp các quản trị viên mạng hiểu rõ hơn về cách sử dụng mạng và nhận diện các vấn đề hoặc bất thường có thể xảy ra.

## Tính năng
- Thu thập lưu lượng mạng theo thời gian thực.
- Phân tích các gói tin mạng bao gồm TCP, UDP và ICMP.
- Lọc lưu lượng dựa trên địa chỉ IP nguồn/đích hoặc giao thức.
- Ghi lại và báo cáo thống kê mạng.
- Cảnh báo tùy chỉnh cho các mẫu lưu lượng hoặc ngưỡng nhất định.

## Công nghệ sử dụng
- **Java**: Ngôn ngữ lập trình chính.
- **pcap4j**: Thư viện Java dùng để thu thập và phân tích gói tin mạng.
- **Maven**: Công cụ quản lý dự án và phụ thuộc.
- **JUnit**: Công cụ dùng cho kiểm thử đơn vị.

## Hướng dẫn cài đặt


## Cách sử dụng
- Khởi chạy hệ thống và chọn giao diện mạng cần giám sát.
- Xem lưu lượng mạng trực tiếp, có thể lọc theo giao thức hoặc địa chỉ IP.
- Tạo báo cáo về tổng dữ liệu truyền tải, các kết nối đang hoạt động, và nhiều hơn nữa.

## Các cải tiến trong tương lai
- Tích hợp với cơ sở dữ liệu để lưu trữ và phân tích dữ liệu lâu dài.
- Giao diện web để dễ dàng truy cập và quản lý.
- Các tùy chọn lọc nâng cao (ví dụ: theo kích thước gói tin hoặc các ứng dụng cụ thể).


 

