import { ref, computed } from 'vue'
import type { PageResult } from '../types'
import { DEFAULT_PAGE_SIZE } from '../constants'

export function usePagination<T>(fetchFn: (page: number, size: number) => Promise<PageResult<T>>) {
  const data = ref<T[]>([]) as { value: T[] }
  const loading = ref(false)
  const currentPage = ref(1)
  const pageSize = ref(DEFAULT_PAGE_SIZE)
  const total = ref(0)
  const totalPages = ref(0)

  const hasMore = computed(() => currentPage.value < totalPages.value)
  const isEmpty = computed(() => !loading.value && data.value.length === 0)

  async function fetch(page = 1) {
    loading.value = true
    try {
      const result = await fetchFn(page - 1, pageSize.value) // 后端分页从0开始
      data.value = result.content
      currentPage.value = result.pageNumber + 1
      total.value = result.totalElements
      totalPages.value = result.totalPages
    } finally {
      loading.value = false
    }
  }

  async function loadMore() {
    if (!hasMore.value || loading.value) return
    loading.value = true
    try {
      const result = await fetchFn(currentPage.value, pageSize.value)
      data.value = [...data.value, ...result.content]
      currentPage.value = result.pageNumber + 1
      total.value = result.totalElements
      totalPages.value = result.totalPages
    } finally {
      loading.value = false
    }
  }

  function reset() {
    data.value = []
    currentPage.value = 1
    total.value = 0
    totalPages.value = 0
  }

  return {
    data,
    loading,
    currentPage,
    pageSize,
    total,
    totalPages,
    hasMore,
    isEmpty,
    fetch,
    loadMore,
    reset,
  }
}
