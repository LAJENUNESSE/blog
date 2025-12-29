import request from './request'
import type { Result, AuthResponse, LoginRequest, RegisterRequest } from '../types'

export const authApi = {
  login: (data: LoginRequest) => request.post<Result<AuthResponse>>('/api/auth/login', data),

  register: (data: RegisterRequest) =>
    request.post<Result<AuthResponse>>('/api/auth/register', data),

  refresh: (refreshToken: string) =>
    request.post<Result<AuthResponse>>('/api/auth/refresh', null, {
      params: { refreshToken },
    }),
}
