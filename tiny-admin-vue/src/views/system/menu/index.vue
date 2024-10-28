<script lang="ts" setup>
import {getMenuTree} from '@/api/system/menu.ts'
import MenuModal from '@/views/system/menu/MenuModal.vue'
import PcMenuList from "@/views/system/menu/PcMenuList.vue";

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
    <a-affix :offset-top="84">
      <Search :show-status="false" :placeholder="$t('搜索菜单名称')" :loading="loading" @open-modal="openModal" @query-list="queryList"/>
    </a-affix>
    <PcMenuList :loading="loading" :dataSource="dataSource" :pagination="pagination" @open-modal="openModal"
                @query-list="queryList"/>
    <MenuModal ref="modalRef" :menuTree="dataSource" @query-list="queryList"/>
  </div>
</template>
