<script lang="ts" setup>
const {content, length} = defineProps({
  content: {type: String, default: ''},
  length: {type: Number, default: 20},
  trigger: {type: String, default: 'hover'}
})
</script>

<template>
  <span v-if="content?.length">
    <span v-if="content.length <= length">{{ content }}</span>
    <a-tooltip v-else :arrow="false" :trigger="trigger">
      <template #title>
        <span>{{ content }}</span>
      </template>
      <span>{{ content?.substring(0, length) }}...</span>
    </a-tooltip>
  </span>
</template>

<style scoped>

</style>