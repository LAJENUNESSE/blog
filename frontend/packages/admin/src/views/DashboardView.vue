<script setup lang="ts">
  import { ref, onMounted } from 'vue'
  import { NGrid, NGi, NCard, NStatistic, NNumberAnimation } from 'naive-ui'
  import {
    articleApi,
    commentApi,
    categoryApi,
    tagApi,
    type ArticleDTO,
    type PageResult,
  } from '@blog/shared'

  const stats = ref({
    articles: 0,
    categories: 0,
    tags: 0,
    pendingComments: 0,
  })

  const recentArticles = ref<ArticleDTO[]>([])

  onMounted(async () => {
    try {
      const [articlesRes, categoriesRes, tagsRes, pendingRes] = await Promise.all([
        articleApi.admin.getAll({ page: 0, size: 1 }) as unknown as Promise<{
          data: PageResult<ArticleDTO>
        }>,
        categoryApi.admin.getAll() as unknown as Promise<{ data: unknown[] }>,
        tagApi.admin.getAll() as unknown as Promise<{ data: unknown[] }>,
        commentApi.admin.getPendingCount() as unknown as Promise<{ data: number }>,
      ])

      stats.value = {
        articles: articlesRes.data.totalElements,
        categories: categoriesRes.data.length,
        tags: tagsRes.data.length,
        pendingComments: pendingRes.data,
      }

      const recentRes = (await articleApi.admin.getAll({ page: 0, size: 5 })) as unknown as {
        data: PageResult<ArticleDTO>
      }
      recentArticles.value = recentRes.data.content
    } catch (err) {
      console.error('Failed to load stats:', err)
    }
  })
</script>

<template>
  <div class="space-y-6">
    <h1 class="text-2xl font-bold">仪表盘</h1>

    <NGrid :x-gap="16" :y-gap="16" :cols="4">
      <NGi>
        <NCard>
          <NStatistic label="文章总数">
            <NNumberAnimation :from="0" :to="stats.articles" />
          </NStatistic>
        </NCard>
      </NGi>
      <NGi>
        <NCard>
          <NStatistic label="分类数量">
            <NNumberAnimation :from="0" :to="stats.categories" />
          </NStatistic>
        </NCard>
      </NGi>
      <NGi>
        <NCard>
          <NStatistic label="标签数量">
            <NNumberAnimation :from="0" :to="stats.tags" />
          </NStatistic>
        </NCard>
      </NGi>
      <NGi>
        <NCard>
          <NStatistic label="待审核评论">
            <NNumberAnimation :from="0" :to="stats.pendingComments" />
          </NStatistic>
        </NCard>
      </NGi>
    </NGrid>

    <NCard title="最近文章">
      <div v-if="recentArticles.length === 0" class="text-gray-500">暂无文章</div>
      <div v-else class="space-y-3">
        <div
          v-for="article in recentArticles"
          :key="article.id"
          class="flex items-center justify-between py-2 border-b border-gray-100 last:border-0"
        >
          <RouterLink
            :to="`/articles/${article.id}/edit`"
            class="text-blue-600 hover:text-blue-700"
          >
            {{ article.title }}
          </RouterLink>
          <span class="text-sm text-gray-500">{{ article.status }}</span>
        </div>
      </div>
    </NCard>
  </div>
</template>
