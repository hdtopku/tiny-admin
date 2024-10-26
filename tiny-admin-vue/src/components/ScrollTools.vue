<script lang="ts" setup>
import {SyncOutlined, VerticalAlignBottomOutlined, VerticalAlignTopOutlined} from "@ant-design/icons-vue";

const {scrollToRef, style} = defineProps({
  scrollToRef: Object,
  style: {type: Object, default: {right: '10px'}}
})
const reload = () => {
  location.reload()
}
const scrollToBottom = () => {
  scrollToRef && scrollToRef.scrollIntoView({
    behavior: 'smooth'
  })
}
</script>

<template>
  <a-float-button-group :style="style" shape="square">
    <a-float-button class="group" @click="reload">
      <template #icon>
        <SyncOutlined class="group-hover:scale-110 duration-300"/>
      </template>
    </a-float-button>
    <a-back-top :visibility-height="0" class="group">
      <template #icon>
        <VerticalAlignTopOutlined class="group-hover:scale-110 duration-300"/>
      </template>
    </a-back-top>
    <a-float-button class="group" @click="scrollToBottom">
      <template #icon>
        <VerticalAlignBottomOutlined class="group-hover:scale-110 duration-300"/>
      </template>
    </a-float-button>
  </a-float-button-group>
</template>

<style scoped>

</style>