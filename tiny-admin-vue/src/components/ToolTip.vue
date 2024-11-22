<script lang="ts" setup>
const {content, length} = defineProps({
  content: {type: String, default: ''},
  length: {type: Number, default: 20},
  trigger: {type: String, default: 'hover'},
  className: {type: String, default: ''},
})
</script>

<template>
  <span v-if="content?.length">
    <span :class="className" v-if="content.length <= length">{{ content }}</span>
    <a-tooltip v-else :arrow="false" :trigger="trigger">
      <template #title>
        <span>{{ content }}</span>
      </template>
      <span :class="className">{{ content?.substring(0, length) }}...</span>
    </a-tooltip>
  </span>
</template>

<style scoped>

</style>