import request from './request'
import type { Result, UploadResponse } from '../types'

export const uploadApi = {
  uploadImage: (file: File) => {
    const formData = new FormData()
    formData.append('file', file)
    return request.post<Result<UploadResponse>>('/api/admin/upload', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    })
  },

  uploadImages: (files: File[]) => {
    const formData = new FormData()
    files.forEach((file) => formData.append('files', file))
    return request.post<Result<UploadResponse[]>>('/api/admin/upload/batch', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    })
  },
}
