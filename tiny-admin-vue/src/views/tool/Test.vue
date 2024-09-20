<template>
  <ImageUpload v-model:image-urls="imageUrls" :count="2"/>
</template>
<script lang="ts" setup>
import {Ref} from "vue";

const imageUrls: Ref<string[]> = ref([])
watch(imageUrls, (newVal, oldVal) => {
  console.log('newVal:', newVal)
  console.log('oldVal:', oldVal)
})
</script>