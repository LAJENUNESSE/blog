#!/bin/bash

# 0. 定义路径
PROJECT_ROOT=$(pwd)
BACKEND_STATIC="$PROJECT_ROOT/backend/src/main/resources/static"

echo "=========================================="
echo "       🚀 开始构建单体极简包 (Lite)"
echo "=========================================="

# 1. 准备后端静态资源目录
echo "[1/4] 🧹 清理后端静态资源目录..."
# 如果目录不存在则创建
mkdir -p "$BACKEND_STATIC"
# 清空目录 (保留目录本身)
rm -rf "$BACKEND_STATIC"/*

# 2. 构建前端 (只构建 Web 前台，Admin 暂时先不管，为了先跑通流程)
echo "[2/4] 📦 正在编译前端 (Web)..."
cd frontend
# 这里的 --filter @blog/web 意思是只编译 web 项目，不编译 admin
pnpm --filter @blog/web build 

if [ $? -ne 0 ]; then
    echo "❌ 前端编译失败！"
    exit 1
fi

# 3. 搬运资源
echo "[3/4] 🚚 正在将前端产物注入后端..."
# 将 dist 下的所有文件复制到 spring boot 的 static 目录
cp -r packages/web/dist/* "$BACKEND_STATIC"

echo "✅ 前端资源注入完成！"

# 4. 构建后端 JAR
echo "[4/4] ☕ 正在构建后端 JAR 包..."
cd ../backend
# 跳过测试以加速构建 (-DskipTests)
./mvnw clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ 后端构建失败！"
    exit 1
fi

echo ""
echo "🎉🎉🎉 构建成功！ 🎉🎉🎉"
echo "你的最终成品在这里: backend/target/blog-0.0.1-SNAPSHOT.jar"
echo "=========================================="
