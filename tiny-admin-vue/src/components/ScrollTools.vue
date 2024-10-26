<script lang="ts" setup>
import {SyncOutlined, VerticalAlignBottomOutlined, VerticalAlignTopOutlined} from "@ant-design/icons-vue";

const {scrollToRef, style} = defineProps({
  scrollToRef: Object,
  style: {type: Object, default: {right: '10px'}}
})
const reload = () => {
  location.reload()
}
// let bottomRef
// const setRefAction = (ref) => {
//   bottomRef = ref
// }
const bottomRef = ref()
const scrollToBottom = () => {
  bottomRef.value && bottomRef.value.scrollIntoView({
    behavior: 'smooth'
  })
}
</script>

<template>
  <a-float-button-group :style="style" shape="square">
    <a-back-top :duration="300" class="group">
      <template #icon>
        <VerticalAlignTopOutlined class="group-hover:scale-110 duration-300"/>
      </template>
    </a-back-top>
    <a-float-button class="group" @click="reload">
      <template #icon>
        <SyncOutlined class="group-hover:scale-110 duration-300"/>
      </template>
    </a-float-button>
    <a-float-button class="group" @click="scrollToBottom">
      <template #icon>
        <VerticalAlignBottomOutlined class="group-hover:scale-110 duration-300"/>
      </template>
    </a-float-button>
  </a-float-button-group>
  <div ref="bottomRef"></div>
</template>

<style scoped>

</style>