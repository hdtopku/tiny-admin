<template>
  <a-carousel arrows style="width: 200px">
    <template #prevArrow>
      <div class="custom-slick-arrow" style="left: 10px; z-index: 1">
        <left-circle-outlined/>
      </div>
    </template>
    <template #nextArrow>
      <div class="custom-slick-arrow" style="right: 10px">
        <right-circle-outlined/>
      </div>
    </template>
    <template #customPaging="props">
      <a>
        <img :src="params.imgUrls[props.i]"/>
      </a>
    </template>
    <div v-for="url in params.imgUrls" :key="url">
      <img :src="url" style="margin: 0 auto"/>
    </div>
  </a-carousel>
</template>
<script lang="ts" setup>
import {LeftCircleOutlined, RightCircleOutlined} from '@ant-design/icons-vue';

const params = defineProps({
  imgUrls: {
    type: Array,
    required: true,
  }
})

</script>
<style scoped>
:deep(.slick-slide) {
  text-align: center;
  height: 160px;
  line-height: 160px;
  overflow: hidden;
}

:deep(.slick-arrow.custom-slick-arrow) {
  width: 25px;
  height: 25px;
  font-size: 25px;
  color: #fff;
  background-color: rgba(31, 45, 61, 0.11);
  transition: ease all 0.3s;
  opacity: 0.3;
  z-index: 1;
}

:deep(.slick-arrow.custom-slick-arrow:before) {
  display: none;
}

:deep(.slick-arrow.custom-slick-arrow:hover) {
  color: #fff;
  opacity: 0.5;
}


</style>
