// 统一响应格式
export interface Result<T> {
  code: number
  message: string
  data: T
}

// 分页响应格式
export interface PageResult<T> {
  content: T[]
  pageNumber: number
  pageSize: number
  totalElements: number
  totalPages: number
  first: boolean
  last: boolean
}

// 分页请求参数
export interface PageParams {
  page?: number
  size?: number
}
