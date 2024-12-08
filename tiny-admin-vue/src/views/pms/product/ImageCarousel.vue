<template>
  <a-carousel arrows :style="{width, height}">
    <template #prevArrow>
      <div class="custom-slick-arrow left-arrow">
        <span class="anticon anticon-left-circle">
          <LeftOutlined />
        </span>
      </div>
    </template>
    <template #nextArrow>
      <div class="custom-slick-arrow right-arrow">
        <span class="anticon anticon-right-circle">
          <RightOutlined />
        </span>
      </div>
    </template>
    <template #customPaging="props">
      <img :src="(imgUrls[props.i] as string) ?? ''" class="cursor-pointer custom-paging-image" alt=""/>
    </template>
    <div v-for="url in imgUrls" :key="url as string">
      <img :src="url as string" alt="" class="carousel-image"/>
    </div>
  </a-carousel>
</template>

<script lang="ts" setup>
import { LeftOutlined, RightOutlined } from "@ant-design/icons-vue";

const {imgUrls, width, height} = defineProps({
  imgUrls: {
    type: Array,
    default: [],
  },
  width: {
    type: String,
    default: '200px',
  },
  height: {
    type: String,
    default: '200px',
  },
});
</script>

<style scoped>

/* Beautify Slick Dots */
:deep(.slick-dots) {
  z-index: 1;
  position: absolute;
  bottom: 15px;
  width: 100%;
  display: flex;
  justify-content: center;
  gap: 8px; /* Add spacing between dots */
  flex-wrap: wrap; /* Allow wrapping if too many dots */
}

/* Style for custom paging images */
:deep(.slick-dots li button img) {
  width: 30px; /* Increase the size of the pagination image */
  height: 30px; /* Ensure it's a square */
  object-fit: cover; /* Ensure the image covers the area without distortion */
  border-radius: 50%; /* Make the image circular */
}

/* Hover effect on the active dot */
:deep(.slick-dots li.slick-active button img) {
  opacity: 1;
}

/* Enhance Slick Slide */
:deep(.slick-slide img) {
  border: 5px solid #fff;
  overflow: hidden;
  border-radius: 8px; /* Rounded corners */
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1); /* Add shadow */
  display: block;
  margin: auto;
  transition: transform 0.3s ease; /* Zoom effect */
}

:deep(.slick-slide img:hover) {
  transform: scale(1.05); /* Slight zoom on hover */
}

:deep(.custom-slick-arrow::before) {
  display: none; /* Hide default arrow */
}

/* Style Custom Arrow */
:deep(.slick-arrow.custom-slick-arrow) {
  position: absolute;
  top: 50%;
  transform: translateY(-50%) translateZ(0); /* Trigger GPU acceleration */
  z-index: 1; /* Higher z-index to ensure arrow visibility */
  width: 40px;
  height: 40px;
  font-size: 20px;
  color: #fff;
  background-color: rgba(0, 0, 0, 0.6); /* Darker background */
  border-radius: 50%; /* Circular background */
  display: flex !important;
  align-items: center;
  justify-content: center; /* Center the icon horizontally and vertically */
  opacity: 0.7;
  overflow: hidden;
  transition: opacity 0.3s, transform 0.3s; /* Both opacity and transform on hover */
  pointer-events: auto; /* Ensure the arrows are clickable */
  will-change: transform, opacity; /* Optimize for both transform and opacity */
}

/* Hover Effect for Arrows */
:deep(.slick-arrow.custom-slick-arrow:hover) {
  opacity: 1; /* Only change opacity on hover */
  transform: translateY(-50%) scale(1.1); /* Scale on hover to create zoom effect */
}

/* Position Arrows */
.left-arrow {
  left: 5px;
}

.right-arrow {
  right: 5px;
}

/* Arrow Icons */
:deep(.anticon) {
  font-size: 24px; /* Adjust the size of the icon */
  line-height: 1; /* Ensure the icon's line-height doesn't affect its position */
  display: inline-flex; /* Ensure the icon behaves like an inline element */
  margin: 0; /* Remove any margin */
  vertical-align: middle; /* Center the icon vertically */
  justify-content: center; /* Center the icon horizontally */
  align-items: center; /* Center the icon horizontally */
}

/* Image in Carousel */
.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  margin: 0 auto;
}
</style>
