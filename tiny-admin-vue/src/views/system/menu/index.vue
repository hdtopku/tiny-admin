<script lang="ts" setup>
import {getMenuTree} from '@/api/system/menu.ts'
import MenuModal from '@/views/system/menu/MenuModal.vue'
import PcMenuList from "@/views/system/menu/PcMenuList.vue";
import Search from "@/components/Search.vue";

const modalRef = ref<any>(), loading = ref(false), dataSource = ref([])
let pagination: any = {}, searchParams: any = {keyword: ''}

const queryList = (params = {}) => {
  loading.value = true
  searchParams = {...searchParams, ...params}
  getMenuTree(searchParams).then((res: any) => {
    dataSource.value = res
  }).finally(() => {
    loading.value = false
  })
}
const openModal = (record: any) => {
  modalRef.value.openModal(record)
}
</script>

<template>
  <div>
    <Search :loading="loading" :placeholder="$t('搜索菜单名称')" :show-status="false" :top="84" @open-modal="openModal"
            @query-list="queryList"/>
    <PcMenuList :loading="loading" :dataSource="dataSource" :pagination="pagination" @open-modal="openModal"
                @query-list="queryList"/>
    <MenuModal ref="modalRef" :menuTree="dataSource" @query-list="queryList"/>
  </div>
</template>
