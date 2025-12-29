export interface SettingDTO {
  id: number
  key: string
  value: string | null
  description: string | null
}

export interface SettingRequest {
  key: string
  value?: string
  description?: string
}
