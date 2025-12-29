#!/bin/bash

# 启动前端服务
cd "$(dirname "$0")/../frontend"

# 检查是否安装依赖
if [ ! -d "node_modules" ]; then
    echo "正在安装前端依赖..."
    npm install
fi

# 同时启动 web 和 admin
echo "启动前台 (端口 3000) 和后台 (端口 3001)..."
npm run dev:web &
npm run dev:admin &

wait
