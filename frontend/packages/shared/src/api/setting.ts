import request from './request'
import type { Result, SettingDTO, SettingRequest } from '../types'

export const settingApi = {
  // 公开接口
  getAll: () => request.get<Result<Record<string, string>>>('/api/settings'),

  getByKey: (key: string) => request.get<Result<string>>(`/api/settings/${key}`),

  // 管理接口
  admin: {
    getAll: () => request.get<Result<SettingDTO[]>>('/api/admin/settings'),

    getByKey: (key: string) => request.get<Result<SettingDTO>>(`/api/admin/settings/${key}`),

    save: (data: SettingRequest) => request.post<Result<SettingDTO>>('/api/admin/settings', data),

    batchUpdate: (data: Record<string, string>) =>
      request.put<Result<void>>('/api/admin/settings/batch', data),

    delete: (key: string) => request.delete<Result<void>>(`/api/admin/settings/${key}`),
  },
}
