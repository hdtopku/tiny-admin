<template>
  <a-layout class="min-h-screen">
    <div v-show="showMask" class="mask" @click="closeMask"></div>
    <a-layout-sider
        v-model:collapsed="sidebarCollapsed"
        :collapsedWidth="collapsedWidth"
        :style="{
        overflow: 'auto',
        position: widthLessThanMiddle ? 'fixed' : 'fixed',
        left: 0,
        top: 0,
        bottom: 0,
      }"
        breakpoint="md"
        class="z-[1000] float-left"
        collapsible
        @breakpoint="onBreakpoint"
        @collapse="onCollapse"
    >
      <div
          :class="{
          hidden: widthLessThanMiddle && sidebarCollapsed,
          'text-xs w-[80px]': sidebarCollapsed,
          'text-2xl w-[200px]': !sidebarCollapsed,
        }"
          class="py-4 font-bold text-gray-400 float-left text-center"
      >
        Tiny Admin
      </div>
      <Sidebar class="overflow-auto mt-[64px]"/>
      <span
          :class="getSidebarClass()"
          class="opacity-80 fixed z-10 top-1/2 transform -translate-x-1/2 -translate-y-1/2 text-white bg-[#011528] text-2xl hover:bg-gray-700 rounded-lg cursor-pointer p-1"
          @click="sidebarCollapsed = !sidebarCollapsed"
      >
        <svg
            fill="currentColor"
            height="24px"
            viewBox="0 -960 960 960"
            width="14px"
            xmlns="http://www.w3.org/2000/svg"
        >
          <path
              d="M120-240v-80h720v80H120Zm0-200v-80h720v80H120Zm0-200v-80h720v80H120Z"
          />
        </svg>
      </span>
    </a-layout-sider>
    <a-layout>
      <a-layout-header
          :class="getContentClass()"
          class="fixed z-10 border-l border-b border-gray-800 flex justify-between items-center"
      >
        <Breadcrumb/>
        <div class="flex items-center gap-3">
          <a-button class="group" ghost size="small" type="primary" @click="toggleLocale">
            <div class="group-hover:scale-105 duration-300 flex items-center gap-[2px]">
              <span class="rounded-full">{{ getLanguageText() === 'ENG' ? '🇬🇧' : '🇨🇳' }}</span>
              {{ getLanguageText() }}
            </div>
          </a-button>
          <DarkToggle/>
          <a-dropdown>
            <template #overlay>
              <a-menu>
                <a-menu-item>
                  <a-button size="small" type="link" @click="showMyInfo">{{
                      $t('个人中心')
                    }}
                  </a-button>
                </a-menu-item>
                <a-menu-item>
                  <a-button danger size="small" type="link" @click="logout">{{
                      $t('退出登录')
                    }}
                  </a-button>
                </a-menu-item>
              </a-menu>
            </template>
            <div class="hover:scale-105 duration-300 flex items-center gap-1 cursor-pointer">
              <a-avatar
                  :size="32"
                  :src="useUserStore().userInfo.avatar"
                  class="border-2 border-l-orange-400 border-t-amber-600 border-b-fuchsia-500 border-r-purple-500"
                  shape="circle"
              />
            </div>
          </a-dropdown>
        </div>
      </a-layout-header>
      <a-layout-content :class="getContentClass()" class="pt-[40px]">
        <Tabs class="fixed z-100 pt-[2px]"/>
        <div class="mt-10">
          <router-view #default="{ Component }">
            <transition
                :duration="{ enter: 250, leave: 150 }"
                appear
                appear-active-class="animate__animated animate__fadeInLeft"
                enter-active-class="animate__animated animate__fadeIn fade-enter-active"
                leave-active-class="animate__animated animate__fadeOut"
                mode="out-in"
            >
              <keep-alive>
                <component :is="Component" :key="getKey()"/>
              </keep-alive>
            </transition>
          </router-view>
        </div>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>
<script lang="ts" setup>
import i18n, {t} from '@/utils/i18n.ts'
import {ref} from 'vue'
import {storeToRefs} from 'pinia'
import {useMenuStore, useUserStore} from '@/store'
import Tabs from '@/layout/Tabs.vue'
import Sidebar from '@/layout/Sidebar.vue'
import {useRoute} from 'vue-router'
import router from '@/router'
import {message} from 'ant-design-vue'
import DarkToggle from '@/components/DarkToggle.vue'
import useGlobal from '@/hooks/useGlobal.ts'
import Breadcrumb from '@/layout/Breadcrumb.vue'

const {sidebarCollapsed, widthLessThanMiddle} = storeToRefs(useMenuStore())

const collapsedWidth = ref<number>(80)
const showMask = ref<boolean>(false)
const onCollapse = (collapsed: boolean) => {
  collapsedWidth.value = widthLessThanMiddle.value && collapsed ? 0.01 : 80
}
const onBreakpoint = (broken: boolean) => {
  widthLessThanMiddle.value = broken
  broken ? (collapsedWidth.value = 0.01) : (collapsedWidth.value = 80)
  if (!broken) showMask.value = false
}
let routeKey,
    sidebarChanged = false // 解决点击侧边栏，收缩或展开时页面会跟着刷新的问题
const getKey = () => {
  if (!routeKey || !sidebarChanged) {
    routeKey =
        useRoute().path + (useRoute().meta.keepAlive ? '' : Math.random())
  }
  sidebarChanged = false
  return routeKey
}
watch(showMask, (val) => {
  val
      ? (document.body.style.overflow = 'hidden')
      : (document.body.style.overflow = 'auto')
})
watch(sidebarCollapsed, (val) => {
  if (widthLessThanMiddle.value) {
    showMask.value = !val
  }
  sidebarChanged = true
})
const getSidebarClass = () => {
  if (sidebarCollapsed.value) {
    if (widthLessThanMiddle.value) return 'left-1'
    return 'left-[80px]'
  }
  return 'left-[200px]'
}
const getContentClass = () => {
  if (widthLessThanMiddle.value) return 'w-full'
  if (sidebarCollapsed.value) return 'w-[calc(100%-80px)] ml-[80px]'
  return 'w-[calc(100%-200px)] ml-[200px]'
}
const closeMask = () => {
  showMask.value = false
  sidebarCollapsed.value = true
}
const logout = () => {
  localStorage.clear()
  message.success(t('已退出登录！'))
  router.replace('/login')
}
const {$bus} = useGlobal()
const showMyInfo = () => {
  $bus.emit('show-my-info-modal')
}
const toggleLocale = () => {
  const locale: string = localStorage.getItem('locale') || ''
  i18n.locale = locale === 'zh-CN' ? 'en-US' : 'zh-CN'
  localStorage.setItem('locale', i18n.locale)
  location.reload()
}
const getLanguageText = () => {
  const locale: string = localStorage.getItem('locale') || ''
  return locale === 'zh-CN' ? 'ENG' : '中文'
}
</script>
<style scoped>
::v-deep(.ant-layout-header) {
  height: 40px;
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
