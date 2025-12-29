<script setup lang="ts">
  import { ref, onMounted, watch } from 'vue'
  import { useRoute } from 'vue-router'
  import { articleApi, tagApi, type ArticleDTO, type TagDTO, type PageResult } from '@blog/shared'
  import ArticleCard from '@/components/article/ArticleCard.vue'

  const route = useRoute()
  const tag = ref<TagDTO | null>(null)
  const articles = ref<ArticleDTO[]>([])
  const loading = ref(true)
  const currentPage = ref(0)
  const hasMore = ref(false)

  async function loadData(page = 0) {
    loading.value = true
    try {
      const id = Number(route.params.id)

      if (page === 0) {
        const tagRes = (await tagApi.getById(id)) as unknown as { data: TagDTO }
        tag.value = tagRes.data
      }

      const res = (await articleApi.getByTag(id, { page, size: 10 })) as unknown as {
        data: PageResult<ArticleDTO>
      }

      if (page === 0) {
        articles.value = res.data.content
      } else {
        articles.value = [...articles.value, ...res.data.content]
      }
      currentPage.value = res.data.pageNumber
      hasMore.value = !res.data.last
    } catch (error) {
      console.error('Failed to load:', error)
    } finally {
      loading.value = false
    }
  }

  function loadMore() {
    if (hasMore.value && !loading.value) {
      loadData(currentPage.value + 1)
    }
  }

  watch(() => route.params.id, () => loadData(0))
  onMounted(() => loadData())
</script>

<template>
  <div>
    <!-- Header -->
    <div v-if="tag" class="mb-8">
      <h1 class="text-2xl font-bold text-gray-900 dark:text-white">
        标签: #{{ tag.name }}
      </h1>
      <p class="mt-1 text-sm text-gray-500 dark:text-gray-400">
        共 {{ tag.articleCount }} 篇文章
      </p>
    </div>

    <!-- Loading -->
    <div v-if="loading && articles.length === 0" class="flex justify-center py-12">
      <div class="w-8 h-8 border-4 border-blue-600 border-t-transparent rounded-full animate-spin"></div>
    </div>

    <!-- Empty -->
    <div v-else-if="articles.length === 0" class="text-center py-12 text-gray-500 dark:text-gray-400">
      该标签下暂无文章
    </div>

    <!-- Articles -->
    <div v-else>
      <ArticleCard v-for="article in articles" :key="article.id" :article="article" />

      <div v-if="hasMore" class="py-8 text-center">
        <button
          @click="loadMore"
          :disabled="loading"
          class="px-6 py-2 bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-300 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700 disabled:opacity-50"
        >
          {{ loading ? '加载中...' : '加载更多' }}
        </button>
      </div>
    </div>
  </div>
</template>
