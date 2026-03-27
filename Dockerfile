# Sử dụng OpenJDK image nhẹ (Alpine)
FROM eclipse-temurin:17-jdk-alpine

# Tạo thư mục làm việc
WORKDIR /app

# Copy toàn bộ source code vào image
COPY src ./src

# Biên dịch code Java (bao gồm cả Constant, SocketClient, SocketServer) vào thư mục ./out
RUN mkdir -p out && javac -d out src/main/java/com/example/demo/Util/*.java src/main/java/com/example/demo/socket/*.java