import request from './request'
import type { Result, PageResult, PageParams, TagDTO, TagRequest } from '../types'

export const tagApi = {
  // 公开接口
  getAll: () => request.get<Result<TagDTO[]>>('/api/tags'),

  getById: (id: number) => request.get<Result<TagDTO>>(`/api/tags/${id}`),

  getBySlug: (slug: string) => request.get<Result<TagDTO>>(`/api/tags/slug/${slug}`),

  // 管理接口
  admin: {
    getAll: () => request.get<Result<TagDTO[]>>('/api/admin/tags'),

    getPaged: (params?: PageParams) =>
      request.get<Result<PageResult<TagDTO>>>('/api/admin/tags/paged', { params }),

    create: (data: TagRequest) => request.post<Result<TagDTO>>('/api/admin/tags', data),

    update: (id: number, data: TagRequest) =>
      request.put<Result<TagDTO>>(`/api/admin/tags/${id}`, data),

    delete: (id: number) => request.delete<Result<void>>(`/api/admin/tags/${id}`),
  },
}
