<script setup lang="ts">
import {useDebounceFn} from "@vueuse/core"

const props = defineProps({
  loading: {
    type: Boolean,
    default: false
  }
})
const keyword = ref(''), status = ref(true)
const emit = defineEmits(['openModal', 'queryList'])
const handleAdd = () => {
  emit('openModal')
}
const handleSearch = () => {
  emit('queryList', {keyword: keyword.value, status: status.value})
}
handleSearch()
const debounceQuery = useDebounceFn(handleSearch, 500)
watch(() => [keyword.value, status.value], debounceQuery)
</script>

<template>
  <div class="flex mb-3 bg-gray-100">
    <div class="flex items-center sm:gap-4 gap-1 mx-auto sm:w-[80%] w-[95%]">
      <a-button :loading="props.loading" type="primary" @click="handleAdd">{{
          $t('新增')
        }}
      </a-button>
      <a-input
          id="keyword"
          v-model:value="keyword"
          :placeholder="$t('搜索用户名、昵称、邮箱、手机号')"
          allow-clear
          autocomplete="off"
          class="text-left"
          type="text"
          @keyup.enter.native="handleSearch"
      >
        <template #prefix>
          <a-switch
              v-model:checked="status"
              :checked-children="$t('已启用')"
              :loading="props.loading"
              :un-checked-children="$t('已禁用')"
              class="flex-shrink-0"
          />
        </template>
        <template #suffix>
          <a-button :loading="props.loading" type="primary" @click="handleSearch">{{
              $t('搜索')
            }}
          </a-button>
        </template>
      </a-input>
    </div>
  </div>
</template>