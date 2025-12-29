import request from './request'
import type { Result, PageResult, PageParams, UserDTO } from '../types'

export const userApi = {
  admin: {
    getAll: (params?: PageParams) =>
      request.get<Result<PageResult<UserDTO>>>('/api/admin/users', { params }),

    getById: (id: number) => request.get<Result<UserDTO>>(`/api/admin/users/${id}`),

    toggleStatus: (id: number) =>
      request.post<Result<UserDTO>>(`/api/admin/users/${id}/toggle-status`),

    delete: (id: number) => request.delete<Result<void>>(`/api/admin/users/${id}`),
  },
}
