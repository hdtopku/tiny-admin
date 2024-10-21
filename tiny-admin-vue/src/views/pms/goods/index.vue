<template>
  <div>
    <!--   Search -->
    <Search :loading="loading" @open-modal="openModal" @query-list="queryList"></Search>
    <!--   PC Table List -->
    <PcGoodsList :dataSource="dataSource" :loading="loading" :pagination="pagination" @open-modal="openModal"
                 @query-list="queryList"></PcGoodsList>
    <!--   Mobile Table List -->
    <MobileGoodsList :dataSource="dataSource" :loading="loading" @open-modal="openModal"></MobileGoodsList>
    <!--   Goods add/edit Modal -->
    <GoodsModal ref="modalRef" @query-list="queryList"/>
  </div>
</template>
<script lang="ts" setup>
import {getGoodsPage} from '@/api/pms/goods.ts'
import GoodsModal from '@/views/pms/goods/GoodsModal.vue'
import PcGoodsList from "@/views/pms/goods/PcGoodsList.vue";
import MobileGoodsList from "@/views/pms/goods/MobileGoodsList.vue";
import Search from "@/components/Search.vue";

const loading = ref(false), dataSource = ref([]), modalRef = ref()
let pagination = {}, searchParams = {}

const queryList = ({keyword = '', status = true, pageNum = 1, pageSize = 2}) => {
  loading.value = true
  searchParams = {...searchParams, keyword, status, pageNum, pageSize}
  getGoodsPage(searchParams).then((res: any) => {
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