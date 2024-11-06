<template>
  <PageStructure>
    <template #pc="{dataSource, loading, pagination, queryList, changeRecordStatus, deleteRecordById}">
      <PcFlashSaleList :data-source="dataSource" :loading="loading" :pagination="pagination" class="hidden sm:block"
                       @change-record-status="changeRecordStatus" @query-list="queryList"
                       @delete-record-by-id="deleteRecordById"/>
    </template>
    <template #mobile="{dataSource, loading, pagination, queryList, changeRecordStatus, deleteRecordById}">
      <MobileFlashSaleList :data-source="dataSource" :loading="loading" :pagination="pagination" class="hidden sm:block"
                           @change-record-status="changeRecordStatus" @query-list="queryList"
                           @delete-record-by-id="deleteRecordById"/>
    </template>
    <template #modal="{queryList}">
      <DataModal ref="modalRef" @query-list="queryList"/>
    </template>
    <AddGoodsModal ref="addModalRef" @query-list="queryList"></AddGoodsModal>
    <RemoveGoodsModal
        ref="removeModalRef"
        @query-list="queryList"
    ></RemoveGoodsModal>
  </PageStructure>
</template>
<script lang="ts" setup>
import DataModal from '@/views/sms/flashsale/DataModal.vue'
import {deleteFlashSale, getFlashSalePage, updateFlashSale} from '@/api/sms/flashSale.ts'
import AddGoodsModal from '@/views/sms/flashsale/AddGoodsModal.vue'
import RemoveGoodsModal from '@/views/sms/flashsale/RemoveGoodsModal.vue'
import PcFlashSaleList from "@/views/sms/flashsale/PcFlashSaleList.vue";
import MobileFlashSaleList from "@/views/sms/flashsale/MobileFlashSaleList.vue";

const modalRef = ref<any>()
const openModal = (record: any) => {
  modalRef.value.openModal(record)
}

provide('init', {
  deleteFunc: deleteFlashSale,
  saveOrUpdateFunc: updateFlashSale,
  pageFunc: getFlashSalePage,
  openModal
})
</script>
