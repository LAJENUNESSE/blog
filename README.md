# Blog

一个使用 Spring Boot + Vue 3 构建的博客系统。

## 项目结构

```
blog/
├── backend/          # Spring Boot 后端
│   ├── src/
│   ├── pom.xml
│   └── mvnw
├── frontend/         # Vue 3 前端 (Monorepo)
│   ├── packages/
│   │   ├── shared/   # 共享代码
│   │   ├── web/      # 前台应用
│   │   └── admin/    # 后台管理
│   └── package.json
└── scripts/          # 启动脚本
```

## 技术栈

### 后端
- Java 21
- Spring Boot 4.0.1
- Spring Security + JWT
- SQLite + Hibernate
- Flyway 数据库迁移

### 前端
- Vue 3 + TypeScript
- Vite
- Pinia 状态管理
- Tailwind CSS（前台）
- Naive UI（后台）
- ByteMD 编辑器

## 快速开始

### 启动所有服务
```bash
./scripts/start-all.sh
```

### 单独启动

**后端：**
```bash
cd backend
./mvnw spring-boot:run
```

**前端：**
```bash
cd frontend
npm install  # 首次需要安装依赖
npm run dev:web    # 前台 (端口 3000)
npm run dev:admin  # 后台 (端口 3001)
```

## 访问地址

| 服务 | 地址 |
|------|------|
| 后端 API | http://localhost:8080 |
| 前台 | http://localhost:3000 |
| 后台管理 | http://localhost:3001/admin |

## 默认账号

- 用户名：`admin`
- 密码：`admin123`
