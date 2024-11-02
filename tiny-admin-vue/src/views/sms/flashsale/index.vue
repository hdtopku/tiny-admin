<template>
  <div>
    <Search :loading="loading" :search-form="searchParams" :top="84" placeholder="搜索商品名称、备注"
            @open-modal="openModal" @query-list="queryList"/>
    <PcFlashSaleList :dataSource="dataSource" :loading="loading" :pagination="pagination" class="hidden"
                     @open-modal="openModal" @query-list="queryList"/>
    <FlashSaleModal ref="modalRef" @query-list="queryList"></FlashSaleModal>
    <AddGoodsModal ref="addModalRef" @query-list="queryList"></AddGoodsModal>
    <RemoveGoodsModal
        ref="removeModalRef"
        @query-list="queryList"
    ></RemoveGoodsModal>
  </div>
</template>
<script lang="ts" setup>
import FlashSaleModal from '@/views/sms/flashsale/FlashSaleModal.vue'
import {getFlashSalePage} from '@/api/sms/flashSale.ts'
import AddGoodsModal from '@/views/sms/flashsale/AddGoodsModal.vue'
import RemoveGoodsModal from '@/views/sms/flashsale/RemoveGoodsModal.vue'
import PcFlashSaleList from "@/views/sms/flashsale/PcFlashSaleList.vue";
import Search from "@/components/Search.vue";

const openModal = (record: any) => {
  modalRef.value.openModal(record)
}
const loading = ref(false), dataSource = ref([]), modalRef = ref()
let pagination: any = {}, searchParams: any = {keyword: '', status: true, pageNum: 1, pageSize: 10}

const queryList = (params = {}) => {
  loading.value = true
  searchParams = {...searchParams, ...params}
  getFlashSalePage(searchParams)
      .then((res: any) => {
        dataSource.value = res.records
        pagination = {current: res.current, pageSize: res.size, total: res.total,}
      })
      .finally(() => {
        loading.value = false
      })
}
const handleEdit = (record: any) => {
  modalRef.value.showModal(record)
}

const addModalRef = ref()
const assignGoods = (record: any) => {
  addModalRef.value.openModal(record)
}
const removeModalRef = ref()
const removeGoods = (record: any) => {
  removeModalRef.value.openModal(record)
}

const deleteBrand = (id) => {
  console.log(id)
}
const confirmChangeStatus = (record) => {
  console.log(record)
}
const changeStatus = (record) => {
  console.log(record)
}
</script>
