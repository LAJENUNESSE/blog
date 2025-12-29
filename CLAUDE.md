# CLAUDE.md

This file provides guidance to Claude Code when working with this repository.

## Project Structure

```
blog/
├── backend/          # Spring Boot 4.0.1 后端
│   ├── src/main/java/com/example/blog/
│   ├── src/main/resources/
│   └── pom.xml
├── frontend/         # Vue 3 前端 Monorepo
│   ├── packages/
│   │   ├── shared/   # 共享 API、类型、工具
│   │   ├── web/      # 前台博客 (端口 3000)
│   │   └── admin/    # 后台管理 (端口 3001)
│   └── package.json
├── scripts/          # 启动脚本
└── README.md
```

## Quick Start

```bash
# 启动后端 (端口 8080)
cd backend && ./mvnw spring-boot:run

# 启动前端 (需要先安装依赖)
cd frontend && npm install && npm run dev:web
cd frontend && npm run dev:admin

# 或使用脚本
./scripts/start-backend.sh
./scripts/start-frontend.sh
./scripts/start-all.sh
```

## Backend (Spring Boot)

### Build Commands
```bash
cd backend
./mvnw compile          # 编译
./mvnw spring-boot:run  # 运行
./mvnw test             # 测试
./mvnw package -DskipTests  # 打包
```

### Tech Stack
- Java 21, Spring Boot 4.0.1, Spring Security 7
- SQLite + Hibernate (community dialect)
- Flyway 数据库迁移
- JWT 认证 (jjwt 0.12.6)

### API Structure
- 公开: `/api/auth/**`, GET `/api/articles/**`, `/api/categories/**`, `/api/tags/**`
- 管理员: `/api/admin/**` (需要 ADMIN 角色)
- 响应格式: `Result<T>` = `{code, message, data}`

### Default Credentials
- Admin: `admin` / `admin123`

## Frontend (Vue 3)

### Build Commands
```bash
cd frontend
npm install             # 安装依赖
npm run dev:web         # 启动前台 (端口 3000)
npm run dev:admin       # 启动后台 (端口 3001)
npm run build           # 构建所有包
npm run lint            # 代码检查
npm run format          # 代码格式化
```

### Tech Stack
- Vue 3 + Vite + TypeScript
- Pinia 状态管理
- Vue Router
- Tailwind CSS
- Naive UI (admin)
- ByteMD (Markdown 编辑器)

### Package Structure
- `@blog/shared`: API 客户端、TypeScript 类型、工具函数
- `@blog/web`: 前台博客界面
- `@blog/admin`: 后台管理界面

### Coding Style
- TypeScript + Vue SFC
- 2-space 缩进
- 单引号，无分号
- 组件: PascalCase (如 `ArticleCard.vue`)
- 视图: `*View.vue`
- Composables: `useX.ts`

## Environment Variables

### Frontend (.env)
```
VITE_API_BASE_URL=http://localhost:8080
```

### Backend (application.yml)
```
JWT_SECRET=your-base64-encoded-secret
```

## Database
- SQLite 文件: `backend/blog.db` (gitignored)
- 迁移文件: `backend/src/main/resources/db/migration/`
