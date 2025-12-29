export type CommentStatus = 'PENDING' | 'APPROVED' | 'REJECTED'

export interface CommentDTO {
  id: number
  content: string
  authorName: string | null
  authorEmail: string | null
  authorUrl: string | null
  status: CommentStatus
  articleId: number
  articleTitle: string
  parentId: number | null
  replies: CommentDTO[]
  createdAt: string
  updatedAt: string
}

export interface CommentRequest {
  content: string
  authorName?: string
  authorEmail?: string
  authorUrl?: string
  parentId?: number
}
