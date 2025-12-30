#!/bin/bash

# 0. 定义路径
PROJECT_ROOT=$(pwd)
BACKEND_STATIC="$PROJECT_ROOT/backend/src/main/resources/static"

echo "=========================================="
echo "       🚀 开始构建单体极简包 (Lite)"
echo "=========================================="

# 1. 准备后端静态资源目录
echo "[1/5] 🧹 清理后端静态资源目录..."
mkdir -p "$BACKEND_STATIC"
rm -rf "$BACKEND_STATIC"/*

# 2. 构建前端 Web
echo "[2/5] 📦 正在编译前端 (Web)..."
cd frontend
pnpm --filter @blog/web build

if [ $? -ne 0 ]; then
    echo "❌ Web 前端编译失败！"
    exit 1
fi

# 3. 构建前端 Admin
echo "[3/5] 📦 正在编译前端 (Admin)..."
pnpm --filter @blog/admin build

if [ $? -ne 0 ]; then
    echo "❌ Admin 前端编译失败！"
    exit 1
fi

# 4. 搬运资源
echo "[4/5] 🚚 正在将前端产物注入后端..."
# Web 放在根目录
cp -r packages/web/dist/* "$BACKEND_STATIC"
# Admin 放在 /admin 子目录
mkdir -p "$BACKEND_STATIC/admin"
cp -r packages/admin/dist/* "$BACKEND_STATIC/admin"

echo "✅ 前端资源注入完成！"

# 5. 构建后端 JAR
echo "[5/5] ☕ 正在构建后端 JAR 包..."
cd ../backend
./mvnw clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ 后端构建失败！"
    exit 1
fi

echo ""
echo "🎉🎉🎉 构建成功！ 🎉🎉🎉"
echo "你的最终成品在这里: backend/target/blog-0.0.1-SNAPSHOT.jar"
echo ""
echo "访问地址："
echo "  前台: http://localhost:8080/"
echo "  后台: http://localhost:8080/admin/"
echo "=========================================="
