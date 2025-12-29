import request from './request'
import type { Result, PageResult, PageParams, CommentDTO, CommentRequest } from '../types'

export const commentApi = {
  // 公开接口
  getByArticle: (articleId: number) =>
    request.get<Result<CommentDTO[]>>(`/api/comments/article/${articleId}`),

  create: (articleId: number, data: CommentRequest) =>
    request.post<Result<CommentDTO>>(`/api/comments/article/${articleId}`, data),

  // 管理接口
  admin: {
    getAll: (params?: PageParams & { status?: string }) =>
      request.get<Result<PageResult<CommentDTO>>>('/api/admin/comments', { params }),

    getById: (id: number) => request.get<Result<CommentDTO>>(`/api/admin/comments/${id}`),

    getPendingCount: () => request.get<Result<number>>('/api/admin/comments/pending/count'),

    approve: (id: number) => request.post<Result<CommentDTO>>(`/api/admin/comments/${id}/approve`),

    reject: (id: number) => request.post<Result<CommentDTO>>(`/api/admin/comments/${id}/reject`),

    delete: (id: number) => request.delete<Result<void>>(`/api/admin/comments/${id}`),
  },
}
