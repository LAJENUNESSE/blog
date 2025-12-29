<script setup lang="ts">
  import { ref, onMounted } from 'vue'
  import { articleApi, type ArticleDTO, type PageResult } from '@blog/shared'
  import ArticleCard from '@/components/article/ArticleCard.vue'

  const articles = ref<ArticleDTO[]>([])
  const loading = ref(true)
  const currentPage = ref(0)
  const totalPages = ref(0)
  const hasMore = ref(false)

  async function loadArticles(page = 0) {
    loading.value = true
    try {
      const res = (await articleApi.getPublished({ page, size: 10 })) as unknown as {
        data: PageResult<ArticleDTO>
      }
      if (page === 0) {
        articles.value = res.data.content
      } else {
        articles.value = [...articles.value, ...res.data.content]
      }
      currentPage.value = res.data.pageNumber
      totalPages.value = res.data.totalPages
      hasMore.value = !res.data.last
    } catch (error) {
      console.error('Failed to load articles:', error)
    } finally {
      loading.value = false
    }
  }

  function loadMore() {
    if (hasMore.value && !loading.value) {
      loadArticles(currentPage.value + 1)
    }
  }

  onMounted(() => {
    loadArticles()
  })
</script>

<template>
  <div>
    <!-- Loading State -->
    <div v-if="loading && articles.length === 0" class="flex justify-center py-12">
      <div
        class="w-8 h-8 border-4 border-blue-600 border-t-transparent rounded-full animate-spin"
      ></div>
    </div>

    <!-- Empty State -->
    <div
      v-else-if="!loading && articles.length === 0"
      class="text-center py-12 text-gray-500 dark:text-gray-400"
    >
      暂无文章
    </div>

    <!-- Article List -->
    <div v-else>
      <ArticleCard v-for="article in articles" :key="article.id" :article="article" />

      <!-- Load More Button -->
      <div v-if="hasMore" class="py-8 text-center">
        <button
          @click="loadMore"
          :disabled="loading"
          class="px-6 py-2 bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-300 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700 disabled:opacity-50"
        >
          {{ loading ? '加载中...' : '加载更多' }}
        </button>
      </div>

      <!-- No More -->
      <div v-else class="py-8 text-center text-gray-400 dark:text-gray-500 text-sm">
        没有更多了
      </div>
    </div>
  </div>
</template>
