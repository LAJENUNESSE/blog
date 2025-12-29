export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

export const ARTICLE_STATUS = {
  DRAFT: '草稿',
  PUBLISHED: '已发布',
  ARCHIVED: '已归档',
} as const

export const COMMENT_STATUS = {
  PENDING: '待审核',
  APPROVED: '已通过',
  REJECTED: '已拒绝',
} as const

export const USER_ROLE = {
  ADMIN: '管理员',
  USER: '用户',
} as const

export const DEFAULT_PAGE_SIZE = 10
