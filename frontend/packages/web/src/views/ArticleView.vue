<script setup lang="ts">
  import { ref, onMounted, computed, watch } from 'vue'
  import { useRoute, RouterLink } from 'vue-router'
  import MarkdownIt from 'markdown-it'
  import hljs from 'highlight.js'
  import { articleApi, commentApi, type ArticleDTO, type CommentDTO } from '@blog/shared'
  import { formatDate } from '@blog/shared'
  import 'highlight.js/styles/github-dark.css'

  const route = useRoute()
  const article = ref<ArticleDTO | null>(null)
  const comments = ref<CommentDTO[]>([])
  const loading = ref(true)
  const error = ref<string | null>(null)

  const md = new MarkdownIt({
    html: true,
    linkify: true,
    highlight: (str: string, lang: string) => {
      if (lang && hljs.getLanguage(lang)) {
        try {
          return hljs.highlight(str, { language: lang }).value
        } catch (_) {
          /* empty */
        }
      }
      return ''
    },
  })

  const renderedContent = computed(() => {
    if (!article.value) return ''
    return md.render(article.value.content)
  })

  async function loadArticle() {
    loading.value = true
    error.value = null
    try {
      const slug = route.params.slug as string
      const res = (await articleApi.getBySlug(slug)) as unknown as { data: ArticleDTO }
      article.value = res.data

      // Load comments
      const commentsRes = (await commentApi.getByArticle(res.data.id)) as unknown as {
        data: CommentDTO[]
      }
      comments.value = commentsRes.data.filter((c: CommentDTO) => c.status === 'APPROVED')
    } catch (err) {
      error.value = '文章加载失败'
      console.error(err)
    } finally {
      loading.value = false
    }
  }

  async function handleLike() {
    if (!article.value) return
    try {
      await articleApi.like(article.value.id)
      article.value.likeCount++
    } catch (err) {
      console.error('Failed to like:', err)
    }
  }

  watch(() => route.params.slug, loadArticle)

  onMounted(loadArticle)
</script>

<template>
  <div>
    <!-- Loading -->
    <div v-if="loading" class="flex justify-center py-12">
      <div
        class="w-8 h-8 border-4 border-blue-600 border-t-transparent rounded-full animate-spin"
      ></div>
    </div>

    <!-- Error -->
    <div v-else-if="error" class="text-center py-12 text-red-500">
      {{ error }}
    </div>

    <!-- Article Content -->
    <article v-else-if="article" class="pb-12">
      <!-- Header -->
      <header class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-4">
          {{ article.title }}
        </h1>

        <div class="flex items-center flex-wrap gap-4 text-sm text-gray-500 dark:text-gray-400">
          <span>{{ article.author.nickname || article.author.username }}</span>
          <span>{{ formatDate(article.publishedAt || article.createdAt, 'YYYY年MM月DD日') }}</span>
          <span v-if="article.category">
            <RouterLink
              :to="`/category/${article.category.id}`"
              class="hover:text-blue-600 dark:hover:text-blue-400"
            >
              {{ article.category.name }}
            </RouterLink>
          </span>
          <span class="flex items-center gap-1">
            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
              />
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"
              />
            </svg>
            {{ article.viewCount }}
          </span>
        </div>

        <!-- Tags -->
        <div v-if="article.tags.length" class="mt-4 flex flex-wrap gap-2">
          <RouterLink
            v-for="tag in article.tags"
            :key="tag.id"
            :to="`/tag/${tag.id}`"
            class="px-2 py-0.5 text-sm bg-gray-100 dark:bg-gray-800 text-gray-600 dark:text-gray-400 rounded hover:bg-gray-200 dark:hover:bg-gray-700"
          >
            #{{ tag.name }}
          </RouterLink>
        </div>
      </header>

      <!-- Cover Image -->
      <div v-if="article.coverImage" class="mb-8">
        <img
          :src="article.coverImage"
          :alt="article.title"
          class="w-full rounded-lg shadow-md"
        />
      </div>

      <!-- Content -->
      <div
        class="prose prose-lg dark:prose-invert max-w-none"
        v-html="renderedContent"
      ></div>

      <!-- Actions -->
      <div class="mt-8 pt-8 border-t border-gray-200 dark:border-gray-700">
        <button
          @click="handleLike"
          class="inline-flex items-center gap-2 px-4 py-2 bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-300 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700"
        >
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"
            />
          </svg>
          {{ article.likeCount }}
        </button>
      </div>

      <!-- Comments Section -->
      <section v-if="article.allowComment" class="mt-12">
        <h2 class="text-xl font-semibold text-gray-900 dark:text-white mb-6">
          评论 ({{ comments.length }})
        </h2>

        <div v-if="comments.length === 0" class="text-gray-500 dark:text-gray-400">
          暂无评论
        </div>

        <div v-else class="space-y-6">
          <div
            v-for="comment in comments"
            :key="comment.id"
            class="p-4 bg-gray-50 dark:bg-gray-800 rounded-lg"
          >
            <div class="flex items-center gap-2 mb-2">
              <span class="font-medium text-gray-900 dark:text-white">
                {{ comment.authorName || '匿名用户' }}
              </span>
              <span class="text-sm text-gray-500 dark:text-gray-400">
                {{ formatDate(comment.createdAt, 'YYYY-MM-DD HH:mm') }}
              </span>
            </div>
            <p class="text-gray-700 dark:text-gray-300">{{ comment.content }}</p>
          </div>
        </div>
      </section>
    </article>
  </div>
</template>
