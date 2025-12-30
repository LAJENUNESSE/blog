#!/bin/bash

# 启动所有服务
SCRIPT_DIR="$(dirname "$0")"

echo "=========================================="
echo "         启动 Blog 全栈应用"
echo "=========================================="
echo ""
echo "后端: http://localhost:8080"
echo "前台: http://localhost:3000"
echo "后台: http://localhost:3001/admin"
echo ""
echo "=========================================="

# 启动后端 (后台运行)
echo "[1/2] 启动后端服务..."
cd "$SCRIPT_DIR/../backend"
./mvnw spring-boot:run &
BACKEND_PID=$!

# 等待后端启动
sleep 5

# 启动前端
echo "[2/2] 启动前端服务..."
cd "$SCRIPT_DIR/../frontend"

if [ ! -d "node_modules" ]; then
    echo "正在安装前端依赖..."
    pnpm install
fi

npm run dev:web &
npm run dev:admin &

echo ""
echo "所有服务已启动！按 Ctrl+C 停止所有服务。"

# 等待所有后台进程
wait
