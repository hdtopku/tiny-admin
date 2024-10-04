<template>
  <div>
    <a-breadcrumb :routes="routes">
      <template #itemRender="{ route }">
        <div v-if="routes.indexOf(route) === routes.length - 1" class="text-gray-500">
          {{ route.breadcrumbName }}
        </div>
        <router-link v-else :to="`${route.path}`">
          <span class="text-gray-300 bg-gray-700 px-2 py-1 rounded-md">{{ route.breadcrumbName }}</span>
        </router-link>
      </template>
      <template #separator>
        <span class="text-gray-400">/</span>
      </template>
    </a-breadcrumb>
  </div>
</template>
<script lang="ts" setup>
import {ref} from 'vue';
import {useMenuStore} from "@/store";
import router from "@/router";

interface Route {
  path: string;
  breadcrumbName: string;
  children?: Array<{
    path: string;
    breadcrumbName: string;
  }>;
}

const routes = ref<Route[]>([]);

watch(() => router.currentRoute.value.path, (path) => {
  routes.value = useMenuStore().getBreadcrumb(path)
}, {immediate: true})
</script>

