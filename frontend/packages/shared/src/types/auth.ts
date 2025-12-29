export type Role = 'ADMIN' | 'USER'

export interface UserInfo {
  id: number
  username: string
  email: string
  nickname: string | null
  avatar: string | null
  role: Role
}

export interface UserDTO {
  id: number
  username: string
  email: string
  nickname: string | null
  avatar: string | null
  role: Role
  enabled: boolean
  createdAt: string
  updatedAt: string
}

export interface LoginRequest {
  username: string
  password: string
}

export interface RegisterRequest {
  username: string
  password: string
  email: string
  nickname?: string
}

export interface AuthResponse {
  accessToken: string
  refreshToken: string
  tokenType: string
  expiresIn: number
  user: UserInfo
}
