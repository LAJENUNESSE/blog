export interface CategoryDTO {
  id: number
  name: string
  slug: string
  description: string | null
  sortOrder: number
  articleCount: number
  createdAt: string
  updatedAt: string
}

export interface CategoryRequest {
  name: string
  slug?: string
  description?: string
  sortOrder?: number
}
