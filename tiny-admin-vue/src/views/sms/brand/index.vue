<template>
  <div>
    <Search :loading="loading" :search-form="searchParams" :top="84" placeholder="搜索品牌名称、备注"
            @open-modal="openModal" @query-list="queryList"/>
    <PcBrandList :dataSource="dataSource" :loading="loading" :pagination="pagination" class="hidden sm:block"
                 @open-modal="openModal" @query-list="queryList"/>
    <MobileBrandList :dataSource="dataSource" :loading="loading" :pagination="pagination" class="block sm:hidden"
                     @open-modal="openModal" @query-list="queryList"/>
    <BrandModal ref="modalRef" @query-list="queryList"></BrandModal>
  </div>
</template>
<script lang="ts" setup>
import {getSmsBrandPage} from '@/api/sms/brand.ts'
import BrandModal from '@/views/sms/brand/BrandModal.vue'
import PcBrandList from "@/views/sms/brand/PcBrandList.vue";
import MobileBrandList from "@/views/sms/brand/MobileBrandList.vue";
import Search from "@/components/Search.vue";

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
        pagination = {current: res.current, pageSize: res.size, total: res.total,}
      })
      .finally(() => {
        loading.value = false
      })
}
</script>
