export interface TagDTO {
  id: number
  name: string
  slug: string
  articleCount: number
  createdAt: string
  updatedAt: string
}

export interface TagRequest {
  name: string
  slug?: string
}
