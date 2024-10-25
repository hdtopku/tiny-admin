<template>
  <div>
    <!--   Search -->
    <a-affix :offset-top="84">
      <Search :loading="loading" placeholder="搜索品牌名、简介、品牌故事" @open-modal="openModal"
              @query-list="queryList"></Search>
    </a-affix>
    <!--   PC Data List -->
    <PcBrandList :dataSource="dataSource" :loading="loading" :pagination="pagination" @open-modal="openModal"
                 @query-list="queryList"></PcBrandList>
    <!--  add/edit Data Modal -->
    <BrandModal ref="modalRef" @query-list="queryList"></BrandModal>
  </div>
</template>
<script lang="ts" setup>
import {getBrandPage} from '@/api/pms/brand.ts'
import BrandModal from '@/views/pms/brand/BrandModal.vue'
import Search from "@/components/Search.vue";
import PcBrandList from "@/views/pms/brand/PcBrandList.vue";

const loading = ref(false), dataSource = ref([]), modalRef = ref()
let pagination: any = {}, searchParams: any = {}

const queryList = ({keyword = '', status = true, pageNum = 1, pageSize = 10}) => {
  loading.value = true
  searchParams = {...searchParams, keyword, status, pageNum, pageSize}
  getBrandPage(searchParams).then((res: any) => {
    dataSource.value = res.records
    pagination = {current: res.current, pageSize: res.size, total: res.total,}
  }).finally(() => {
    loading.value = false
  })
}

const openModal = (record: any = {}) => {
  modalRef.value.openModal(record)
}
</script>
