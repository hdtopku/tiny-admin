<template>
  <div>
    <a-affix :offset-top="84">
      <Search :loading="loading" :search-form="searchParams" placeholder="搜索品牌名称、备注" @open-modal="openModal"
              @query-list="queryList"/>
    </a-affix>
    <PcBrandList :dataSource="dataSource" :loading="loading" :pagination="pagination" class="hidden sm:block"
                 @open-modal="openModal" @query-list="queryList"/>
    <BrandModal ref="modalRef" @query-list="queryList"></BrandModal>
  </div>
</template>
<script lang="ts" setup>
import {getSmsBrandPage} from '@/api/sms/brand.ts'
import BrandModal from '@/views/sms/brand/BrandModal.vue'
import PcBrandList from "@/views/sms/brand/PcBrandList.vue";

const openModal = (record: any) => {
  modalRef.value.openModal(record)
}
const loading = ref(false), dataSource = ref([]), modalRef = ref()
let pagination: any = {}, searchParams: any = {keyword: '', status: true, pageNum: 1, pageSize: 10}

const queryList = (params = {}) => {
  loading.value = true
  searchParams = {...searchParams, ...params}
  getSmsBrandPage(searchParams)
      .then((res: any) => {
        dataSource.value = res.records
      })
      .finally(() => {
        loading.value = false
      })
}
</script>
