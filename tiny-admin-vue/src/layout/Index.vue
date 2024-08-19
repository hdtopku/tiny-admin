<template>
  <Page>
    <a-layout class="min-h-screen">
      <div v-show="showMask" class="mask" @click="closeMask"></div>
      <a-layout-sider
          v-model:collapsed="sidebarCollapsed"
          :collapsedWidth="collapsedWidth"
          :style="{overflow: 'auto', position: isMiddleWidth ?'fixed' : 'fixed', left: 0, top: 0, bottom: 0 }"
          breakpoint="md" class="z-[1000] float-left"
          collapsible
          @breakpoint="onBreakpoint"
          @collapse="onCollapse">
        <div
            :class="{'hidden':isMiddleWidth && sidebarCollapsed, 'text-xs w-[80px]': sidebarCollapsed, 'text-2xl w-[200px]':!sidebarCollapsed}"
            class="py-4 font-bold text-gray-400 float-left text-center">
          Tiny Admin
        </div>
        <Sidebar class="overflow-auto mt-[64px]"/>
        <span :class="sidebarCollapsed ? 'left-20' : 'left-52'"
              class="opacity-80 fixed z-10 top-1/2 transform -translate-x-1/2 -translate-y-1/2 text-white bg-[#011528] text-2xl hover:bg-gray-700 rounded-r-lg cursor-pointer p-2"
              @click="sidebarCollapsed =!sidebarCollapsed">
      <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="currentColor"><path
          d="M120-240v-80h720v80H120Zm0-200v-80h720v80H120Zm0-200v-80h720v80H120Z"/></svg>
        </span>
      </a-layout-sider>
      <a-layout
          :class="{'ml-[80px]': sidebarCollapsed, 'ml-[200px]':!sidebarCollapsed}"
      >
        <a-layout-header class="fixed w-full z-[12] border-b border-gray-200">
          <Breadcrumb class="mx-4 my-2"/>
        </a-layout-header>
        <a-layout-content class="relative mt-20">
          <Tabs class="bg-white fixed z-10 pt-11 top-0 px-2"/>
          <router-view #default="{ Component, route }" class="">
            <transition :duration="{ enter: 250, leave: 150 }"
                        appear
                        appear-active-class="animate__animated animate__fadeInLeft"
                        enter-active-class="animate__animated animate__fadeIn fade-enter-active"
                        leave-active-class="animate__animated animate__fadeOut"
                        mode="out-in">
              <keep-alive>
                <component :is="Component"
                           :key="route.path + (route.meta.keepAlive ? '' : Math.random())"/>
              </keep-alive>
            </transition>
          </router-view>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </Page>
</template>
<script lang="ts" setup>
import {ref} from 'vue';
import {storeToRefs} from "pinia";
import {useMenuStore} from "@/store";
import Tabs from "@/layout/Tabs.vue";
import Sidebar from "@/layout/Sidebar.vue";
import Breadcrumb from "@/layout/Breadcrumb.vue";

const {sidebarCollapsed} = storeToRefs(useMenuStore())


const collapsedWidth = ref<number>(80);
const isMiddleWidth = ref<boolean>(false);
const showMask = ref<boolean>(false);

const onCollapse = (collapsed: boolean) => {
  collapsedWidth.value = isMiddleWidth.value && collapsed ? 0.01 : 80;
};
const onBreakpoint = (broken: boolean) => {
  isMiddleWidth.value = broken;
  broken ? collapsedWidth.value = 0.01 : collapsedWidth.value = 80;
  if (!broken) showMask.value = false
};

watch(showMask, (val) => {
  val ? document.body.style.overflow = "hidden" : document.body.style.overflow = "auto"
})
watch(sidebarCollapsed, (val) => {
  if (isMiddleWidth.value) {
    showMask.value = !val;
  }
})

const closeMask = () => {
  showMask.value = false;
  sidebarCollapsed.value = true;
}
</script>
<style scoped>
::v-deep(.ant-layout-sider-zero-width-trigger) {
  display: none;
}

::v-deep(.ant-layout-header) {
  height: auto;
  padding: 0;
}

.ant-layout .ant-layout-header {
  background-color: #fff;
}

.mask {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 999;
}
</style>

