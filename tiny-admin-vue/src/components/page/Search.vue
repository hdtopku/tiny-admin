<script setup lang="ts">
import {useDebounceFn} from "@vueuse/core"

const keyword = ref(''), enabled = ref(true)
const emit = defineEmits(['openModal', 'queryList'])
const handleAdd = () => {
  emit('openModal')
}
const handleSearch = () => {
  keyword.value = keyword.value.trim()
  emit('queryList', {keyword: keyword.value, enabled: enabled.value})
}
const debounceQuery = useDebounceFn(handleSearch, 500)
watch(() => [keyword.value, enabled.value], debounceQuery)

const {loading, placeholder, showSwitch} = defineProps({
  loading: {
    type: Boolean,
    default: false
  },
  placeholder: {
    type: String,
    default: 'Please enter search keywords'
  },
  showSwitch: {
    type: Boolean,
    default: true
  },
  showAdd: {
    type: Boolean,
    default: true
  },
  searchClass: {
    type: String,
    default: 'bg-gray-100 dark:bg-black'
  },
  top: {
    type: Number,
    default: 0
  }
})
</script>

<template>
  <a-affix :offset-top="top">
    <div :class="searchClass" class="flex mb-3">
      <div class="flex items-center sm:gap-4 gap-1 mx-auto sm:w-[80%] w-[95%]">
        <a-button v-if="showAdd" :loading="loading" type="primary" @click="handleAdd">
          Add
        </a-button>
        <a-input
            id="keyword"
            v-model:value="keyword"
            :placeholder="placeholder"
            allow-clear
            autocomplete="off"
            class="text-left"
            type="text"
            @keyup.enter.native="handleSearch"
        >
          <template #prefix>
            <a-switch
                v-if="showSwitch"
                v-model:checked="enabled"
                checked-children="Enabled"
                :loading="loading"
                un-checked-children="Disabled"
                class="flex-shrink-0"
            />
          </template>
          <template #suffix>
            <a-button :loading="loading" type="primary" @click="handleSearch">Search</a-button>
          </template>
        </a-input>
      </div>
    </div>
  </a-affix>
</template>