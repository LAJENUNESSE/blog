import request from './request'
import type { Result, CategoryDTO, CategoryRequest } from '../types'

export const categoryApi = {
  // 公开接口
  getAll: () => request.get<Result<CategoryDTO[]>>('/api/categories'),

  getById: (id: number) => request.get<Result<CategoryDTO>>(`/api/categories/${id}`),

  getBySlug: (slug: string) => request.get<Result<CategoryDTO>>(`/api/categories/slug/${slug}`),

  // 管理接口
  admin: {
    getAll: () => request.get<Result<CategoryDTO[]>>('/api/admin/categories'),

    create: (data: CategoryRequest) =>
      request.post<Result<CategoryDTO>>('/api/admin/categories', data),

    update: (id: number, data: CategoryRequest) =>
      request.put<Result<CategoryDTO>>(`/api/admin/categories/${id}`, data),

    delete: (id: number) => request.delete<Result<void>>(`/api/admin/categories/${id}`),
  },
}
