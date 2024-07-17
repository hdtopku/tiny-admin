<template>
  <a-layout class="min-h-screen">
    <div v-show="showMask" class="mask" @click="closeMask"></div>
    <a-layout-sider class="z-[1000]"
                    :style="{height: '100vh', position: isMiddleWidth ?'fixed' : '', left: 0, top: 0, bottom: 0 }"
                    v-model:collapsed="menuCollapsed" collapsible breakpoint="md"
                    :collapsedWidth="collapsedWidth"
                    @collapse="onCollapse"
                    @breakpoint="onBreakpoint">
      <div :class="{'hidden':isMiddleWidth && menuCollapsed, 'text-xs': menuCollapsed, 'text-2xl':!menuCollapsed}"
           class="text-center py-4 font-bold text-gray-400">
        Tiny Admin
      </div>
      <Sidebar class="pb-16 pt-4"/>
      <span @click="menuCollapsed =!menuCollapsed"
            class="opacity-80 absolute top-2/3 text-white bg-[#011528] -right-10 text-2xl hover:bg-gray-700 rounded-r-lg cursor-pointer p-2">
      <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="currentColor"><path
          d="M120-240v-80h720v80H120Zm0-200v-80h720v80H120Zm0-200v-80h720v80H120Z"/></svg>
        </span>
    </a-layout-sider>
    <a-layout>
      <a-layout-header class="flex !px-4">
      </a-layout-header>
      <a-layout-content>
        <router-view #default="{ Component, route }">
          <transition :duration="{ enter: 10, leave: 0 }"
                      appear-active-class="animate__animated animate__slideInUp"
                      enter-active-class="animate__animated animate__fadeIn fade-enter-active"
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
</template>
<script lang="ts" setup>
import {ref} from 'vue';
import Sidebar from "@/layout/Sidebar.vue";
import {storeToRefs} from "pinia";
import {useMenuStore} from "@/store";

const {menuCollapsed} = storeToRefs(useMenuStore())
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
watch(menuCollapsed, (val) => {
  if (isMiddleWidth.value) {
    showMask.value = !val;
  }
})

const closeMask = () => {
  showMask.value = false;
  menuCollapsed.value = true;
}
</script>
<style scoped>
::v-deep(.ant-layout-sider-zero-width-trigger) {
  display: none;
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

